package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Asset {
    private final Logger logger = LoggerFactory.getLogger(Asset.class);

    private String abbreviation;
    private String name;
    private String description;
    private CryptoCurrencyRate rate; // in dollar

    public Asset(String abbreviation, String name, String description, CryptoCurrencyRate rate) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.description = description;
        this.rate = rate;
        logger.info("New Asset");
    }

    public Asset() {
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

    public CryptoCurrencyRate getRate() {
        return rate;
    }

    public void setRate(CryptoCurrencyRate rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                '}';
    }
}
