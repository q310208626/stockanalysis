package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.data.StockBoardBean;
import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.financial.balance.BalanceDataItem;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.enumeration.TREND;
import com.person.lsj.stock.filter.dongfang.MdiStockDetailsDataFilter;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockDataCapturerServiceTest {
    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Test
    public void getLastWorkDay() {
        LocalDate lastWorkDay = stockDataCapturerService.getLastWorkDay();
        Assert.assertNotNull(lastWorkDay);
    }

    @Test
    public void getAllStockCodes() {
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        Assert.assertNotNull(allStockCodes);
    }

    @Test
    public void getStockMoneyFlow() {
        List<String> stockCodes = stockDataCapturerService.getStockCode(1);
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(stockCodes);
        Assert.assertNotNull(stockMoneyFlowDataList);
        Assert.assertEquals(20, stockMoneyFlowDataList.size());
    }

    @Test
    public void getStockBoardMoneyFlow() {
        List<StockBoardBean> allStockBoards = stockDataCapturerService.getAllStockBoards(Constant.BOARD_TYPE_INDUSTRY);
        List<String> stockCodes = allStockBoards.stream().map(x -> x.getBoardCode()).collect(Collectors.toList());
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(stockCodes);
        Assert.assertNotNull(stockMoneyFlowDataList);
        Assert.assertEquals(20, stockMoneyFlowDataList.size());
    }

    @Test
    public void getStockV6Details() {
        List<String> stockCodes = stockDataCapturerService.getStockCode(1);
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(stockCodes);
        Assert.assertNotNull(stockCodesV6Detail);
        Assert.assertEquals(20, stockCodesV6Detail.size());
    }

    @Test
    public void getStockCodesV6Details() {
        ArrayList<String> stockCodeList = new ArrayList<>();
        stockCodeList.add("600021");
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(stockCodeList);
        Assert.assertNotNull(stockCodesV6Detail);
    }

    @Test
    public void getStockCodesCurDetails() {
        ArrayList<String> stockCodeList = new ArrayList<>();
        stockCodeList.add("600426");
        Map<String, StockCurDetailsData> stockCodesCurDayDetail = stockDataCapturerService.getStockCodesCurDayDetail(stockCodeList);
        Assert.assertNotNull(stockCodesCurDayDetail);
    }

    @Test
    public void getStockStatus() {
        Integer stockStatus = stockDataCapturerService.getStockStatus(null);
        Assert.assertNull(stockStatus);
    }

    @Test
    public void getStockBoards() {
        List<StockBoardBean> allStockBoards = stockDataCapturerService.getAllStockBoards(Constant.BOARD_TYPE_INDUSTRY);
        Assert.assertNotNull(allStockBoards);
    }

    @Test
    public void getStockBoardsV6Details() {
        Map<String, StockDetailsData> stockBoardsV6Details = stockDataCapturerService.getStockBoardsV6Details(Constant.BOARD_TYPE_INDUSTRY);
        Assert.assertNotNull(stockBoardsV6Details);
    }

    @Test
    public void MdiFilterTest() {
        TREND[] pdiTrend = new TREND[]{TREND.TEND_UP};
        TREND[] mdiTrend = new TREND[]{TREND.TEND_DOWN};
        TREND[] adxTrend = new TREND[]{TREND.TEND_UP, TREND.TEND_UP};
        MdiStockDetailsDataFilter mdiStockDetailsDataFilter = new MdiStockDetailsDataFilter(pdiTrend, mdiTrend, adxTrend, 10.0f);
        ArrayList<String> stockCodeList = new ArrayList<>();
        stockCodeList.add("600938");
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(stockCodeList);
        Map<String, StockDetailsData> filterRet = mdiStockDetailsDataFilter.filter(stockCodesV6Detail);
        Assert.assertNotNull(filterRet);
    }

    @Test
    public void getStockFinancialData(){
        ArrayList<String> stockCodeList = new ArrayList<>();
        stockCodeList.add("600938");
        Map<String, List<BalanceDataItem>> stockFinancialDataBalance = stockDataCapturerService.getStockFinancialDataBalance(stockCodeList);
        Assert.assertNotNull(stockFinancialDataBalance);
    }
}
