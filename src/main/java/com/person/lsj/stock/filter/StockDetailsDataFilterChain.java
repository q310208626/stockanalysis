package com.person.lsj.stock.filter;

import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockDetailsDataFilterChain {
    private String taskId;
    private List<StockDetailsDataFilter> stockDetailsDataFilters;
    private Map<String, StockDetailsData> stockDetailsDataMap;

    private MoneyFlowDataFilter moneyFlowDataFilter;
    private List<StockMoneyFlowBean> stockMoneyFlowBeanList;

    // 0-for stock code  1-for stock board
    private int flag = 0;

    public StockDetailsDataFilterChain(List<StockDetailsDataFilter> stockDetailsDataFilters) {
        this.stockDetailsDataFilters = stockDetailsDataFilters;
    }

    public List<StockMoneyFlowBean> doFilterMoneyFlow() {
        return doFilterMoneyFlow(0);
    }

    public List<StockMoneyFlowBean> doFilterMoneyFlow(int fewDaysAgo) {
        if (CollectionUtils.isEmpty(stockMoneyFlowBeanList) || moneyFlowDataFilter == null) {
            return stockMoneyFlowBeanList;
        }

        List<StockMoneyFlowBean> afterFilterData = moneyFlowDataFilter.filter(stockMoneyFlowBeanList, fewDaysAgo);
        return afterFilterData;
    }

    public Map<String, StockDetailsData> doFilter() {
        return doFilter(0);
    }

    public Map<String, StockDetailsData> doFilter(int fewDaysAgo) {
        if (CollectionUtils.isEmpty(stockDetailsDataMap) || CollectionUtils.isEmpty(stockDetailsDataFilters)) {
            return stockDetailsDataMap;
        }

        Map<String, StockDetailsData> temp = new HashMap<String, StockDetailsData>();
        temp.putAll(stockDetailsDataMap);
        for (StockDetailsDataFilter stockDetailsDataFilter : stockDetailsDataFilters) {
            temp = stockDetailsDataFilter.filter(temp, fewDaysAgo);
        }

        stockDetailsDataMap.clear();
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
