package com.person.lsj.stock.configuration;

import com.person.lsj.stock.bean.dongfang.result.StockDataResultSum;
import com.person.lsj.stock.constant.JobConstants;
import com.person.lsj.stock.scheduler.job.*;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class QuartzConfiguration {

    @Bean(name = "newStockDataCaptureJobTrigger")
    public Trigger newStockDataCaptureJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(newStockDataCaptureJob())
                .withIdentity(JobConstants.JOB_NAME_NEW_STOCK_DATA_CAPTURE_JOB)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 5 15 * * ? *"))
                .build();
    }

    @Bean(name = "stockDataResultJobTrigger")
    public Trigger stockDataResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(stockDataResultJob())
                .withIdentity(JobConstants.JOB_NAME_STOCK_DATA_RESULT_JOB)
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 15 * * ? *"))
                .startNow()
                .build();
    }

    @Bean(name = "currentDayDataResultJobTrigger")
    public Trigger currentDayDataResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(currentDayDataResultJob())
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_DATA_RESULT)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 10-15 * * ? *"))
                .build();
    }

    @Bean(name = "currentStockBoardIndustryResultJobTrigger")
    public Trigger currentStockBoardIndustryResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(currentStockBoardIndustryResultJob())
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_BOARD_INDUSTRY_RESULT_JOB)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 10-15 * * ? *"))
                .build();
    }

    @Bean(name = "currentStockBoardConceptResultJobTrigger")
    public Trigger currentStockBoardConceptResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(currentStockBoardConceptResultJob())
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_BOARD_CONCEPT_RESULT_JOB)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/10 10-15 * * ? *"))
                .build();
    }

    @Bean(name = "newStockDataCaptureJob")
    public JobDetail newStockDataCaptureJob() {
        return JobBuilder
                .newJob(NewStockDataCaptureJob.class)
                .withIdentity(JobConstants.JOB_NAME_NEW_STOCK_DATA_CAPTURE_JOB)
                .storeDurably()
                .build();
    }

    @Bean(name = "StockDataResultJob")
    public JobDetail stockDataResultJob() {
        return JobBuilder
                .newJob(StockDataResultJob.class)
                .withIdentity(JobConstants.JOB_NAME_STOCK_DATA_RESULT_JOB)
                .storeDurably()
                .build();
    }

    @Bean(name = "currentDayDataResultJob")
    public JobDetail currentDayDataResultJob() {
        Map<String, StockDataResultSum> stockFilterTasksResultMap = new LinkedHashMap<>();
        Map<String, String> jobDetailsMap = new HashMap<>();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstants.RESULT_MAP, stockFilterTasksResultMap);
        jobDataMap.put(JobConstants.JOB_DETAILS_MAP, jobDetailsMap);

        return JobBuilder
                .newJob(CurrentDayDataResultJob.class)
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_DATA_RESULT)
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean(name = "currentStockBoardIndustryResultJob")
    public JobDetail currentStockBoardIndustryResultJob() {
        Map<String, StockDataResultSum> stockFilterTasksResultMap = new LinkedHashMap<>();
        Map<String, String> jobDetailsMap = new HashMap<>();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstants.RESULT_MAP, stockFilterTasksResultMap);
        jobDataMap.put(JobConstants.JOB_DETAILS_MAP, jobDetailsMap);

        return JobBuilder
                .newJob(CurrentStockBoardIndustryResultJob.class)
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_BOARD_INDUSTRY_RESULT_JOB)
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean(name = "currentStockBoarddConceptResultJob")
    public JobDetail currentStockBoardConceptResultJob() {
        Map<String, StockDataResultSum> stockFilterTasksResultMap = new LinkedHashMap<>();
        Map<String, String> jobDetailsMap = new HashMap<>();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConstants.RESULT_MAP, stockFilterTasksResultMap);
        jobDataMap.put(JobConstants.JOB_DETAILS_MAP, jobDetailsMap);

        return JobBuilder
                .newJob(CurrentStockBoardConceptResultJob.class)
                .withIdentity(JobConstants.JOB_NAME_CURRENT_DAY_BOARD_CONCEPT_RESULT_JOB)
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }
}
