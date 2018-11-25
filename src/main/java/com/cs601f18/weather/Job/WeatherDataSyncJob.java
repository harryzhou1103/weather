package com.cs601f18.weather.Job;

import com.cs601f18.weather.Services.WeatherDataService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataService.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Weather sync job");
    }

}
