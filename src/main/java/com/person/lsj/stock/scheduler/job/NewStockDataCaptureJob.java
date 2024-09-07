package com.person.lsj.stock.scheduler.job;

import com.person.lsj.stock.bean.dongfang.data.StockDetailsData;
import com.person.lsj.stock.bean.dongfang.moneyflow.StockMoneyFlowBean;
import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class NewStockDataCaptureJob implements Job {
    private static Logger LOGGER = Logger.getLogger(NewStockDataCaptureJob.class);

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private StockDataFilterTasks stockDataFilterTasks;

    @Autowired
    private StockDataResultService stockDataResultService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.debug("NewStockDataCaptureJob start[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "]");
        List<String> allStockCodes = stockDataCapturerService.getAllStockCodes();
        if (CollectionUtils.isEmpty(allStockCodes)) {
            LOGGER.debug("NewStockDataCaptureJob End With Empty Stock Codes");
            return;
        }

        // get money flow data
        List<StockMoneyFlowBean> stockMoneyFlowDataList = stockDataCapturerService.getStockMoneyFlowData(allStockCodes);
        for (String taskId : stockDataFilterTasks.getStockFilterTasksMap().keySet()) {
            StockDetailsDataFilterChain stockDetailsDataFilterChain = stockDataFilterTasks.getStockFilterTasksMap().get(taskId);
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
        stockDataResultService.addStockDataResult(stockFilterTasksResultMap);
        LOGGER.debug("NewStockDataCaptureJob End Normally");
    }
}
