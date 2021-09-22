package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OudeCoinDto {
    private String abbreviation;
    //private double cryptoRate;
    //private String currency;
    private Rate rate;
    private final Logger logger = LoggerFactory.getLogger(OudeCoinDto.class);

    public OudeCoinDto(String abbreviation, Rate rate) {
        this.abbreviation = abbreviation;
        this.rate = rate;
        logger.info("New CoinDto");
    }

    public OudeCoinDto() { }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "CoinDto{" +
                "abbreviation='" + abbreviation + '\'' +
                ", rate=" + rate +
                '}';
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */



