package net.engineeringdigest.journalApp.Api.Response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {


    private Current current;

    @Data
    public class Current {

        private int temperature;

        private List<String> weather_descriptions;

        private int feelslike;


    }
}



