package com.person.lsj.stock.service.impl;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.dao.StockDataResultDetailsMapper;
import com.person.lsj.stock.dao.StockDataResultMapper;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.scheduler.task.StockDataFilterTasks;
import com.person.lsj.stock.service.StockDataResultService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StockDataResultServiceImpl implements StockDataResultService {
    private static Logger LOGGER = Logger.getLogger(StockDataResultService.class);

    @Autowired
    private StockDataResultMapper stockDataResultMapper;

    @Autowired
    private StockDataResultDetailsMapper stockDataResultDetailsMapper;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Override
    @Transactional
    public void addStockDataResult(List<StockDataResultSum> stockFilterTasksResultList) {
        LOGGER.debug("enter addStockDataResult:" + stockFilterTasksResultList == null ? 0 : stockFilterTasksResultList.size());

        stockDataResultMapper.addBatch(stockFilterTasksResultList);

        for (StockDataResultSum stockDataResultSum : stockFilterTasksResultList) {
            stockDataResultSum.getStockDataResultDetailsList().forEach(x -> {
                x.setResultId(stockDataResultSum.getResultId());
            });
            stockDataResultDetailsMapper.addBatch(stockDataResultSum.getStockDataResultDetailsList());
        }
        LOGGER.debug("exit addStockDataResult");
    }

    @Override
    @Transactional
    public void addStockDataResult(Map<String, StockDataResultSum> stockFilterTasksResultMap) {
        LOGGER.debug("enter addStockDataResult:" + stockFilterTasksResultMap == null ? 0 : stockFilterTasksResultMap.size());

        List<StockDataResultSum> stockFilterTasksResultList = new ArrayList<>();
        stockFilterTasksResultMap.values().forEach(stockFilterTasksResultList::add);
        stockDataResultMapper.addBatch(stockFilterTasksResultList);

        for (StockDataResultSum stockDataResultSum : stockFilterTasksResultList) {
            stockDataResultSum.getStockDataResultDetailsList().forEach(x -> {
                x.setResultId(stockDataResultSum.getResultId());
            });
            stockDataResultDetailsMapper.addBatch(stockDataResultSum.getStockDataResultDetailsList());
        }
        LOGGER.debug("exit addStockDataResult");
    }

    @Override
    public StockDataResultSum queryStockDataResult(int resultId) {
        LOGGER.debug("enter queryStockDataResult:" + resultId);

        StockDataResultSum stockDataResultSum = stockDataResultMapper.queryById(resultId);

        String taskId = stockDataResultSum.getTaskId();
        StockDetailsDataFilterChain stockDetailsDataFilterChain = stockDataFilterTasks.getStockFilterTasksMap().get(taskId);
        stockDataResultSum.setDesc(stockDetailsDataFilterChain == null ? "No Description" : stockDetailsDataFilterChain.getFilterRuleMsgs());

        LOGGER.debug("exit queryStockDataResult:" + stockDataResultSum != null);
        return stockDataResultSum;
    }

    @Override
    public List<StockDataResultSum> queryStockDataResult(LocalDate collectDate) {
        LOGGER.debug("enter queryStockDataResult:" + collectDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        StockDataResultSum stockDataResultSum = new StockDataResultSum();
        stockDataResultSum.setCollectDate(collectDate);

        List<StockDataResultSum> stockDataResultSumList = stockDataResultMapper.queryByDate(stockDataResultSum);
        for (StockDataResultSum dataResultSum : stockDataResultSumList) {
            String taskId = dataResultSum.getTaskId();
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockDataFilterTasks.getStockFilterTasksMap().get(taskId);
            if (null == stockDetailsDataFilterChain) {
                continue;
            }
            dataResultSum.setDesc(stockDetailsDataFilterChain.getFilterRuleMsgs());
        }

        LOGGER.debug("exit queryStockDataResult:" + stockDataResultSumList == null ? 0 : stockDataResultSumList.size());
        return stockDataResultSumList;
    }

    @Override
    public StockDataResultSum queryStockDataResultByTaskIdAndDate(String taskId, LocalDate collectDate) {
        LOGGER.debug("enter queryStockDataResult:" + taskId + ":" + collectDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

        StockDataResultSum stockDataResultSum = new StockDataResultSum();
        stockDataResultSum.setTaskId(taskId);
        stockDataResultSum.setCollectDate(collectDate);

        StockDataResultSum queryResult = stockDataResultMapper.queryBytaskIdAndDate(stockDataResultSum);

        LOGGER.debug("exit queryStockDataResult:" + queryResult != null);
        return queryResult;
    }

    @Override
    public boolean judgeExistByTaskIdAndDate(String taskId, LocalDate collectDate) {
        LOGGER.debug("enter queryStockDataResult:" + taskId + ":" + collectDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

        StockDataResultSum stockDataResultSum = new StockDataResultSum();
        stockDataResultSum.setTaskId(taskId);
        stockDataResultSum.setCollectDate(collectDate);

        StockDataResultSum queryResult = stockDataResultMapper.queryBytaskIdAndDate(stockDataResultSum);

        LOGGER.debug("exit queryStockDataResult:" + queryResult != null);
        return queryResult != null;
    }

    @Override
    @Transactional
    public void updateStockDataResult(StockDataResultSum stockDataResultSum) {
        if (stockDataResultSum == null) {
            return;
        }
        stockDataResultMapper.updateOne(stockDataResultSum);

        List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();
        if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
            return;
        }
        stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);
    }

    @Override
    @Transactional
    public void updateStockDataResults(List<StockDataResultSum> stockFilterTasksResultList) {
        LOGGER.debug("enter updateStockDataResults:" + stockFilterTasksResultList == null ? 0 : stockFilterTasksResultList.size());
        if (CollectionUtils.isEmpty(stockFilterTasksResultList)) {
            return;
        }

        stockDataResultMapper.updateBatch(stockFilterTasksResultList);

        for (StockDataResultSum stockDataResultSum : stockFilterTasksResultList) {
            List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();
            if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
                continue;
            }
            stockDataResultDetailsMapper.updateBatch(stockDataResultDetailsList);
        }
        LOGGER.debug("exit updateStockDataResults");
    }

    @Override
    public LocalDate getLatestWorkDay() {
        List<LocalDate> localDates = stockDataResultMapper.queryCollectDateDesc();
        if (CollectionUtils.isEmpty(localDates)) {
            return null;
        }

        LocalDate latestWorkDate = localDates.getFirst();
        return latestWorkDate;
    }
}
