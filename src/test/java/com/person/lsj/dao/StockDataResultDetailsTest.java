package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.dao.StockDataResultDetailsMapper;
import com.person.lsj.stock.enumeration.RESULT;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockDataResultDetailsTest {

    @Autowired
    StockDataResultDetailsMapper stockDataResultDetailsMapper;

    @Test
    @Transactional
    public void addStockDataResultDetails() {
        StockDataResultDetails stockDataResultDetails = new StockDataResultDetails();
        stockDataResultDetails.setStockCode("test0001");
        stockDataResultDetails.setResult(RESULT.UNKNOW.result);
        stockDataResultDetailsMapper.add(stockDataResultDetails);
        Assert.assertNotEquals(0, stockDataResultDetails.getDetailsId());
    }

    @Test
    @Transactional
    public void addBatchStockDataResultDetails() {
        StockDataResultDetails stockDataResultDetails1 = new StockDataResultDetails();
        stockDataResultDetails1.setStockCode("test0001");
        stockDataResultDetails1.setResult(RESULT.UNKNOW.result);

        StockDataResultDetails stockDataResultDetails2 = new StockDataResultDetails();
        stockDataResultDetails2.setStockCode("test0002");
        stockDataResultDetails2.setResult(RESULT.UNKNOW.result);

        List<StockDataResultDetails> stockDataResultDetailsList = List.of(stockDataResultDetails1, stockDataResultDetails2);

        stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);

        for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
            Assert.assertNotEquals(0, stockDataResultDetails.getDetailsId());
        }
    }

    @Test
    @Transactional
    public void queryStockDataResultDetails() {
        StockDataResultDetails stockDataResultDetails = new StockDataResultDetails();
        stockDataResultDetails.setStockCode("test0001");
        stockDataResultDetails.setResult(RESULT.UNKNOW.result);
        stockDataResultDetailsMapper.add(stockDataResultDetails);

        StockDataResultDetails stockDataResultDetailsQuery = stockDataResultDetailsMapper.query(stockDataResultDetails.getDetailsId());
        Assert.assertNotNull(stockDataResultDetailsQuery);
    }

    @Test
    @Transactional
    public void queryBatchStockDataResultDetails() {
        StockDataResultDetails stockDataResultDetails1 = new StockDataResultDetails();
        stockDataResultDetails1.setStockCode("test0001");
        stockDataResultDetails1.setResult(RESULT.UNKNOW.result);

        StockDataResultDetails stockDataResultDetails2 = new StockDataResultDetails();
        stockDataResultDetails2.setStockCode("test0002");
        stockDataResultDetails2.setResult(RESULT.UNKNOW.result);
        List<StockDataResultDetails> stockDataResultDetailsList = List.of(stockDataResultDetails1, stockDataResultDetails2);

        stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);

        List<Integer> detailsIds = List.of(stockDataResultDetails1.getDetailsId(), stockDataResultDetails2.getDetailsId());
        List<StockDataResultDetails> stockDataResultDetailsListQuery = stockDataResultDetailsMapper.queryBatch(detailsIds);
        Assert.assertEquals(2, stockDataResultDetailsListQuery.size());
    }

    @Test
    @Transactional
    public void updateOne() {
        StockDataResultDetails stockDataResultDetails = new StockDataResultDetails();
        stockDataResultDetails.setStockCode("test0001");
        stockDataResultDetails.setResult(RESULT.UNKNOW.result);
        stockDataResultDetailsMapper.add(stockDataResultDetails);

        stockDataResultDetails.setResult(RESULT.SUCC.result);
        stockDataResultDetailsMapper.update(stockDataResultDetails);

        StockDataResultDetails queried = stockDataResultDetailsMapper.query(stockDataResultDetails.getDetailsId());
        Assert.assertEquals(RESULT.SUCC.result,queried.getResult());
    }

    @Test
    @Transactional
    public void updateBatch() {
        StockDataResultDetails stockDataResultDetails1 = new StockDataResultDetails();
        stockDataResultDetails1.setStockCode("test0001");
        stockDataResultDetails1.setResult(RESULT.UNKNOW.result);
        stockDataResultDetails1.setOpen(10.1f);
        stockDataResultDetails1.setClose(11.3f);
        stockDataResultDetails1.setIncrease(1.2f);

        StockDataResultDetails stockDataResultDetails2 = new StockDataResultDetails();
        stockDataResultDetails2.setStockCode("test0002");
        stockDataResultDetails2.setResult(RESULT.UNKNOW.result);
        stockDataResultDetails1.setOpen(10.2f);
        stockDataResultDetails1.setClose(9.3f);
        stockDataResultDetails1.setIncrease(-1.2f);
        List<StockDataResultDetails> stockDataResultDetailsList = List.of(stockDataResultDetails1, stockDataResultDetails2);

        stockDataResultDetailsMapper.addBatch(stockDataResultDetailsList);

        stockDataResultDetails1.setResult(RESULT.SUCC.result);
        stockDataResultDetails2.setResult(RESULT.FAIL.result);
        stockDataResultDetailsMapper.updateBatch(List.of(stockDataResultDetails1, stockDataResultDetails2));

        StockDataResultDetails queried1 = stockDataResultDetailsMapper.query(stockDataResultDetails1.getDetailsId());
        StockDataResultDetails queried2 = stockDataResultDetailsMapper.query(stockDataResultDetails2.getDetailsId());
        Assert.assertEquals(RESULT.SUCC.result, queried1.getResult());
        Assert.assertEquals(RESULT.FAIL.result, queried2.getResult());
    }
}
