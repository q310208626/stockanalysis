package com.person.lsj.stock.filter.dongfang;

import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowData;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.MoneyFlowDataFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main force money flow filter
 */
public class MainMoneyFlowDataFilter implements MoneyFlowDataFilter {
    private static Logger LOGGER = Logger.getLogger(MacdStockDetailsDataFilter.class);

    // default judge rule: do not care about 3 days money flow trend,just care 3 days total money
    private static TREND[] DEFAULT_JUDGE_RULE = {TREND.TEND_RANDOM, TREND.TEND_RANDOM, TREND.TEND_RANDOM};

    // target funds imcrement 1000w
    private static int DEFAULT_TARGET_MONEY_FLOW_INCREMENT = 10000000;

    private TREND[] daysJudgeRule;

    private int targetMoneyNum;

    public MainMoneyFlowDataFilter() {
        this(DEFAULT_JUDGE_RULE, DEFAULT_TARGET_MONEY_FLOW_INCREMENT);
    }

    public MainMoneyFlowDataFilter(TREND[] daysJudgeRule, int targetMoneyNum) {
        this.daysJudgeRule = daysJudgeRule;
        this.targetMoneyNum = targetMoneyNum;
    }

    @Override
    public List<StockMoneyFlowBean> filter(List<StockMoneyFlowBean> stockMoneyFlowBeanList) {
        if (CollectionUtils.isEmpty(stockMoneyFlowBeanList) || ArrayUtils.isEmpty(daysJudgeRule)) {
            return stockMoneyFlowBeanList;
        }

        List<StockMoneyFlowBean> filterStockMoneyFlowBeanList = new ArrayList<StockMoneyFlowBean>();
        for (StockMoneyFlowBean stockMoneyFlowBean : stockMoneyFlowBeanList) {
            List<StockMoneyFlowData> stockMoneyFlowBeanDatas = stockMoneyFlowBean.getDatas();
            if (CollectionUtils.isEmpty(stockMoneyFlowBeanDatas)) {
                continue;
            }

            int mainDaysMoneyFlow = 0;
            boolean moneyAllPositive = true;
            boolean matchJudgeRule = true;
            for (int i = 0; i < (stockMoneyFlowBeanDatas.size() < daysJudgeRule.length ? stockMoneyFlowBeanDatas.size() : daysJudgeRule.length); i++) {
                StockMoneyFlowData stockMoneyFlowData = stockMoneyFlowBeanDatas.get(i);
                TREND curDayJudgeRule = daysJudgeRule[i];

                // judge current day money flow
                switch (curDayJudgeRule) {
                    case TEND_DOWN:
                        if (stockMoneyFlowData.getMainMoneyFlow() > 0) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_UP:
                        if (stockMoneyFlowData.getMainMoneyFlow() < 0) {
                            matchJudgeRule = false;
                        }
                        break;
                    case TEND_RANDOM:
                        break;
                    default:
                        break;
                }

                mainDaysMoneyFlow += stockMoneyFlowData.getMainMoneyFlow();
            }

            if (matchJudgeRule && moneyAllPositive && mainDaysMoneyFlow >= targetMoneyNum) {
                filterStockMoneyFlowBeanList.add(stockMoneyFlowBean);
            }
        }
        return filterStockMoneyFlowBeanList;
    }

    @Override
    public String getFilterRuleMsg() {
        StringBuffer filterRuleMsg = new StringBuffer("主力资金判断: ");
        filterRuleMsg.append("总资金量为[").append(targetMoneyNum).append("], ");
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
