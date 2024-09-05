package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.dao.StockDataResultDetailsMapper;
import com.person.lsj.stock.dao.StockDataResultMapper;
import com.person.lsj.stock.enumeration.RESULT;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockDataResultTest {

    @Autowired
    private StockDataResultMapper stockDataResultMapper;

    @Autowired
    private StockDataResultDetailsMapper stockDataResultDetailsMapper;

    @Test
    @Transactional
    public void addStockDataResult() {
        StockDataResultSum stockDataResult = new StockDataResultSum();
        stockDataResult.setTaskId("Task_0001");
        stockDataResult.setCollectDate(LocalDate.now());
        stockDataResultMapper.add(stockDataResult);

        StockDataResultDetails stockDataResultDetails1 = new StockDataResultDetails();
        stockDataResultDetails1.setStockCode("test0001");
        stockDataResultDetails1.setResult(RESULT.UNKNOW.result);
        stockDataResultDetails1.setResultId(stockDataResult.getResultId());

        StockDataResultDetails stockDataResultDetails2 = new StockDataResultDetails();
        stockDataResultDetails2.setStockCode("test0002");
        stockDataResultDetails2.setResult(RESULT.UNKNOW.result);
        stockDataResultDetails2.setResultId(stockDataResult.getResultId());

        List<StockDataResultDetails> stockDataResultDetailsList = List.of(stockDataResultDetails1, stockDataResultDetails2);
        stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);
    }

    @Test
    @Transactional
    public void addBatchStockDataResult() {
        StockDataResultSum stockDataResult1 = new StockDataResultSum();
        stockDataResult1.setTaskId("Task_0001");
        stockDataResult1.setCollectDate(LocalDate.now());

        StockDataResultSum stockDataResult2 = new StockDataResultSum();
        stockDataResult2.setTaskId("Task_0002");
        stockDataResult2.setCollectDate(LocalDate.now());

        List<StockDataResultSum> stockDataResultSumList = List.of(stockDataResult1, stockDataResult2);
        stockDataResultMapper.addBatch(stockDataResultSumList);

        for (StockDataResultSum stockDataResultSum : stockDataResultSumList) {
            StockDataResultDetails stockDataResultDetails1 = new StockDataResultDetails();
            stockDataResultDetails1.setStockCode("test0001");
            stockDataResultDetails1.setResult(RESULT.UNKNOW.result);
            stockDataResultDetails1.setResultId(stockDataResultSum.getResultId());

            StockDataResultDetails stockDataResultDetails2 = new StockDataResultDetails();
            stockDataResultDetails2.setStockCode("test0002");
            stockDataResultDetails2.setResult(RESULT.UNKNOW.result);
            stockDataResultDetails2.setResultId(stockDataResultSum.getResultId());
            List<StockDataResultDetails> stockDataResultDetailsList = List.of(stockDataResultDetails1, stockDataResultDetails2);
            stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);
        }
    }

    @Test
    @Transactional
    public void quertStockDataResultById() {
        StockDataResultSum stockDataResult = new StockDataResultSum();
        stockDataResult.setTaskId("Task_0001");
        stockDataResult.setCollectDate(LocalDate.now());
        stockDataResultMapper.add(stockDataResult);

        StockDataResultSum queryResult = stockDataResultMapper.queryById(stockDataResult.getResultId());
        Assert.assertEquals(1, queryResult);
    }

    @Test
    @Transactional
    public void queryStockDataResultByTaskIdAndCollectDate() {
        addBatchStockDataResult();

        StockDataResultSum stockDataResult = new StockDataResultSum();
        stockDataResult.setTaskId("Task_0001");
        stockDataResult.setCollectDate(LocalDate.now());
        StockDataResultSum stockDataResultSum = stockDataResultMapper.queryBytaskIdAndDate(stockDataResult);
        Assert.assertNotNull(stockDataResultSum);
        Assert.assertEquals(2, stockDataResultSum.getStockDataResultDetailsList().size());
    }

    @Test
    @Transactional
    public void quertStockDataResultByDate() {
        addBatchStockDataResult();

        StockDataResultSum stockDataResult = new StockDataResultSum();
        stockDataResult.setCollectDate(LocalDate.now());
        List<StockDataResultSum> stockDataResultSums = stockDataResultMapper.queryByDate(stockDataResult);
        Assert.assertNotNull(stockDataResultSums);
        Assert.assertEquals(2, stockDataResultSums.size());
        Assert.assertEquals(2, stockDataResultSums.get(0).getStockDataResultDetailsList().size());
    }

    @Test
    @Transactional
    public void updateOne() {
        StockDataResultSum stockDataResult = new StockDataResultSum();
        stockDataResult.setTaskId("Task_0001");
        stockDataResult.setCollectDate(LocalDate.now());
        stockDataResultMapper.add(stockDataResult);

        StockDataResultSum queryResult = stockDataResultMapper.queryById(stockDataResult.getResultId());
        queryResult.setAccuracyRate(60.2f);

        stockDataResultMapper.updateOne(queryResult);

        StockDataResultSum queryResult2 = stockDataResultMapper.queryById(stockDataResult.getResultId());
        Assert.assertEquals(60.2f, queryResult2.getAccuracyRate(), 0);
    }

    @Test
    @Transactional
    public void updateBatch() {
        StockDataResultSum stockDataResult1 = new StockDataResultSum();
        stockDataResult1.setTaskId("Task_0001");
        stockDataResult1.setCollectDate(LocalDate.now());

        StockDataResultSum stockDataResult2 = new StockDataResultSum();
        stockDataResult2.setTaskId("Task_0002");
        stockDataResult2.setCollectDate(LocalDate.now());

        List<StockDataResultSum> stockDataResultSumList = List.of(stockDataResult1, stockDataResult2);
        stockDataResultMapper.addBatch(stockDataResultSumList);

        StockDataResultSum queryResultBefore1 = stockDataResultMapper.queryById(stockDataResult1.getResultId());
        queryResultBefore1.setAccuracyRate(60.2f);
        StockDataResultSum queryResultBefore2 = stockDataResultMapper.queryById(stockDataResult2.getResultId());
        queryResultBefore2.setAccuracyRate(55.3f);
        stockDataResultMapper.updateBatch(List.of(queryResultBefore1, queryResultBefore2));

        StockDataResultSum queryResultAfter1 = stockDataResultMapper.queryById(stockDataResult1.getResultId());
        Assert.assertEquals(60.2f, queryResultAfter1.getAccuracyRate(), 0);
        StockDataResultSum queryResultAfter2 = stockDataResultMapper.queryById(stockDataResult2.getResultId());
        Assert.assertEquals(55.3f, queryResultAfter2.getAccuracyRate(), 0);

    }
}
