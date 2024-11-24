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

public class IncreaseRateDataFilter implements StockDetailsDataFilter {
    private static Logger LOGGER = Logger.getLogger(IncreaseRateDataFilter.class);

    private static TREND[] DEFAULT_JUDGE_RULE = {TREND.TEND_RANDOM, TREND.TEND_RANDOM, TREND.TEND_RANDOM};

    // size = judgeDay  key=reveser day num  value=0/1/* 0=up 1=down *=up or down
    // eg.  [*,1,1,1,1] represent: last day is up or down, second~fifth to last day is down
    private TREND[] daysJudgeRule = DEFAULT_JUDGE_RULE;


    private float[] increaseRateRule = new float[]{-10.0f, -10.0f, -10.0f};

    public IncreaseRateDataFilter(TREND[] daysJudgeRule, float[] increaseRateRule) {
        this.daysJudgeRule = daysJudgeRule;
        this.increaseRateRule = increaseRateRule;
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
            int ruleJudgeDay = Math.min(daysJudgeRule == null ? 0 : daysJudgeRule.length, increaseRateRule == null ? 0 : increaseRateRule.length);
            int judgeDay = stockDetailsData.getStockDataEntities().size() <= ruleJudgeDay ? stockDetailsData.getStockDataEntities().size() - 1 : ruleJudgeDay;
            boolean matchJudgeRule = true;
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
                float curDayIncreaseRate = stockDataEntity.getIncreasePercentage();
                TREND curDayJudgeRule = daysJudgeRule[curReverseDayNum - 1];
                float targetIncreaseRate = increaseRateRule[curReverseDayNum - 1];

                // judge current day kdj tend
                switch (curDayJudgeRule) {
                    case TEND_DOWN:
                        if (targetIncreaseRate < curDayIncreaseRate) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_UP:
                        if (targetIncreaseRate > curDayIncreaseRate) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_RANDOM:
                        break;
                    default:
                        break;
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

        if (daysJudgeRule.length > 0) {
            filterRuleMsg.append("涨幅判断, ").append(",判断[").append(daysJudgeRule.length).append("]天");
            for (int i = 0; i < daysJudgeRule.length; i++) {
                TREND curDayJudgeRule = daysJudgeRule[i];
                float targetIncrRate = increaseRateRule[i];
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天");
                if (curDayJudgeRule.equals(TREND.TEND_UP)) {
                    filterRuleMsg.append(",涨幅大于[").append(targetIncrRate).append("]");
                } else if (curDayJudgeRule.equals(TREND.TEND_DOWN)) {
                    filterRuleMsg.append(",涨幅小于[").append(targetIncrRate).append("]");
                } else {
                    filterRuleMsg.append(",不做判断");
                }
            }
        }
        return filterRuleMsg.toString();
    }
}
