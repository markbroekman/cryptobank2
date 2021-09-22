package com.example.cryptobank.controller;

import com.example.cryptobank.service.CoinGeckoUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@RestController
public class UpdateController {
    private CoinGeckoUpdateService updateService;
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    public UpdateController(CoinGeckoUpdateService updateService) {
        this.updateService = updateService;
        logger.info("New UpdateController");
    }

    @GetMapping("/getRate")
    public void getRate() {
        updateService.updateRates();


//        RestTemplate restTemplate = new RestTemplate();
//        LinkedHashMap newRate = restTemplate.getForObject(
//                "https://api.coingecko.com/api/v3/simple/price?ids=" + assetName + "&vs_currencies=usd", LinkedHashMap.class);
//        newRate.forEach((e,t) -> System.out.println(e.toString()+" "+t.toString()));
////        CryptoCurrencyRate currencyRate = new CryptoCurrencyRate(
//                newRate.getAbbreviation(), newRate.getCryptoRate());
        //vervolgens opslaan in database


    }



}
