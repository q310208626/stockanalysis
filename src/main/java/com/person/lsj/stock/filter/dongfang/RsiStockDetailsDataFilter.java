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

public class RsiStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(RsiStockDetailsDataFilter.class);

    private int minRsi = 0;

    private int maxRsi = 100;

    private TREND[] rsi1JudgeRule;

    public RsiStockDetailsDataFilter(int minRsi, int maxRsi, TREND[] rsi1JudgeRule) {
        this.minRsi = minRsi;
        this.maxRsi = maxRsi;
        this.rsi1JudgeRule = rsi1JudgeRule;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        return filter(stockDetailsDataMap, 0);
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap, int fewDaysAgo) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if ((ArrayUtils.isEmpty(rsi1JudgeRule) || CollectionUtils.isEmpty(stockDetailsDataMap))) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int maxJudgeRule = rsi1JudgeRule == null ? 0 : rsi1JudgeRule.length;
            int judgeDay = stockDetailsData.getStockDataEntities().size() - fewDaysAgo <= maxJudgeRule ? stockDetailsData.getStockDataEntities().size() - 1 - fewDaysAgo : maxJudgeRule;
            boolean matchJudgeRule = true;
            if (judgeDay == 0) {
                matchJudgeRule = false;
            }
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);

                boolean kRet = filterRsiA(stockDetailsData, curReverseDayNum, fewDaysAgo);

                matchJudgeRule = kRet;

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum) {
                    if (minRsi > stockDataEntity.getRsiA() || maxRsi < stockDataEntity.getRsiA()) {
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

    private boolean filterRsiA(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(rsi1JudgeRule) || rsi1JudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = rsi1JudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getRsiA() < stockDataEntity.getRsiA()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getRsiA() > stockDataEntity.getRsiA()) {
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
        return "";
    }
}
