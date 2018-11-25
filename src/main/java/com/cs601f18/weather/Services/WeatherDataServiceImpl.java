package com.cs601f18.weather.Services;

import com.cs601f18.weather.Objects.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataService.class);
    private static final String QUERY_URL_BY_CITYID = "http://wthrcdn.etouch.cn/weather_mini?citykey=";
    private static final String QUERY_URL_BY_CITYNAME = "http://wthrcdn.etouch.cn/weather_mini?city=";
    private static final long TIMEOUT = 10L;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public WeatherResponse getDataByCityId(String cityId) {
        //http://wthrcdn.etouch.cn/weather_mini?citykey=10128060
        String url = QUERY_URL_BY_CITYID + cityId;
        return doGetWeather(url);

    }

    public WeatherResponse getDataByCityName(String cityName) {
        //http://wthrcdn.etouch.cn/weather_mini?city=深圳
        String url = QUERY_URL_BY_CITYNAME + cityName;

        return doGetWeather(url);
    }

    private WeatherResponse doGetWeather(String url) {
        WeatherResponse res = null;
        String key = null;
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String strBody = null;
        ObjectMapper mapper = new ObjectMapper();
        if (stringRedisTemplate.hasKey(key)) {
            LOGGER.info("Redis has data");
            strBody = operations.get(key);
        }
        else {
            LOGGER.info("Redis does not have data, will go to fetch the data");
            ResponseEntity<String> resString = restTemplate.getForEntity(url, String.class);
            if (resString.getStatusCodeValue() == 200) {
                strBody = resString.getBody();
            }
            operations.set(key, strBody, TIMEOUT, TimeUnit.SECONDS);
        }


        try {
            res = mapper.readValue(strBody, WeatherResponse.class);
        }
        catch (IOException e) {
//            e.printStackTrace();
            LOGGER.error("Error!", e);
        }
        return res;

    }


}
