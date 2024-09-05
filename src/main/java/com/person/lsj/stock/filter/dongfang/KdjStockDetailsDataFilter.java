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

public class KdjStockDetailsDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(KdjStockDetailsDataFilter.class);

    // abs(kdj) <= 10
    private static int TARGET_ABS_KDJ = 15;

    // size = judgeDay  key=reveser day num  value=0/1/* 0=up 1=down *=up or down
    // eg.  [*,1,1,1,1] represent: last day is up or down, second~fifth to last day is down
    private TREND[] daysJudgeRule;

    private int targetAbsKdj;

    public KdjStockDetailsDataFilter(int targetAbsKdj, TREND[] daysJudgeRule) {
        this.targetAbsKdj = targetAbsKdj;
        this.daysJudgeRule = daysJudgeRule;
    }

    public KdjStockDetailsDataFilter(TREND[] daysJudgeRule) {
        this(TARGET_ABS_KDJ, daysJudgeRule);
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
                TREND curDayJudgeRule = daysJudgeRule[curReverseDayNum -1 ];

                // judge current day kdj tend
                switch (curDayJudgeRule) {
                    case TEND_DOWN:
                        if (stockDataEntityPre.getKdjJ() < stockDataEntity.getKdjJ()) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_UP:
                        if (stockDataEntityPre.getKdjJ() > stockDataEntity.getKdjJ()) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_RANDOM:
                        break;
                    default:
                        break;
                }

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum && !(Math.abs(stockDataEntity.getKdjJ()) <= TARGET_ABS_KDJ)) {
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
        StringBuffer filterRuleMsg = new StringBuffer("Kdj中J线判断: ");
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
