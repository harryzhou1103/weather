package com.cs601f18.weather.Services;

import com.cs601f18.weather.Objects.City;

import java.util.List;

public interface CityDataService {

    public List<City> listCity() throws Exception;
}
