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

public class VolumeTrendDetailsFilter implements StockDetailsDataFilter {

    private static Logger LOGGER = Logger.getLogger(VolumeTrendDetailsFilter.class);

    private boolean isTodayLargeThen5 = false;

    private boolean isTodayLargeThen10 = false;

    private TREND[] volumeJudgeRule;

    private TREND[] volume5JudgeRule;

    private TREND[] volume10JudgeRule;

    public VolumeTrendDetailsFilter(TREND[] volumeJudgeRule, boolean isTodayLargeThen5, boolean isTodayLargeThen10) {
        this(volumeJudgeRule, null, null, isTodayLargeThen5, isTodayLargeThen10);
    }

    public VolumeTrendDetailsFilter(TREND[] volumeJudgeRule, TREND[] volume5JudgeRule, TREND[] volume10JudgeRule, boolean isTodayLargeThen5, boolean isTodayLargeThen10) {
        this.volumeJudgeRule = volumeJudgeRule;
        this.volume5JudgeRule = volume5JudgeRule;
        this.volume10JudgeRule = volume10JudgeRule;
        this.isTodayLargeThen5 = isTodayLargeThen5;
        this.isTodayLargeThen10 = isTodayLargeThen10;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        return Map.of();
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap, int fewDaysAgo) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if ((ArrayUtils.isEmpty(volumeJudgeRule) && ArrayUtils.isEmpty(volume5JudgeRule) && ArrayUtils.isEmpty(volume10JudgeRule)) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int maxJudgeRule = Math.max(Math.max(volumeJudgeRule == null ? 0 : volumeJudgeRule.length, volume5JudgeRule == null ? 0 : volume5JudgeRule.length), volume10JudgeRule == null ? 0 : volume10JudgeRule.length);
            int judgeDay = stockDetailsData.getStockDataEntities().size() - fewDaysAgo <= maxJudgeRule ? stockDetailsData.getStockDataEntities().size() - 1 - fewDaysAgo : maxJudgeRule;
            boolean matchJudgeRule = true;
            if (judgeDay == 0) {
                matchJudgeRule = false;
            }
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);

                boolean volumeRet = filterVolume(stockDetailsData, curReverseDayNum, fewDaysAgo);
                boolean volume5Ret = filterVolume5(stockDetailsData, curReverseDayNum, fewDaysAgo);
                boolean volume10Ret = filterVolume10(stockDetailsData, curReverseDayNum, fewDaysAgo);

                matchJudgeRule = volumeRet && volume5Ret && volume10Ret;

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum) {
                    if (isTodayLargeThen5 && stockDataEntity.getVolume() < stockDataEntity.getVolume5()) {
                        matchJudgeRule = false;
                    }

                    if (isTodayLargeThen10 && stockDataEntity.getVolume() < stockDataEntity.getVolume10()) {
                        matchJudgeRule = false;
                    }
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

    private boolean filterVolume(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(volumeJudgeRule) || volumeJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = volumeJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getVolume() < stockDataEntity.getVolume()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getVolume() > stockDataEntity.getVolume()) {
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

    private boolean filterVolume5(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(volume5JudgeRule) || volume5JudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = volume5JudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getVolume5() < stockDataEntity.getVolume5()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getVolume5() > stockDataEntity.getVolume5()) {
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

    private boolean filterVolume10(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(volume10JudgeRule) || volume10JudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = volume10JudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getVolume10() < stockDataEntity.getVolume10()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getVolume10() > stockDataEntity.getVolume10()) {
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

        if (volumeJudgeRule != null && volumeJudgeRule.length > 0) {
            filterRuleMsg.append("当天交易量判断判断: ");
            filterRuleMsg.append(",判断[").append(volumeJudgeRule.length).append("]天");
            for (int i = 0; i < volumeJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(volumeJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }

        if (volume5JudgeRule != null && volume5JudgeRule.length > 0) {
            filterRuleMsg.append(",5日交易线判断: ");
            filterRuleMsg.append(",判断[").append(volume5JudgeRule.length).append("]天");
            for (int i = 0; i < volume5JudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(volume5JudgeRule[i].tendString)
                        .append("趋势]");
            }
        }

        if (volume10JudgeRule != null && volume10JudgeRule.length > 0) {
            filterRuleMsg.append(",10日交易线判断: ");
            filterRuleMsg.append(",判断[").append(volume10JudgeRule.length).append("]天");
            for (int i = 0; i < volume10JudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(volume10JudgeRule[i].tendString)
                        .append("趋势]");
            }
        }

        if (isTodayLargeThen5) {
            filterRuleMsg.append(",当天交易量超过5日交易线");
        }

        if (isTodayLargeThen10) {
            filterRuleMsg.append(",当天交易量超过10日交易线");
        }
        return filterRuleMsg.toString();
    }
}
