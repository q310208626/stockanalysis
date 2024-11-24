package com.person.lsj.stock.filter.dongfang;

import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowData;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.MoneyFlowDataFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class BigOrderMoneyFlowDataFilter implements MoneyFlowDataFilter {
    private static Logger LOGGER = Logger.getLogger(BigOrderMoneyFlowDataFilter.class);

    // default judge rule: do not care about 3 days money flow trend,just care 3 days total money
    private static TREND[] DEFAULT_JUDGE_RULE = {TREND.TEND_RANDOM, TREND.TEND_RANDOM, TREND.TEND_RANDOM};

    // target funds imcrement 1000w
    private static int DEFAULT_TARGET_MONEY_FLOW_INCREMENT = 10000000;

    private TREND[] largeOrderTrend = DEFAULT_JUDGE_RULE;

    private TREND[] superLargeOrderTrend = DEFAULT_JUDGE_RULE;

    private int targetMoneyNum = DEFAULT_TARGET_MONEY_FLOW_INCREMENT;


    public BigOrderMoneyFlowDataFilter(TREND[] largeOrderTrend, TREND[] superLargeOrderTrend) {
        this(largeOrderTrend, superLargeOrderTrend, DEFAULT_TARGET_MONEY_FLOW_INCREMENT);
    }

    public BigOrderMoneyFlowDataFilter(TREND[] largeOrderTrend, TREND[] superLargeOrderTrend, int targetMoneyNum) {
        this.largeOrderTrend = largeOrderTrend;
        this.superLargeOrderTrend = superLargeOrderTrend;
        this.targetMoneyNum = targetMoneyNum;
    }

    @Override
    public List<StockMoneyFlowBean> filter(List<StockMoneyFlowBean> stockMoneyFlowBeanList) {
        if (CollectionUtils.isEmpty(stockMoneyFlowBeanList) || (ArrayUtils.isEmpty(largeOrderTrend) && ArrayUtils.isEmpty(superLargeOrderTrend))) {
            return stockMoneyFlowBeanList;
        }

        List<StockMoneyFlowBean> filterStockMoneyFlowBeanList = new ArrayList<StockMoneyFlowBean>();
        for (StockMoneyFlowBean stockMoneyFlowBean : stockMoneyFlowBeanList) {
            List<StockMoneyFlowData> stockMoneyFlowBeanDatas = stockMoneyFlowBean.getDatas();
            if (CollectionUtils.isEmpty(stockMoneyFlowBeanDatas)) {
                continue;
            }

            int mainDaysMoneyFlow = 0;
            boolean matchJudgeRule = true;
            int judgeDay = Math.max(largeOrderTrend == null ? 0 : largeOrderTrend.length, superLargeOrderTrend == null ? 0 : superLargeOrderTrend.length);
            for (int i = 0; i < (stockMoneyFlowBeanDatas.size() < judgeDay ? stockMoneyFlowBeanDatas.size() : judgeDay); i++) {
                StockMoneyFlowData stockMoneyFlowData = stockMoneyFlowBeanDatas.get(i);

                boolean largeRet = filterLargeOrder(stockMoneyFlowData,i);
                boolean superLargeRet = filterSuperLargeOrder(stockMoneyFlowData,i);
                matchJudgeRule = largeRet && superLargeRet;

                // if one days data no match,break down current loop
                if (!matchJudgeRule) {
                    break;
                }

                mainDaysMoneyFlow += stockMoneyFlowData.getMainMoneyFlow();
            }

            if (matchJudgeRule && mainDaysMoneyFlow >= targetMoneyNum) {
                filterStockMoneyFlowBeanList.add(stockMoneyFlowBean);
            }
        }
        return filterStockMoneyFlowBeanList;
    }

    private boolean filterLargeOrder(StockMoneyFlowData stockDetailsData, int curReverseDayNum) {
        if (ArrayUtils.isEmpty(largeOrderTrend) || largeOrderTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        TREND curDayJudgeRule = largeOrderTrend[curReverseDayNum];

        // judge current day money flow
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDetailsData.getLargeOrderMoney() > 0) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDetailsData.getLargeOrderMoney() < 0) {
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

    private boolean filterSuperLargeOrder(StockMoneyFlowData stockDetailsData, int curReverseDayNum) {
        if (ArrayUtils.isEmpty(superLargeOrderTrend) || superLargeOrderTrend.length < curReverseDayNum) {
            return true;
        }

        boolean matchJudgeRule = true;
        TREND curDayJudgeRule = superLargeOrderTrend[curReverseDayNum];

        // judge current day money flow
        switch (curDayJudgeRule) {
            case TEND_DOWN:
                if (stockDetailsData.getSuperLargeOrderMoney() > 0) {
                    matchJudgeRule = false;
                }
                break;
            case TEND_UP:
                if (stockDetailsData.getSuperLargeOrderMoney() < 0) {
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
        StringBuffer filterRuleMsg = new StringBuffer("大单资金判断: ");
        filterRuleMsg.append("总资金量为[").append(targetMoneyNum).append("]");

        if (largeOrderTrend != null && largeOrderTrend.length > 0) {
            filterRuleMsg.append(" ,大单判断[").append(largeOrderTrend.length).append("]天");
            for (int i = 0; i < largeOrderTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(largeOrderTrend[i].tendString)
                        .append("趋势]");
            }
        }

        if (superLargeOrderTrend != null && superLargeOrderTrend.length > 0) {
            filterRuleMsg.append(" ,超大单判断[").append(superLargeOrderTrend.length).append("]天");
            for (int i = 0; i < superLargeOrderTrend.length; i++) {
                filterRuleMsg.append(" ,倒数第[").append(i + 1).append("]天")
                        .append("呈现[")
                        .append(superLargeOrderTrend[i].tendString)
                        .append("趋势]");
            }
        }
        return filterRuleMsg.toString();
    }
}
