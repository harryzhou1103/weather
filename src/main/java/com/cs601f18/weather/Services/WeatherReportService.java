package com.cs601f18.weather.Services;

import com.cs601f18.weather.Objects.Weather;

public interface WeatherReportService {


    Weather getDataByCityId(String cityId);

}
