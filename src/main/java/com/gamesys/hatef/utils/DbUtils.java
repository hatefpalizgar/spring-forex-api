package com.gamesys.hatef.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.hatef.model.PriceCandle;
import com.gamesys.hatef.service.CandleService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * This class is responsible for refreshing database every 1 minute by fetching data from external API
 */

@Slf4j
@Component
@EnableScheduling
public class DbUtils {
    
    @Value("${api.access.key}")
    private String accessKey;
    
    @Value("${api.currency.pair}")
    private String currencyPair;
    
    @Value("${app.default.timeZone}")
    private String timeZone;
    
    @Autowired
    private CandleService service;
    
    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    @PostConstruct
    public void update() throws UnirestException, JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get("https://api.twelvedata.com/time_series?interval=1min")
                                                 .header("accept", "application/json")
                                                 .queryString("symbol", currencyPair)
                                                 .queryString("format", "JSON")
                                                 .queryString("apikey", accessKey)
                                                 .queryString("timezone", timeZone)
                                                 .asJson();
        List<PriceCandle> allCandles = getCandlesFrom(response);
        allCandles.forEach(c -> service.save(c));
        log.info("API call successful: database updated ");
    }
    
    private List<PriceCandle> getCandlesFrom(HttpResponse<JsonNode> response) throws JsonProcessingException {
        JSONArray jsonArray = getJsonArrayFrom(response);
        return mapToList(jsonArray);
    }
    
    private JSONArray getJsonArrayFrom(HttpResponse<JsonNode> response) {
        log.info("extracting price candles from API response");
        return (JSONArray) response.getBody().getObject().get("values");
    }
    
    private List<PriceCandle> mapToList(JSONArray jsonArray) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonArray.toString(), new TypeReference<List<PriceCandle>>() {
        });
    }
}
