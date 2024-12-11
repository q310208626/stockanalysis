package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.scheduler.job.NewStockDataCaptureJob;
import com.person.lsj.stock.scheduler.task.StockDataFilterTasks;
import com.person.lsj.stock.service.StockDataCapturerService;
import com.person.lsj.stock.service.StockDataResultService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
public class StockFilterAccuracyTest {
    private static Logger LOGGER = Logger.getLogger(NewStockDataCaptureJob.class);

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Autowired
    private StockDataResultService stockDataResultService;

    private int testDay = 30;

    @Test
    public void testStockCode() {
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes, testDay + 10);
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(allStockCodes);
        Map<String,Float> resultMap = new HashMap<>();
        Map<String,Boolean> taskMatch = new HashMap<>();
        Map<String,Integer> matchCount = new HashMap<>();
        Map<String,Integer> totalCount = new HashMap<>();

        // 循环60次，查看Filter对于历史数据的预测正确率
        for (int daysAgo = 1; daysAgo <= testDay; daysAgo++) {
            Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();


            for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
                StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);

                // check if task for stock code
                if (stockDetailsDataFilterChain.getFlag() != Constant.TASK_FLAG_STOCK_CODE) {
                    continue;
                }

                // money flow filter
                stockDetailsDataFilterChain.setStockMoneyFlowBeanList(stockMoneyFlowDataList);
                List<StockMoneyFlowBean> targetMoneyFlowBeanList = stockDetailsDataFilterChain.doFilterMoneyFlow(daysAgo);
                List<String> targetStockCodeList = targetMoneyFlowBeanList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
                for (int i = targetMoneyFlowBeanList.size() - 1; i >= 0; i--) {
                    StockMoneyFlowBean stockMoneyFlowBean = targetMoneyFlowBeanList.get(i);
                    if (stockMoneyFlowBean.getStockCode() == null) {
                        System.out.println("stockCode is null" + i + ":" + stockMoneyFlowBean.getStockCode());
                    }
                }

                Map<String, StockDetailsData> tempStockCodesV6DetailsMap = new HashMap();
                targetStockCodeList.stream().forEach(stockCode->{
                    tempStockCodesV6DetailsMap.put(stockCode, stockCodesV6Detail.get(stockCode));
                });
                // v6 details filter
                stockDetailsDataFilterChain.setStockDetailsDataMap(tempStockCodesV6DetailsMap);
            }
            stockDataFilterTasks.processTasks(Constant.TASK_FLAG_STOCK_CODE, daysAgo);
            Map<String, StockDataResultSum> stockFilterTasksResultMap = stockDataFilterTasks.getStockFilterTasksResultMap();
            // 统计本次的各个任务的预测正确率
            for (String taskId : stockFilterTasksResultMap.keySet()) {
                StockDataResultSum stockDataResultSum = stockFilterTasksResultMap.get(taskId);
                List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();
                int stockCount = stockDataResultDetailsList.size();

                if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
                    continue;
                }

                totalCount.put(taskId, totalCount.getOrDefault(taskId, 0) + stockCount);
                taskMatch.put(taskId, true);
                for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                    String stockCode = stockDataResultDetails.getStockCode();
                    StockDetailsData stockDetailsData = stockCodesV6Detail.get(stockCode);

                    // 获取下一天的数据
                    StockDataEntity nextStockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - daysAgo);
                    float increasePercentage = nextStockDataEntity.getIncreasePercentage();
                    if (increasePercentage > 0) {
                        matchCount.put(taskId, matchCount.getOrDefault(taskId, 0) + 1);
                    }
                    LOGGER.debug(taskId + ":" + nextStockDataEntity.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + stockCode + ":");
                }
            }
        }

        for (String taskId : totalCount.keySet()) {
            resultMap.put(taskId, 1.0f * matchCount.get(taskId) / totalCount.getOrDefault(taskId, 1));
        }

        resultMap.forEach((taskId, resultMapValue) -> {
            System.out.println(taskId + ":" + resultMapValue);
        });
    }
}
