package com.person.lsj.stock.controller;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.JobConstants;
import com.person.lsj.stock.service.StockDataCapturerService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CastUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/curDayJob")
public class StockCurDayJobController {
    private static Logger LOGGER = Logger.getLogger(StockCurDayJobController.class);

    private static final Integer STATE_FREE = 0;

    private static final Integer STATE_RUNNING = 2;

    private static final Integer STATE_ERROR = 4;

    @Autowired
    private StockDataCapturerService stockDataCapturerService;

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/trigger")
    public String triggerJob() {
        try {
            JobKey jobKey = JobKey.jobKey(JobConstants.JOB_NAME_CURRENT_DAY_DATA_RESULT);
            synchronized (this) {
                // job is running, return
                for (JobExecutionContext currentlyExecutingJob : scheduler.getCurrentlyExecutingJobs()) {
                    if (currentlyExecutingJob.getJobDetail().getKey().equals(jobKey)) {
                        return STATE_RUNNING.toString();
                    }
                }

                scheduler.triggerJob(jobKey);
            }
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
            return STATE_ERROR.toString();
        }
        return STATE_RUNNING.toString();
    }

    @GetMapping("/result")
    public CurDayJobResult getCurDayJobResult() {
        try {
            Optional<JobDetail> jobDetail = Optional.of(scheduler.getJobDetail(JobKey.jobKey(JobConstants.JOB_NAME_CURRENT_DAY_DATA_RESULT)));
            if (!jobDetail.isPresent()) {
                return null;
            }

            // get job results
            Map<String, StockDataResultSum> stockDataResultSumMap =
                    (Map<String, StockDataResultSum>) jobDetail.get().getJobDataMap().get(JobConstants.RESULT_MAP);
            Map<String, String> jobDetailsMap =
                    CastUtils.cast(jobDetail.get().getJobDataMap().get(JobConstants.JOB_DETAILS_MAP));
            String executeTime = jobDetailsMap.get(JobConstants.JOB_DETAILS_KEY_EXE_TIME);

            CurDayJobResult curDayJobResult = new CurDayJobResult();
            if (CollectionUtils.isEmpty(stockDataResultSumMap)) {
                return curDayJobResult;
            }
            curDayJobResult.setExecuteTime(executeTime);
            curDayJobResult.setStockFilterTasksResultMap(stockDataResultSumMap);

            return curDayJobResult;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
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
            JobKey jobKey = JobKey.jobKey(JobConstants.JOB_NAME_CURRENT_DAY_DATA_RESULT);

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
