package com.person.lsj.stock.filter;

import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;

import java.util.Map;

public interface StockDetailsDataFilter {
    Map<String, StockDetailsData> filter(Map<String, StockDetailsData> stockDetailsDataMap);

    String getFilterRuleMsg();
}
