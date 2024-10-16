package com.person.lsj.stock.controller;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/curDayJob")
public class StockCurDayJobController {
    private static Logger LOGGER = Logger.getLogger(StockCurDayJobController.class);

    private static final String JOB_NAME = "currentDayDataResultJob";

    private static final String RESULT_MAP = "RESULT_MAP";

    private static final Integer STATE_FREE = 0;

    private static final Integer STATE_RUNNING = 2;

    private static final Integer STATE_ERROR = 4;

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private Scheduler scheduler;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, StockDataResultSum> stockFilterTasksResultMap = new HashMap<String, StockDataResultSum>();

    private LocalDateTime executeTime = null;

    @GetMapping("/trigger")
    public String triggerJob() {
        try {
            JobKey jobKey = new JobKey(JOB_NAME, null);
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(RESULT_MAP, stockFilterTasksResultMap);

            synchronized (this) {
                // job is running, return
                for (JobExecutionContext currentlyExecutingJob : scheduler.getCurrentlyExecutingJobs()) {
                    if (currentlyExecutingJob.getJobDetail().getKey().equals(jobKey)) {
                        return STATE_RUNNING.toString();
                    }
                }

                executeTime = LocalDateTime.now();
                scheduler.triggerJob(jobKey, jobDataMap);
            }
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
            return STATE_ERROR.toString();
        }
        return STATE_RUNNING.toString();
    }

    @GetMapping("/result")
    public CurDayJobResult getCurDayJobResult() {
        if (CollectionUtils.isEmpty(stockFilterTasksResultMap) || executeTime == null) {
            return null;
        }

        CurDayJobResult curDayJobResult = new CurDayJobResult();
        curDayJobResult.setExecuteTime(executeTime.format(dateTimeFormatter));
        curDayJobResult.setStockFilterTasksResultMap(stockFilterTasksResultMap);

        if (CollectionUtils.isEmpty(stockFilterTasksResultMap)) {
            return curDayJobResult;
        }
        return curDayJobResult;
    }

    class CurDayJobResult {
        private String executeTime;
        private Map<String, StockDataResultSum> stockFilterTasksResultMap;

        public String getExecuteTime() {
            return executeTime;
        }

        public void setExecuteTime(String executeTime) {
            this.executeTime = executeTime;
        }

        public Map<String, StockDataResultSum> getStockFilterTasksResultMap() {
            return stockFilterTasksResultMap;
        }

        public void setStockFilterTasksResultMap(Map<String, StockDataResultSum> stockFilterTasksResultMap) {
            this.stockFilterTasksResultMap = stockFilterTasksResultMap;
        }
    }

    @GetMapping("/state")
    public String getJobState() {
        String state = STATE_FREE.toString();
        try {
            JobKey jobKey = new JobKey(JOB_NAME, null);

            // job is running, return
            for (JobExecutionContext currentlyExecutingJob : scheduler.getCurrentlyExecutingJobs()) {
                if (currentlyExecutingJob.getJobDetail().getKey().equals(jobKey)) {
                    return STATE_RUNNING.toString();
                }
            }
        } catch (SchedulerException e) {
            state = STATE_ERROR.toString();
        }

        return state;
    }
}
