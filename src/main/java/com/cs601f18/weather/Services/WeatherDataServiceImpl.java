package com.cs601f18.weather.Services;

import com.cs601f18.weather.Objects.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private static final String QUERYURLBYCITYID = "http://wthrcdn.etouch.cn/weather_mini?citykey=";
    private static final String QUERYURLBYCITYNAME = "http://wthrcdn.etouch.cn/weather_mini?city=";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getDataByCityId(String cityId) {
        //http://wthrcdn.etouch.cn/weather_mini?citykey=10128060
        String url = QUERYURLBYCITYID + cityId;
        return doGetWeather(url);

    }

    public WeatherResponse getDataByCityName(String cityName) {
        //http://wthrcdn.etouch.cn/weather_mini?city=深圳
        String url = QUERYURLBYCITYNAME + cityName;

        return doGetWeather(url);
    }

    private WeatherResponse doGetWeather(String url) {
        WeatherResponse res = null;
        ResponseEntity<String> resString = restTemplate.getForEntity(url, String.class);

        String strBody = null;
        if (resString.getStatusCodeValue() == 200) {
            strBody = resString.getBody();
            System.out.println(strBody);
        }
        ObjectMapper mapper = new ObjectMapper();


        try {
            res = mapper.readValue(strBody, WeatherResponse.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }


}
