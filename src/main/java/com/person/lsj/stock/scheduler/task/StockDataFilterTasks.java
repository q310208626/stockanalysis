package com.person.lsj.stock.scheduler.task;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.enumeration.RESULT;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StockDataFilterTasks {
    // task map
    private Map<String, StockDetailsDataFilterChain> stockFilterTasksMap;

    // task result map
    private Map<String, StockDataResultSum> stockFilterTasksResultMap;

    /**
     * process tasks base on filter chain
     */
    public void processTasks() {
        if (CollectionUtils.isEmpty(stockFilterTasksMap)) {
            return;
        }

        Iterator<Map.Entry<String, StockDetailsDataFilterChain>> iterator = stockFilterTasksMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, StockDetailsDataFilterChain> dataFilterChainEntry = iterator.next();
            String taskId = dataFilterChainEntry.getKey();
            StockDetailsDataFilterChain stockDetailsDataFilterChain = dataFilterChainEntry.getValue();
            Map<String, StockDetailsData> stockDetailsDataMap = stockDetailsDataFilterChain.doFilter();
            addToResultMap(taskId, stockDetailsDataMap);
        }
    }

    private void addToResultMap(String taskId, Map<String, StockDetailsData> stockDetailsDataMap) {
        if (CollectionUtils.isEmpty(stockFilterTasksMap) || CollectionUtils.isEmpty(stockDetailsDataMap)) {
            return;
        }

        stockFilterTasksResultMap = stockFilterTasksResultMap == null ? CollectionUtils.newHashMap(stockFilterTasksMap.size()) : stockFilterTasksResultMap;
        StockDataResultSum StockDataResultSum = new StockDataResultSum();
        StockDataResultSum.setTaskId(taskId);
        StockDataResultSum.setCollectDate(LocalDate.now());

        Set<String> stockCodes = stockDetailsDataMap.keySet();
        for (String stockCode : stockCodes) {
            StockDataResultDetails stockDataResultDetails = new StockDataResultDetails();
            stockDataResultDetails.setStockCode(stockCode);
            stockDataResultDetails.setResult(RESULT.UNKNOW.result);
            StockDataResultSum.getStockDataResultDetailsList().add(stockDataResultDetails);
        }

        stockFilterTasksResultMap.put(taskId,StockDataResultSum);
    }

    public Map<String, StockDetailsDataFilterChain> getStockFilterTasksMap() {
        return stockFilterTasksMap;
    }

    public void setStockFilterTasksMap(Map<String, StockDetailsDataFilterChain> stockFilterTasksMap) {
        this.stockFilterTasksMap = stockFilterTasksMap;
    }

    public Map<String, StockDataResultSum> getStockFilterTasksResultMap() {
        return stockFilterTasksResultMap;
    }

    public void setStockFilterTasksResultMap(Map<String, StockDataResultSum> stockFilterTasksResultMap) {
        this.stockFilterTasksResultMap = stockFilterTasksResultMap;
    }
}
