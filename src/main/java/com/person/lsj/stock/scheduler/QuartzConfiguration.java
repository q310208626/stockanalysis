package com.person.lsj.stock.scheduler;

import com.person.lsj.stock.scheduler.job.CurrentDayDataResultJob;
import com.person.lsj.stock.scheduler.job.NewStockDataCaptureJob;
import com.person.lsj.stock.scheduler.job.StockDataResultJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Bean(name = "newStockDataCaptureJobTrigger")
    public Trigger newStockDataCaptureJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(newStockDataCaptureJob())
                .withIdentity("newStockDataCaptureJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 5 15 * * ? *"))
                .build();
    }

    @Bean(name = "stockDataResultJobTrigger")
    public Trigger stockDataResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(stockDataResultJob())
                .withIdentity("stockDataResultJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 5 15 * * ? *"))
                .build();
    }

    @Bean(name = "currentDayDataResultJobTrigger")
    public Trigger currentDayDataResultJobTrigger() {
        return TriggerBuilder
                .newTrigger()
                .forJob(currentDayDataResultJob())
                .withIdentity("currentDayDataResultJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 10-15 * * ? *"))
                .build();
    }

    @Bean(name = "newStockDataCaptureJob")
    public JobDetail newStockDataCaptureJob() {
        return JobBuilder
                .newJob(NewStockDataCaptureJob.class)
                .withIdentity("newStockDataCaptureJob")
                .storeDurably()
                .build();
    }

    @Bean(name = "StockDataResultJob")
    public JobDetail stockDataResultJob() {
        return JobBuilder
                .newJob(StockDataResultJob.class)
                .withIdentity("stockDataResultJob")
                .storeDurably()
                .build();
    }

    @Bean(name = "currentDayDataResultJob")
    public JobDetail currentDayDataResultJob() {
        return JobBuilder
                .newJob(CurrentDayDataResultJob.class)
                .withIdentity("currentDayDataResultJob")
                .storeDurably()
                .build();
    }
}
