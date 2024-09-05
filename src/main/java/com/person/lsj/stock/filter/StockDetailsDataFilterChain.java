package com.person.lsj.stock.filter;

import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class StockDetailsDataFilterChain {
    private String taskId;
    private List<StockDetailsDataFilter> stockDetailsDataFilters;
    private Map<String, StockDetailsData> stockDetailsDataMap;

    private MoneyFlowDataFilter moneyFlowDataFilter;
    private List<StockMoneyFlowBean> stockMoneyFlowBeanList;


    public StockDetailsDataFilterChain(List<StockDetailsDataFilter> stockDetailsDataFilters) {
        this.stockDetailsDataFilters = stockDetailsDataFilters;
    }

    public List<StockMoneyFlowBean> doFilterMoneyFlow() {
        if (CollectionUtils.isEmpty(stockMoneyFlowBeanList) || moneyFlowDataFilter == null) {
            return stockMoneyFlowBeanList;
        }

        List<StockMoneyFlowBean> afterFilterData = moneyFlowDataFilter.filter(stockMoneyFlowBeanList);
        return afterFilterData;
    }

    public Map<String, StockDetailsData> doFilter() {
        if (CollectionUtils.isEmpty(stockDetailsDataMap) || CollectionUtils.isEmpty(stockDetailsDataFilters)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> temp = Map.copyOf(stockDetailsDataMap);
        for (StockDetailsDataFilter stockDetailsDataFilter : stockDetailsDataFilters) {
            temp = stockDetailsDataFilter.filter(temp);
        }

        return temp;
    }

    public String getFilterRuleMsgs() {
        StringBuilder ruleMsgs = new StringBuilder();
        int msgCount = 1;
        if (moneyFlowDataFilter != null) {
            ruleMsgs.append(msgCount++).append(".");
            ruleMsgs.append(moneyFlowDataFilter.getFilterRuleMsg());
            ruleMsgs.append(System.lineSeparator());
        }

        for (StockDetailsDataFilter stockDetailsDataFilter : stockDetailsDataFilters) {
            String filterRuleMsg = stockDetailsDataFilter.getFilterRuleMsg();
            ruleMsgs.append(msgCount++).append(".");
            ruleMsgs.append(filterRuleMsg);
            ruleMsgs.append(System.lineSeparator());
        }
        return ruleMsgs.toString();
    }

    public Map<String, StockDetailsData> getStockDetailsDataMap() {
        return stockDetailsDataMap;
    }

    public void setStockDetailsDataMap(Map<String, StockDetailsData> stockDetailsDataMap) {
        this.stockDetailsDataMap = stockDetailsDataMap;
    }

    public MoneyFlowDataFilter getMoneyFlowDataFilter() {
        return moneyFlowDataFilter;
    }

    public void setMoneyFlowDataFilter(MoneyFlowDataFilter moneyFlowDataFilter) {
        this.moneyFlowDataFilter = moneyFlowDataFilter;
    }

    public List<StockMoneyFlowBean> getStockMoneyFlowBeanList() {
        return stockMoneyFlowBeanList;
    }

    public void setStockMoneyFlowBeanList(List<StockMoneyFlowBean> stockMoneyFlowBeanList) {
        this.stockMoneyFlowBeanList = stockMoneyFlowBeanList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<StockDetailsDataFilter> getStockDetailsDataFilters() {
        return stockDetailsDataFilters;
    }

    public void setStockDetailsDataFilters(List<StockDetailsDataFilter> stockDetailsDataFilters) {
        this.stockDetailsDataFilters = stockDetailsDataFilters;
    }
}
