package com.person.lsj.stock.scheduler.job;

import com.person.lsj.stock.bean.dongfang.data.StockCurDetailsData;
import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultDetails;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.Constant;
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
import java.util.*;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class CurrentDayDataResultJob implements Job {
    private static Logger LOGGER = Logger.getLogger(NewStockDataCaptureJob.class);

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    private Map<String, StockDetailsData> tempStockDetailsDataMap = new HashMap<>();
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("CurrentDayDataResultJob start[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "]");
        long startTime = 0;
        long endTime = 0;
        try {
            startTime = System.currentTimeMillis();
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
            if (CollectionUtils.isEmpty(allStockCodes)) {
                LOGGER.debug("CurrentDayDataResultJob End With Empty Stock Codes");
                return;
            }

            // get money flow data
            List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes);
            Map<String, StockDetailsDataFilterChain> stockFilterTasksMap = stockDataFilterTasks.getStockFilterTasksMap();
            for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
                StockDetailsDataFilterChain stockDetailsDataFilterChain = stockFilterTasksMap.get(taskId);

                // check if task for stock code
                if (stockDetailsDataFilterChain.getFlag() != Constant.TASK_FLAG_STOCK_CODE) {
                    continue;
                }

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
                Iterator<String> targetStockCodeIterator = targetStockCodeList.iterator();
                List<String> stockCodeToQuery = new ArrayList<>();
                Map<String, StockDetailsData> stockCodesV6DetailMap = new HashMap<>();
                while (targetStockCodeIterator.hasNext()) {
                    String stockCode = targetStockCodeIterator.next();
                    if (stockCode == null) {
                        continue;
                    }

                    if (tempStockDetailsDataMap.get(stockCode) == null){
                        stockCodeToQuery.add(stockCode);
                    } else {
                        stockCodesV6DetailMap.put(stockCode, tempStockDetailsDataMap.get(stockCode));
                    }
                }
                Map<String, StockDetailsData> remainStockDetailsMap = stockDataCapturerService.getStockCodesV6Detail(stockCodeToQuery);
                tempStockDetailsDataMap.putAll(remainStockDetailsMap);
                stockCodesV6DetailMap.putAll(remainStockDetailsMap);
                stockDetailsDataFilterChain.setStockDetailsDataMap(stockCodesV6DetailMap);
            }
            stockDataFilterTasks.processTasks(Constant.TASK_FLAG_STOCK_CODE);

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

            // clear data
            stockDataFilterTasks.getStockFilterTasksMap().values().stream().filter(x->x.getFlag() == Constant.TASK_FLAG_STOCK_CODE).forEach(chain->{
                chain.getStockDetailsDataMap().clear();
                chain.getStockMoneyFlowBeanList().clear();
            });

            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            tempStockDetailsDataMap.clear();
            LOGGER.info("CurrentDayDataResultJob End Normally, cost:" + (endTime - startTime) + "ms");
        }
    }
}
