package com.example.cryptobank.dto;

import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetDto {
    private String abbreviation;
    private String name;
    private String description;
    private double rate;
    private final Logger logger = LoggerFactory.getLogger(AssetDto.class);

    public AssetDto() {
        logger.info("New AssetDto");
    }

    public AssetDto(String abbreviation, String name, String description, double rate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.description = description;
        this.rate = rate;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}