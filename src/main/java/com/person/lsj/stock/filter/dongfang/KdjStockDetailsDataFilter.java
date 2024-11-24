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

    private static int IGNORE_VALUE_INT = -1;

    private static float IGNORE_VALUE_FLOAT = -1.0f;

    // size = judgeDay  key=reveser day num  value=0/1/* 0=up 1=down *=up or down
    // eg.  [*,1,1,1,1] represent: last day is up or down, second~fifth to last day is down
    private TREND[] jJudgeRule;

    private TREND[] kJudgeRule;

    private TREND[] dJudgeRule;

    private float dValueBtwnKD = -1.0f;

    private int targetAbsJ = -1;

    public KdjStockDetailsDataFilter(TREND[] jJudgeRule, int targetAbsJ, TREND[] kJudgeRule, TREND[] dJudgeRule, float dValueBtwnKD) {
        this.jJudgeRule = jJudgeRule;
        this.kJudgeRule = kJudgeRule;
        this.dJudgeRule = dJudgeRule;
        this.dValueBtwnKD = dValueBtwnKD;
        this.targetAbsJ = targetAbsJ;
    }

    public KdjStockDetailsDataFilter(float dValueBtwnKD, TREND[] kJudgeRule, TREND[] dJudgeRule) {
        this(null, IGNORE_VALUE_INT, kJudgeRule, dJudgeRule, dValueBtwnKD);
    }

    public KdjStockDetailsDataFilter(int targetAbsJ, TREND[] jJudgeRule) {
        this(jJudgeRule, targetAbsJ, null, null, IGNORE_VALUE_FLOAT);
    }

    public KdjStockDetailsDataFilter(TREND[] jJudgeRule) {
        this(TARGET_ABS_KDJ, jJudgeRule);
    }

    @Override
    public Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap) {
        LOGGER.debug("enter filter,size:" + stockDetailsDataMap.size());
        if ((ArrayUtils.isEmpty(kJudgeRule) && ArrayUtils.isEmpty(dJudgeRule) && ArrayUtils.isEmpty(jJudgeRule)) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> result = new HashMap<>();

        Iterator<Map.Entry<String, StockDetailsData>> stockDetailsIterator = stockDetailsDataMap.entrySet().stream().iterator();
        while (stockDetailsIterator.hasNext()) {
            Map.Entry<String, StockDetailsData> stockDetailsEntry = stockDetailsIterator.next();
            String stockCode = stockDetailsEntry.getKey();
            StockDetailsData stockDetailsData = stockDetailsEntry.getValue();
            int maxJudgeRule = Math.max(Math.max(kJudgeRule == null ? 0 : kJudgeRule.length, dJudgeRule == null ? 0 : dJudgeRule.length), jJudgeRule == null ? 0 : jJudgeRule.length);
            int judgeDay = stockDetailsData.getStockDataEntities().size() <= maxJudgeRule ? stockDetailsData.getStockDataEntities().size() - 1 : maxJudgeRule;
            boolean matchJudgeRule = true;
            for (int curReverseDayNum = 1; curReverseDayNum <= judgeDay; curReverseDayNum++) {

                StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);

                boolean kRet = filterK(stockDetailsData, curReverseDayNum);
                boolean dRet = filterD(stockDetailsData, curReverseDayNum);
                boolean jRet = filterJ(stockDetailsData, curReverseDayNum);

                matchJudgeRule = kRet && dRet && jRet;

                // the last day kdjJ data must be keep within the target absolute value
                if (1 == curReverseDayNum) {
                    if (targetAbsJ > 0 && !(Math.abs(stockDataEntity.getKdjJ()) <= targetAbsJ)) {
                        matchJudgeRule = false;
                    }

                    if (stockDataEntity.getKdjK() > 30 || stockDataEntity.getKdjD() > 30 || dValueBtwnKD > 0 && !(Math.abs(stockDataEntity.getKdjK() - stockDataEntity.getKdjD()) <= dValueBtwnKD)) {
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

    private boolean filterJ(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if (ArrayUtils.isEmpty(jJudgeRule) || jJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = jJudgeRule[curReverseDayNum - 1];

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

        return matchJudgeRule;
    }

    private boolean filterK(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if (ArrayUtils.isEmpty(kJudgeRule) || kJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = kJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getKdjK() < stockDataEntity.getKdjK()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getKdjK() > stockDataEntity.getKdjK()) {
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

    private boolean filterD(StockDetailsData stockDetailsData, int curReverseDayNum) {
        if (ArrayUtils.isEmpty(dJudgeRule) || dJudgeRule.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum);
        StockDataEntity stockDataEntityPre = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - curReverseDayNum - 1);
        TREND curDayJudgeRule = dJudgeRule[curReverseDayNum - 1];

        // judge current day kdj tend
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDataEntityPre.getKdjD() < stockDataEntity.getKdjD()) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDataEntityPre.getKdjD() > stockDataEntity.getKdjD()) {
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

        if (dValueBtwnKD > 0) {
            filterRuleMsg.append("K线跟D线相差的绝对值不超过[").append(dValueBtwnKD).append("], ");
        }

        if (kJudgeRule != null && kJudgeRule.length > 0) {
            filterRuleMsg.append("Kdj中K线判断: ");
            filterRuleMsg.append(",判断[").append(kJudgeRule.length).append("]天");
            for (int i = 0; i < kJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(kJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }

        if (dJudgeRule != null && dJudgeRule.length > 0) {
            filterRuleMsg.append(",Kdj中D线判断: ");
            filterRuleMsg.append(",判断[").append(dJudgeRule.length).append("]天");
            for (int i = 0; i < dJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(dJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }

        if (targetAbsJ > 0) {
            filterRuleMsg.append("j线绝对值不超过[").append(targetAbsJ).append("], ");
        }

        if (jJudgeRule != null && jJudgeRule.length > 0) {
            filterRuleMsg.append("Kdj中J线判断: ");
            filterRuleMsg.append(",判断[").append(jJudgeRule.length).append("]天");
            for (int i = 0; i < jJudgeRule.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(jJudgeRule[i].tendString)
                        .append("趋势]");
            }
        }
        return filterRuleMsg.toString();
    }

}
