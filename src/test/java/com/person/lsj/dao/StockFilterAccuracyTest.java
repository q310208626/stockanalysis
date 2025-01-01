package com.person.lsj.dao;

import com.person.lsj.stock.ApplicationStarter;
import com.person.lsj.stock.bean.dongfang.data.StockBoardBean;
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

    private int testDay = 60;

    private int countDays = 3;

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

                    // 判断往后判断的天数会不会大于当前的天数，如果大于则跳过该数据
                    if (stockDetailsData.getStockDataEntities().size() - daysAgo < 0) {
                        continue;
                    }

                    // 获取下一天的数据
                    StockDataEntity nextStockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - daysAgo);
                    float increasePercentage = nextStockDataEntity.getIncreasePercentage();
                    boolean isUpNextday = false;
                    if (increasePercentage > 0) {
                        matchCount.put(taskId, matchCount.getOrDefault(taskId, 0) + 1);
                        isUpNextday = true;
                    }
                    LOGGER.debug(taskId + ":" + nextStockDataEntity.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + stockCode + ":" + isUpNextday);
                }
            }
        }

        for (String taskId : totalCount.keySet()) {
            resultMap.put(taskId, 1.0f * matchCount.getOrDefault(taskId, 0) / totalCount.getOrDefault(taskId, 1));
        }

        resultMap.forEach((taskId, resultMapValue) -> {
            System.out.println(taskId + ":" + resultMapValue);
        });
    }

    @Test
    public void testCountDaysAccurancy() {
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes, testDay + 10);
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(allStockCodes);
        Map<String, Float> resultMap = new HashMap<>();
        Map<String, Integer> matchCount = new HashMap<>();
        Map<String, Integer> totalCount = new HashMap<>();

        // task整体的涨幅数据
        Map<String, Float> totalIncrease = new HashMap<>();

        // task-stockCode-date 单一数据的涨幅
        Map<String, Float> singleIncrease = new HashMap<>();

        // task整体的平均涨幅
        Map<String, Float> increaseResultMap = new HashMap<>();

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
                targetStockCodeList.stream().forEach(stockCode -> {
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

                if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
                    continue;
                }

                for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                    String stockCode = stockDataResultDetails.getStockCode();
                    StockDetailsData stockDetailsData = stockCodesV6Detail.get(stockCode);

                    // 判断往后判断的天数会不会大于当前的天数，如果大于则跳过该数据
                    if (stockDetailsData.getStockDataEntities().size() - daysAgo - 1 < 0 || stockDetailsData.getStockDataEntities().size() - daysAgo + countDays >= stockDetailsData.getStockDataEntities().size()) {
                        continue;
                    }

                    // 总数统计+1
                    totalCount.put(taskId, totalCount.getOrDefault(taskId, 0) + 1);

                    // 统计后面几天的总涨幅
                    StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - 1 - daysAgo);
                    String singleKey = taskId + ":" + stockDataEntity.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + stockCode;
                    for (int countDay = 0; countDay < countDays; countDay++) {
                        // 获取下一天的数据
                        StockDataEntity nextStockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - daysAgo + countDay);
                        float increasePercentage = nextStockDataEntity.getIncreasePercentage();
                        totalIncrease.put(taskId, totalIncrease.getOrDefault(taskId, 0.00f) + increasePercentage);
                        singleIncrease.put(singleKey, singleIncrease.getOrDefault(singleKey, 0.00f) + increasePercentage);
                    }

                    boolean isUpNextday = false;
                    // 如果接下来的几天的涨幅大于0
                    if (singleIncrease.getOrDefault(singleKey, 0f) > 0) {
                        matchCount.put(taskId, matchCount.getOrDefault(taskId, 0) + 1);
                        isUpNextday = true;
                    }
                    LOGGER.debug(singleKey + ":" + isUpNextday + ":" + singleIncrease.getOrDefault(singleKey, 0f));
                }
            }
        }

        for (String taskId : totalCount.keySet()) {
            resultMap.put(taskId, 1.0f * matchCount.getOrDefault(taskId, 0) / totalCount.getOrDefault(taskId, 1));
            increaseResultMap.put(taskId, 1.0f * totalIncrease.getOrDefault(taskId, 0f) / totalCount.getOrDefault(taskId, 1));
        }

        resultMap.forEach((taskId, resultMapValue) -> {
            System.out.println(taskId + ":" + resultMapValue + ":" + increaseResultMap.getOrDefault(taskId, 0f));
        });
    }

    @Test
    public void testStockIndustryBoard() {
        List<StockBoardBean> allStockBoards = stockDataCapturerService.getAllStockBoards(Constant.BOARD_TYPE_INDUSTRY);
        List<String> allStockCodes = allStockBoards.stream().map(x -> x.getBoardCode()).collect(Collectors.toList());
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
                if (stockDetailsDataFilterChain.getFlag() != Constant.TASK_FLAG_STOCK_BOARD) {
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
            stockDataFilterTasks.processTasks(Constant.TASK_FLAG_STOCK_BOARD, daysAgo);
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

                    // 判断往后判断的天数会不会大于当前的天数，如果大于则跳过该数据
                    if (stockDetailsData.getStockDataEntities().size() - daysAgo < 0) {
                        continue;
                    }

                    // 获取下一天的数据
                    StockDataEntity nextStockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - daysAgo);
                    float increasePercentage = nextStockDataEntity.getIncreasePercentage();
                    boolean isUpNextday = false;
                    if (increasePercentage > 0) {
                        matchCount.put(taskId, matchCount.getOrDefault(taskId, 0) + 1);
                        isUpNextday = true;
                    }
                    LOGGER.debug(taskId + ":" + nextStockDataEntity.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + stockCode + ":" + isUpNextday);
                }
            }
        }

        for (String taskId : totalCount.keySet()) {
            resultMap.put(taskId, 1.0f * matchCount.getOrDefault(taskId, 0) / totalCount.getOrDefault(taskId, 1));
        }

        resultMap.forEach((taskId, resultMapValue) -> {
            System.out.println(taskId + ":" + resultMapValue);
        });
    }

    @Test
    public void testCountDaysStockIndustryBoardAccurancy() {
        List<StockBoardBean> allStockBoards = stockDataCapturerService.getAllStockBoards(Constant.BOARD_TYPE_INDUSTRY);
        List<String> allStockCodes = allStockBoards.stream().map(x -> x.getBoardCode()).collect(Collectors.toList());
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes, testDay + 10);
        Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(allStockCodes);
        Map<String, Float> resultMap = new HashMap<>();
        Map<String, Integer> matchCount = new HashMap<>();
        Map<String, Integer> totalCount = new HashMap<>();

        // task整体的涨幅数据
        Map<String, Float> totalIncrease = new HashMap<>();

        // task-stockCode-date 单一数据的涨幅
        Map<String, Float> singleIncrease = new HashMap<>();

        // task整体的平均涨幅
        Map<String, Float> increaseResultMap = new HashMap<>();

        // 循环60次，查看Filter对于历史数据的预测正确率
        for (int daysAgo = 1; daysAgo <= testDay; daysAgo++) {
            Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();


            for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
                StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);

                // check if task for stock code
                if (stockDetailsDataFilterChain.getFlag() != Constant.TASK_FLAG_STOCK_BOARD) {
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
                targetStockCodeList.stream().forEach(stockCode -> {
                    tempStockCodesV6DetailsMap.put(stockCode, stockCodesV6Detail.get(stockCode));
                });
                // v6 details filter
                stockDetailsDataFilterChain.setStockDetailsDataMap(tempStockCodesV6DetailsMap);
            }
            stockDataFilterTasks.processTasks(Constant.TASK_FLAG_STOCK_BOARD, daysAgo);
            Map<String, StockDataResultSum> stockFilterTasksResultMap = stockDataFilterTasks.getStockFilterTasksResultMap();
            // 统计本次的各个任务的预测正确率
            for (String taskId : stockFilterTasksResultMap.keySet()) {
                StockDataResultSum stockDataResultSum = stockFilterTasksResultMap.get(taskId);
                List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();

                if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
                    continue;
                }

                for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
                    String stockCode = stockDataResultDetails.getStockCode();
                    StockDetailsData stockDetailsData = stockCodesV6Detail.get(stockCode);

                    // 判断往后判断的天数会不会大于当前的天数，如果大于则跳过该数据
                    if (stockDetailsData.getStockDataEntities().size() - daysAgo - 1 < 0 || stockDetailsData.getStockDataEntities().size() - daysAgo + countDays >= stockDetailsData.getStockDataEntities().size()) {
                        continue;
                    }

                    // 总数统计+1
                    totalCount.put(taskId, totalCount.getOrDefault(taskId, 0) + 1);

                    // 统计后面几天的总涨幅
                    StockDataEntity stockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - 1 - daysAgo);
                    String singleKey = taskId + ":" + stockDataEntity.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + stockCode;
                    for (int countDay = 0; countDay < countDays; countDay++) {
                        // 获取下一天的数据
                        StockDataEntity nextStockDataEntity = stockDetailsData.getStockDataEntities().get(stockDetailsData.getStockDataEntities().size() - daysAgo + countDay);
                        float increasePercentage = nextStockDataEntity.getIncreasePercentage();
                        totalIncrease.put(taskId, totalIncrease.getOrDefault(taskId, 0.00f) + increasePercentage);
                        singleIncrease.put(singleKey, singleIncrease.getOrDefault(singleKey, 0.00f) + increasePercentage);
                    }

                    boolean isUpNextday = false;
                    // 如果接下来的几天的涨幅大于0
                    if (singleIncrease.getOrDefault(singleKey, 0f) > 0) {
                        matchCount.put(taskId, matchCount.getOrDefault(taskId, 0) + 1);
                        isUpNextday = true;
                    }
                    LOGGER.debug(singleKey + ":" + isUpNextday + ":" + singleIncrease.getOrDefault(singleKey, 0f));
                }
            }
        }

        for (String taskId : totalCount.keySet()) {
            resultMap.put(taskId, 1.0f * matchCount.getOrDefault(taskId, 0) / totalCount.getOrDefault(taskId, 1));
            increaseResultMap.put(taskId, 1.0f * totalIncrease.getOrDefault(taskId, 0f) / totalCount.getOrDefault(taskId, 1));
        }

        resultMap.forEach((taskId, resultMapValue) -> {
            System.out.println(taskId + ":" + resultMapValue + ":" + increaseResultMap.getOrDefault(taskId, 0f));
        });
    }
}
