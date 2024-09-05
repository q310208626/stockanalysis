package com.person.lsj.stock.service;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StockDataResultService {
    public void addStockDataResult(List<StockDataResultSum> stockFilterTasksResultList);

    public void addStockDataResult(Map<String, StockDataResultSum> stockFilterTasksResultMap);

    public StockDataResultSum queryStockDataResult(int resultId);

    public List<StockDataResultSum> queryStockDataResult(LocalDate collectDate);

    public StockDataResultSum queryStockDataResultByTaskIdAndDate(String taskId, LocalDate collectDate);

    public boolean judgeExistByTaskIdAndDate(String taskId, LocalDate collectDate);

    public void updateStockDataResult(StockDataResultSum stockDataResultSum);

    public void updateStockDataResults(List<StockDataResultSum> stockDataResultSum);

    /**
     * 获取最近一个工作日
     * @return
     */
    LocalDate getLatestWorkDay();
}
