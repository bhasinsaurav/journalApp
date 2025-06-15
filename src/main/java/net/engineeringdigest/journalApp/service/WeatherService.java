package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }
        else{
            String finalApi = appCache.APP_CACHE.get("weather_api").replace("<city>", city).replace("<apiKey>", apiKey);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();

            if(body!=null){
                redisService.set("weather_of_" + city, body,300l);
            }
            return body;
        }
            }
}
