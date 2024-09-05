package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockTaskTest {
    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void bollTaskTest() {
        List<String> allStockCodes = new ArrayList<>();

        List<String> stockCodes = stockDataCapturerService.getStockCode(1);
        allStockCodes.addAll(stockCodes);


        // get money flow data
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes);
        StockDetailsDataFilterChain stockDetailsDataFilterChain = applicationContext.getBean("stockDetailsDataFilterChainBollDvalue002", StockDetailsDataFilterChain.class);

        // money flow filter
        stockDetailsDataFilterChain.setStockMoneyFlowBeanList(stockMoneyFlowDataList);
        List<StockMoneyFlowBean> targetMoneyFlowBeanList = stockDetailsDataFilterChain.doFilterMoneyFlow();
        List<String> targetStockCodeList = targetMoneyFlowBeanList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());

        // v6 details filter
        Map<String, StockDetailsData> stockCodesV6DetailMap = stockDataCapturerService.getStockCodesV6Detail(targetStockCodeList);
        stockDetailsDataFilterChain.setStockDetailsDataMap(stockCodesV6DetailMap);
        stockDetailsDataFilterChain.doFilter();
    }

    @Test
    public void bollTaskTestSingleStockCode() {

        StockDetailsDataFilterChain stockDetailsDataFilterChain = applicationContext.getBean("stockDetailsDataFilterChainBollDvalue002", StockDetailsDataFilterChain.class);

        List<String> targetStockCodeList = new ArrayList<>();
        targetStockCodeList.add("600346");
        // v6 details filter
        Map<String, StockDetailsData> stockCodesV6DetailMap = stockDataCapturerService.getStockCodesV6Detail(targetStockCodeList);
        stockDetailsDataFilterChain.setStockDetailsDataMap(stockCodesV6DetailMap);
        stockDetailsDataFilterChain.doFilter();
    }
}
