package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Roi {
    public double times;
    public String currency;
    public double percentage;
    private final Logger logger = LoggerFactory.getLogger(Roi.class);

    public Roi(double times, String currency, double percentage) {
        this.times = times;
        this.currency = currency;
        this.percentage = percentage;
        logger.info("New Roi");
    }

    public Roi() {
    }



    public double getTimes() {
        return times;
    }

    public void setTimes(double times) {
        this.times = times;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
