package com.person.lsj.stock.filter.dongfang;

import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.StockDetailsDataFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacdStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(MacdStockDetailsDataFilter.class);

    // size = judgeDay  key=reveser day num  value=0/1/* 0=up 1=down *=up or down
    // eg.  [*,1,1,1,1] represent: last day is up or down, second~fifth to last day is down
    private TREND[] macdJudgeRule = new TREND[]{TREND.TEND_RANDOM};

    private TREND[] diffJudgeRule = new TREND[]{TREND.TEND_RANDOM};

    // diff is large then dea,default is small then dea
    private TREND diffLargeThenDea = TREND.TEND_RANDOM;

    // diff is large then zero
    private TREND isDiffLargeThenZero = TREND.TEND_RANDOM;

    public MacdStockDetailsDataFilter(TREND[] macdJudgeRule) {
        this(macdJudgeRule, TREND.TEND_DOWN);
    }

    public MacdStockDetailsDataFilter(TREND[] macdJudgeRule, TREND diffLargeThenDea) {
        this(macdJudgeRule, diffLargeThenDea, TREND.TEND_RANDOM);
    }

    public MacdStockDetailsDataFilter(TREND[] macdJudgeRule, TREND diffLargeThenDea, TREND isDiffLargeThenZero) {
        this(macdJudgeRule, new TREND[]{TREND.TEND_RANDOM}, diffLargeThenDea, isDiffLargeThenZero);
    }

    public MacdStockDetailsDataFilter(TREND[] macdJudgeRule, TREND[] diffJudgeRule, TREND diffLargeThenDea, TREND isDiffLargeThenZero) {
        this.macdJudgeRule = macdJudgeRule;
        this.diffJudgeRule = diffJudgeRule;
        this.diffLargeThenDea = diffLargeThenDea;
        this.isDiffLargeThenZero = isDiffLargeThenZero;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        return filter(stockDetailsDataMap, 0);
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap, int fewDaysAgo) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if (ArrayUtils.isEmpty(macdJudgeRule) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int judgeDay = stockDetailsData.getStockDataEntities().size() - fewDaysAgo <= macdJudgeRule.length ? stockDetailsData.getStockDataEntities().size() - 1 - fewDaysAgo : macdJudgeRule.length;
            boolean matchJudgeRule = true;
            if (judgeDay == 0) {
                matchJudgeRule = false;
            }
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);

                // judge current day macdDEA is large than macdDiff
                boolean resultMacd = filterMacd(stockDetailsData, curReverseDayNum, fewDaysAgo);
                boolean resultDiff = filterDiff(stockDetailsData, curReverseDayNum, fewDaysAgo);
                matchJudgeRule = resultMacd && resultDiff;

                if (1 == curReverseDayNum) {
                    boolean resultMatchDiffNdDea = isMatchDiffAndDea(stockDataEntity);
                    boolean resultMatchDiffNdZero = isMatchDiffAndZero(stockDataEntity);
                    matchJudgeRule = matchJudgeRule && resultMatchDiffNdDea && resultMatchDiffNdZero;
                }

                // if one days data no match,break down current loop
                if (!matchJudgeRule) {
                    break;
                }
            }

            // if match,add into result
            if (matchJudgeRule) {
                result.put(stockCode, stockDetailsData);
            }

        }
        LOGGER.debug("exit filter,size:" + result.size());
        return result;
    }

    private boolean isMatchDiffAndDea(StockDataEntity stockDataEntity) {
        boolean matchJudgeRule = true;
        if (diffLargeThenDea.equals(TREND.TEND_DOWN) && (stockDataEntity.getMacdDea() < stockDataEntity.getMacdDif())) {
            matchJudgeRule = false;
        } else if (diffLargeThenDea.equals(TREND.TEND_UP) && (stockDataEntity.getMacdDea() > stockDataEntity.getMacdDif())) {
            matchJudgeRule = false;
        }
        return matchJudgeRule;
    }

    private boolean isMatchDiffAndZero(StockDataEntity stockDataEntity) {
        boolean matchJudgeRule = true;
        if (isDiffLargeThenZero.equals(TREND.TEND_DOWN) && (stockDataEntity.getMacdDif() > 0)) {
            matchJudgeRule = false;
        } else if (isDiffLargeThenZero.equals(TREND.TEND_UP) && (stockDataEntity.getMacdDif() < 0)) {
            matchJudgeRule = false;
        }
        return matchJudgeRule;
    }

    private boolean filterMacd(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(macdJudgeRule) || macdJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = macdJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getMacd() < stockDataEntity.getMacd()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getMacd() > stockDataEntity.getMacd()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_RANDOM:
                break;
            default:
                break;
        }

        return matchJudgeRule;
    }

    private boolean filterDiff(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(diffJudgeRule) || diffJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = diffJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getMacdDif() < stockDataEntity.getMacdDif()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getMacdDif() > stockDataEntity.getMacdDif()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_RANDOM:
                break;
            default:
                break;
        }

        return matchJudgeRule;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer();
        if (!diffLargeThenDea.equals(TREND.TEND_RANDOM)) {
            filterRuleMsg.append("判断当天 macdDEA ");
            if (diffLargeThenDea.equals(TREND.TEND_UP)) {
                filterRuleMsg.append("小于");
            } else {
                filterRuleMsg.append("大于");
            }
            filterRuleMsg.append(" macdDiff, ");
        } else {
            filterRuleMsg.append("判断当天 macd, ");
        }

        if (macdJudgeRule != null && macdJudgeRule.length > 0) {
            filterRuleMsg.append("判断[").append(macdJudgeRule.length).append("]天");
            for (int i = 0; i < macdJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(macdJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }
        return filterRuleMsg.toString();
    }
}
