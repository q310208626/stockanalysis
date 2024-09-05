package com.person.lsj.stock.scheduler.job;

import com.person.lsj.stock.bean.dongfang.data.StockDataEntity;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.enumeration.RESULT;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.scheduler.task.StockDataFilterTasks;
import com.person.lsj.stock.service.StockDataCapturerService;
import com.person.lsj.stock.service.StockDataResultService;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class StockDataResultJob implements Job {
    private static Logger LOGGER = Logger.getLogger(StockDataResultJob.class);

    @Autowired
    private StockDataResultService stockDataResultService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    private Executor executor =
            new ThreadPoolExecutor(Constant.CPU_CORE_COUNT * 2, Constant.CPU_CORE_COUNT * 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200));

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.debug("NewStockDataCaptureJob start[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "]");

        Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();
        Set<String> taskIdSet = stockFilterTasksMap.keySet();

        Map<String, StockDataResultSum> yesterDayResultMap = new HashMap();

        LocalDate today = LocalDate.now();
        LocalDate lastWorkDay = stockDataCapturerService.getLastWorkDay();
        for (String taskId : taskIdSet) {
            StockDataResultSum stockDataResultSum = stockDataResultService.queryStockDataResultByTaskIdAndDate(taskId, lastWorkDay);

            if (stockDataResultSum == null) {
                continue;
            }

            yesterDayResultMap.put(taskId, stockDataResultSum);
        }

        List<StockDataResultSum> stockDataResultSumList = yesterDayResultMap.values().stream().collect(Collectors.toList());
        for (StockDataResultSum stockDataResultSum : stockDataResultSumList) {
            List<StockDataResultDetails> stockDataResultDetailsList = stockDataResultSum.getStockDataResultDetailsList();

            if (CollectionUtils.isEmpty(stockDataResultDetailsList)) {
                continue;
            }

            // set filter rule msg
            String taskId = stockDataResultSum.getTaskId();
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);
            String filterRuleMsgs = stockDetailsDataFilterChain.getFilterRuleMsgs();
            stockDataResultSum.setDesc(filterRuleMsgs);

            List<String> sotckCodeList = stockDataResultDetailsList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            Map<String, StockDetailsData> stockCodesV6Detail = stockDataCapturerService.getStockCodesV6Detail(sotckCodeList);
            float accuracyRate = setDetailsResult(stockDataResultDetailsList, stockCodesV6Detail, today);
            stockDataResultSum.setAccuracyRate(accuracyRate);
        }
        stockDataResultService.updateStockDataResults(stockDataResultSumList);
    }

    private static float setDetailsResult(List<StockDataResultDetails> stockDataResultDetailsList, Map<String, StockDetailsData> stockCodesV6Detail, LocalDate today) {
        int totalCount = stockDataResultDetailsList.size();
        int upCount = 0;
        for (StockDataResultDetails stockDataResultDetails : stockDataResultDetailsList) {
            String stockCode = stockDataResultDetails.getStockCode();
            StockDetailsData stockDetailsData = stockCodesV6Detail.get(stockCode);
            List<StockDataEntity> stockDataEntities = stockDetailsData.getStockDataEntities();

            for (int i = stockDataEntities.size() - 1; i >= 0; i--) {
                StockDataEntity stockDataEntity = stockDataEntities.get(i);
                LocalDate stockDataEntityTime = stockDataEntity.getTime();

                int result = RESULT.FAIL.result;
                if (today.isEqual(stockDataEntityTime)) {
                    if (stockDataEntity.getClose() > stockDataEntity.getOpen()) {
                        upCount++;
                        result = RESULT.SUCC.result;
                    }
                    stockDataResultDetails.setResult(result);
                    stockDataResultDetails.setOpen(stockDataEntity.getOpen());
                    stockDataResultDetails.setClose(stockDataEntity.getClose());
                    stockDataResultDetails.setIncrease(stockDataEntity.getIncreasePercentage());
                    break;
                }
            }
        }
        float accuracyRate = upCount * 1.0f * 100 / totalCount;
        return accuracyRate;
    }
}

