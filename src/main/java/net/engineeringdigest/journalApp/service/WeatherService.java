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

    public WeatherResponse getWeather(String city){
        String finalApi = appCache.APP_CACHE.get("weather_api").replace("<city>", city).replace("<apiKey>", apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
