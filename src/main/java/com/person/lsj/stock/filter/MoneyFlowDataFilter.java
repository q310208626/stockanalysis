package com.person.lsj.stock.filter;

import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;

import java.util.List;

public interface MoneyFlowDataFilter {
    List<StockMoneyFlowBean> filter(List<StockMoneyFlowBean> stockMoneyFlowBeans);

    List<StockMoneyFlowBean> filter(List<StockMoneyFlowBean> stockMoneyFlowBeans, int fewDaysAgo);

    String getFilterRuleMsg();
}
