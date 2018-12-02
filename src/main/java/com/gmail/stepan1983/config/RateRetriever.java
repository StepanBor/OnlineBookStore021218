package com.gmail.stepan1983.config;

import com.gmail.stepan1983.model.Rate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RateRetriever {

    private static final String URL = "http://apilayer.net/api/live?access_key=8742691146d4666a721d276747be45ab&currencies=UAH,USD,EUR,RUB";

    @Cacheable("rates")
    public Rate getRate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Rate> response = restTemplate.getForEntity(URL, Rate.class);
        System.out.println(ConsoleColors.PURPLE+response.getBody()+ConsoleColors.RESET);
        return response.getBody();
    }
}
