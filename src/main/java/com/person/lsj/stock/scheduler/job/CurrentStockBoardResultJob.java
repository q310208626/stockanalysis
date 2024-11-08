package com.person.lsj.stock.scheduler.job;

import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.Constant;
import com.person.lsj.stock.constant.CustomDateFormat;
import com.person.lsj.stock.constant.JobConstants;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.scheduler.task.StockDataFilterTasks;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrentStockBoardResultJob implements Job {
    private static Logger LOGGER = Logger.getLogger(NewStockDataCaptureJob.class);

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.debug("CurrentStockBoardResultJob start[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "]");
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        // get money flow data
        Map<String, StockDetailsData> stockBoardsV6Details = stockDataCapturerService.getStockBoardsV6Details();
        Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();
        for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);

            // check if task for stock code
            if (stockDetailsDataFilterChain.getFlag() != Constant.TASK_FLAG_STOCK_BOARD) {
                continue;
            }

            stockDetailsDataFilterChain.setStockDetailsDataMap(stockBoardsV6Details);
        }
        stockDataFilterTasks.processTasks(Constant.TASK_FLAG_STOCK_BOARD);

        Map<String, StockDataResultSum> stockFilterTasksResultMap = stockDataFilterTasks.getStockFilterTasksResultMap();

        // fill result data
        for (StockDataResultSum stockDataResultSum : stockFilterTasksResultMap.values()) {
            List<String> stockCodes = stockDataResultSum.getStockDataResultDetailsList().stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            Map<String, StockCurDetailsData> stockCodesCurDayDetail = stockDataCapturerService.getStockCodesCurDayDetail(stockCodes);

            // set desc
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(stockDataResultSum.getTaskId());
            stockDataResultSum.setDesc(stockDetailsDataFilterChain.getFilterRuleMsgs());

            // set current time data
            stockDataResultSum
                    .getStockDataResultDetailsList()
                    .forEach(x -> {
                        StockCurDetailsData stockCurDetailsData = stockCodesCurDayDetail.get(x.getStockCode());
                        x.setStockName(stockCurDetailsData.getStockName());
                        x.setOpen(Float.valueOf(stockCurDetailsData.getOpen()));
                        x.setClose(Float.valueOf(stockCurDetailsData.getCur()));
                        x.setIncrease(Float.valueOf(stockCurDetailsData.getIncreasePercentage()));
                    });
        }

        // set result
        Map<String, StockDataResultSum> outerStockFilterTasksResultMap = (Map<String, StockDataResultSum>) jobDataMap.get(JobConstants.RESULT_MAP);
        outerStockFilterTasksResultMap.clear();
        outerStockFilterTasksResultMap.putAll(stockFilterTasksResultMap);

        //set job details
        Map<String, String> jobDetailsMap = (Map<String, String>) jobDataMap.get(JobConstants.JOB_DETAILS_MAP);
        jobDetailsMap.put(JobConstants.JOB_DETAILS_KEY_EXE_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern(CustomDateFormat.DATE_TIME_FORMAT)));
        LOGGER.debug("CurrentStockBoardResultJob End Normally");
    }
}
