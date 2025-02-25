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

public class WRStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(WRStockDetailsDataFilter.class);

    private static int DEFAULT_TARGET = 80;

    // 10 days
    private int targetWrA = DEFAULT_TARGET;

    // 6 days
    private int targetWrB = DEFAULT_TARGET;

    // default not judge wrA
    private boolean isJudgeWrA = false;

    // default judge wrB
    private boolean isJudgeWrB = true;

    private TREND[] wrATrend = new TREND[]{TREND.TEND_RANDOM};

    private TREND[] wrBTrend = new TREND[]{TREND.TEND_RANDOM};

    public WRStockDetailsDataFilter() {
        targetWrA = 80;
        targetWrB = 80;
    }

    public WRStockDetailsDataFilter(int targetWrA, TREND[] wrATrend, int targetWrB, TREND[] wrBTrend) {
        this.targetWrA = targetWrA;
        this.targetWrB = targetWrB;
        this.wrATrend = wrATrend;
        this.wrBTrend = wrBTrend;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        return filter(stockDetailsDataMap, 0);
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap, int fewDaysAgo) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if ((ArrayUtils.isEmpty(wrATrend) && ArrayUtils.isEmpty(wrBTrend)) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int maxJudgeRule = Math.max(wrATrend == null ? 0 : wrATrend.length, wrBTrend == null ? 0 : wrBTrend.length);
            int judgeDay = stockDetailsData.getStockDataEntities().size() - fewDaysAgo <= maxJudgeRule ? stockDetailsData.getStockDataEntities().size() - 1 - fewDaysAgo : maxJudgeRule;
            boolean matchJudgeRule = true;
            if (judgeDay == 0) {
                matchJudgeRule = false;
            }
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);

                // judge current day macdDEA is large than macdDiff
                boolean resultWRA = filterWRA(stockDetailsData, curReverseDayNum, fewDaysAgo);
                boolean resultWRB = filterWRB(stockDetailsData, curReverseDayNum, fewDaysAgo);
                matchJudgeRule = resultWRA && resultWRB;

                if (1 == curReverseDayNum) {
                    boolean resultMatchTargetWRA = isMatchTargetWrA(stockDataEntity);
                    boolean resultMatchTargetWRB = isMatchTargetWrB(stockDataEntity);
                    matchJudgeRule = matchJudgeRule && resultMatchTargetWRA && resultMatchTargetWRB;
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

    private boolean filterWRA(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(wrATrend) || wrATrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = wrATrend[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getWrA() < stockDataEntity.getWrA()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getWrA() > stockDataEntity.getWrA()) {
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

    private boolean filterWRB(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(wrBTrend) || wrBTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = wrBTrend[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getWrB() < stockDataEntity.getWrB()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getWrB() > stockDataEntity.getWrB()) {
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

    private boolean isMatchTargetWrA(StockDataEntity stockDataEntity) {
        boolean matchJudgeRule = true;
        if (isJudgeWrA && stockDataEntity.getWrA() < targetWrA) {
            matchJudgeRule = false;
        }
        return matchJudgeRule;
    }

    private boolean isMatchTargetWrB(StockDataEntity stockDataEntity) {
        boolean matchJudgeRule = true;
        if (isJudgeWrB && stockDataEntity.getWrB() < targetWrB) {
            matchJudgeRule = false;
        }
        return matchJudgeRule;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer();
        if (isJudgeWrA) {
            filterRuleMsg.append("判断当天 wrA ").append("大于").append(targetWrA).append(", ");
        }

        if (isJudgeWrB) {
            filterRuleMsg.append("判断当天 wrB ").append("大于").append(targetWrB).append(", ");
        }

        if (wrATrend != null && wrATrend.length > 0) {
            filterRuleMsg.append("判断wrA趋势,");
            filterRuleMsg.append("判断[").append(wrATrend.length).append("]天");
            for (int i = 0; i < wrATrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(wrATrend[i].tendString)
                        .append("趋势], ");
            }
        }

        if (wrBTrend != null && wrBTrend.length > 0) {
            filterRuleMsg.append("判断wrB趋势,");
            filterRuleMsg.append("判断[").append(wrBTrend.length).append("]天");
            for (int i = 0; i < wrBTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(wrBTrend[i].tendString)
                        .append("趋势], ");
            }
        }

        if (filterRuleMsg.lastIndexOf(",") == filterRuleMsg.length() - 1) {
            filterRuleMsg.deleteCharAt(filterRuleMsg.length() - 1);
        }
        return filterRuleMsg.toString();
    }
}
