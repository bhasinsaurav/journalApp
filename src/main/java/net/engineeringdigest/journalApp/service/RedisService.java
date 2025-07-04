package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    public <T> T get(String key, Class<T> weatherResponseClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), weatherResponseClass);
        } catch(Exception e){
            log.error("Exception", e);
            return null;
        }
    }

    public  void set(String key, Object o, Long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);

        } catch(Exception e){
            log.error("Exception", e);

        }
    }
}
