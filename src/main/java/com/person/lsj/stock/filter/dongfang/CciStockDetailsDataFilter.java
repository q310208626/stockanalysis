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

public class CciStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(CciStockDetailsDataFilter.class);

    private int minCci = 0;

    private int maxCci = 100;

    private TREND[] cciJudgeRule;

    public CciStockDetailsDataFilter(int minCci, int maxCci, TREND[] cciJudgeRule) {
        this.minCci = minCci;
        this.maxCci = maxCci;
        this.cciJudgeRule = cciJudgeRule;
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        return filter(stockDetailsDataMap, 0);
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap, int fewDaysAgo) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if ((ArrayUtils.isEmpty(cciJudgeRule) || CollectionUtils.isEmpty(stockDetailsDataMap))) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int maxJudgeRule = cciJudgeRule == null ? 0 : cciJudgeRule.length;
            int judgeDay = stockDetailsData.getStockDataEntities().size() - fewDaysAgo <= maxJudgeRule ? stockDetailsData.getStockDataEntities().size() - 1 - fewDaysAgo : maxJudgeRule;
            boolean matchJudgeRule = true;
            if (judgeDay == 0) {
                matchJudgeRule = false;
            }
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);

                boolean kRet = filterCci(stockDetailsData, curReverseDayNum, fewDaysAgo);

                matchJudgeRule = kRet;

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum) {
                    if (minCci > stockDataEntity.getCci() || maxCci < stockDataEntity.getCci()) {
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

    private boolean filterCci(StockDetailsData stockDetailsData, int curReverseDayNum, int fewDaysAgo) {
        if (ArrayUtils.isEmpty(cciJudgeRule) || cciJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - fewDaysAgo);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1 - fewDaysAgo);
        TREND curDayJudgeRule = cciJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getCci() < stockDataEntity.getCci()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getCci() > stockDataEntity.getCci()) {
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

        if (cciJudgeRule.length > 0) {
            filterRuleMsg.append("Cci判断").append(" ,判断[").append(cciJudgeRule.length).append("]天");
            for (int i = 0; i < cciJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(cciJudgeRule[i].tendString)
                        .append("趋势]");
            }
            filterRuleMsg.append(", ").append(minCci).append("< Cci < ").append(maxCci);
        }
        return filterRuleMsg.toString();
    }
}
