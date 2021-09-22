package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.dto.CoinDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import com.google.gson.*;


import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinGeckoUpdateServiceTest {
    private final Logger logger = LoggerFactory.getLogger(CoinGeckoUpdateServiceTest.class);
    private static URL URL;

    static {
        try {
            URL = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&category=cryptocurrency&order=market_cap_desc&per_page=250&page=1&sparkline=false");
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }


    //@BeforeAll
    public void setup(){
        Map<String, Double> assetMap = new HashMap<>();

    }

    @Test
    public void updateRates() {
        RootRepository mockRepo = Mockito.mock(RootRepository.class);
        CoinGeckoUpdateService updateService = new CoinGeckoUpdateService(mockRepo);
        Map<String, Double> currentRates = new HashMap<>();
        currentRates.put("HEX", 0.18);
        currentRates.put("DOGE", 0.15);
        currentRates.put("ADA", 0.25);
        Mockito.when(mockRepo.getAllCurrentRates()).thenReturn(currentRates);

        double expected = currentRates.get("HEX");
    }

    @Test
    public void getRates() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CoinDto[] result = mapper.readValue(URL,CoinDto[].class);
            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i].toString());
                System.out.println(result[i].getCurrent_price());
            }

            assertThat(result.length >0);
        } catch (IOException error) {
            logger.info(error.getMessage());
        }
    }
}
