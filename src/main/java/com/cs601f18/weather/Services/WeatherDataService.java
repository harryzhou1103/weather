package com.cs601f18.weather.Services;

import com.cs601f18.weather.Objects.Weather;
import com.cs601f18.weather.Objects.WeatherResponse;

public interface WeatherDataService {

    WeatherResponse getDataByCityId(String cityId);

    WeatherResponse getDataByCityName(String cityName);
}
