package com.cs601f18.weather.Job;

import com.cs601f18.weather.Objects.City;
import com.cs601f18.weather.Services.CityDataService;
import com.cs601f18.weather.Services.WeatherDataService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataService.class);
    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private WeatherDataService weatherDataService;

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Weather sync job");
        List<City> cityList = null;

        try {
            cityList = cityDataService.listCity();
        } catch (Exception e) {
            LOGGER.error("Exception!", e);
        }

        for (City city : cityList) {
            System.out.println(city.getCityId());
            String cityId = city.getCityId();
            LOGGER.info("Weather Data Sync Job, cityId is : " + cityId);
            weatherDataService.getDataByCityId(cityId);
        }

        LOGGER.info("Weather Data Sync Job start!");
    }

}
