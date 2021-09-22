package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rate {
    private double cryptoRate;
    private String currency;
    private final Logger logger = LoggerFactory.getLogger(Rate.class);

    public Rate(String currency, double cryptoRate) {
        this.currency = currency;
        this.cryptoRate = cryptoRate;
        logger.info("New Rate");
    }

    public Rate() {
    }

    public double getCryptoRate() {
        return cryptoRate;
    }

    public void setCryptoRate(double cryptoRate) {
        this.cryptoRate = cryptoRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
