package com.person.lsj.stock.scheduler.job;

import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.CustomDateFormat;
import com.person.lsj.stock.constant.JobConstants;
import com.person.lsj.stock.filter.StockDetailsDataFilterChain;
import com.person.lsj.stock.scheduler.task.StockDataFilterTasks;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class CurrentDayDataResultJob implements Job {
    private static Logger LOGGER = Logger.getLogger(NewStockDataCaptureJob.class);

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.debug("NewStockDataCaptureJob start[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "]");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        if (CollectionUtils.isEmpty(allStockCodes)) {
            LOGGER.debug("NewStockDataCaptureJob End With Empty Stock Codes");
            return;
        }

        // get money flow data
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes);
        Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();
        for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);
            // money flow filter
            stockDetailsDataFilterChain.setStockMoneyFlowBeanList(stockMoneyFlowDataList);
            List<StockMoneyFlowBean> targetMoneyFlowBeanList = stockDetailsDataFilterChain.doFilterMoneyFlow();
            List<String> targetStockCodeList = targetMoneyFlowBeanList.stream().map(x -> x.getStockCode()).collect(Collectors.toList());
            for (int i = targetMoneyFlowBeanList.size() - 1; i >= 0; i--) {
                StockMoneyFlowBean stockMoneyFlowBean = targetMoneyFlowBeanList.get(i);
                if (stockMoneyFlowBean.getStockCode() == null) {
                    System.out.println("stockCode is null" + i + ":" + stockMoneyFlowBean.getStockCode());
                }
            }

            // v6 details filter
            Map<String, StockDetailsData> stockCodesV6DetailMap = stockDataCapturerService.getStockCodesV6Detail(targetStockCodeList);
            stockDetailsDataFilterChain.setStockDetailsDataMap(stockCodesV6DetailMap);
        }
        stockDataFilterTasks.processTasks();

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
        LOGGER.debug("NewStockDataCaptureJob End Normally");
    }
}