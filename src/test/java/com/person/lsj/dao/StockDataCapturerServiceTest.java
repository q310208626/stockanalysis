package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockDataCapturerServiceTest {
    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Test
    public void getLastWorkDay(){
        LocalDate lastWorkDay = stockDataCapturerService.getLastWorkDay();
        Assert.assertNotNull(lastWorkDay);
    }

    @Test
    public void getAllStockCodes(){
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        Assert.assertNotNull(allStockCodes);
    }

    @Test
    public void getStockMoneyFlow(){
        List<String> stockCodes = stockDataCapturerService.getStockCode(1);
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(stockCodes);
        Assert.assertNotNull(stockMoneyFlowDataList);
        Assert.assertEquals(20, stockMoneyFlowDataList.size());
    }

    @Test
    public void getStockV6Details(){
        List<String> stockCodes = stockDataCapturerService.getStockCode(1);
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(stockCodes);
        Assert.assertNotNull(stockCodesV6Detail);
        Assert.assertEquals(20, stockCodesV6Detail.size());
    }

    @Test
    public void getStockCodesV6Details(){
        ArrayList<String> stockCodeList = new ArrayList<>();
        stockCodeList.add("600021");
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(stockCodeList);
        Assert.assertNotNull(stockCodesV6Detail);
    }
}
