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
    private TREND[] daysJudgeRule;

    // diff is large then dea,default is small then dea
    private TREND diffLargeThenDea;

    public MacdStockDetailsDataFilter(TREND[] daysJudgeRule) {
        this(daysJudgeRule, TREND.TEND_DOWN);
    }

    public MacdStockDetailsDataFilter(TREND[] daysJudgeRule, TREND diffLargeThenDea) {
        this.daysJudgeRule = daysJudgeRule;
        this.diffLargeThenDea = diffLargeThenDea;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if (ArrayUtils.isEmpty(daysJudgeRule) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int judgeDay = stockDetailsData.getStockDataEntities().size() <= daysJudgeRule.length ? stockDetailsData.getStockDataEntities().size() - 1 : daysJudgeRule.length;
            boolean matchJudgeRule = true;
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
                StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
                TREND curDayJudgeRule = daysJudgeRule[curReverseDayNum - 1];

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

                // judge current day macdDEA is large than macdDiff
                if (diffLargeThenDea.equals(TREND.TEND_DOWN) && (stockDataEntity.getMacdDea() < stockDataEntity.getMacdDif())) {
                    matchJudgeRule = false;
                } else if (diffLargeThenDea.equals(TREND.TEND_UP) && (stockDataEntity.getMacdDea() > stockDataEntity.getMacdDif())) {
                    matchJudgeRule = false;
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

        if (daysJudgeRule != null && daysJudgeRule.length > 0) {
            filterRuleMsg.append("判断[").append(daysJudgeRule.length).append("]天");
            for (int i = 0; i < daysJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(daysJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }
        return filterRuleMsg.toString();
    }
}
