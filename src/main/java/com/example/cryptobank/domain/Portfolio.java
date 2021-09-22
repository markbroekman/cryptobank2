package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Logger logger = LoggerFactory.getLogger(Portfolio.class);
    private Map<String, Double> assetMap;

    public Portfolio() {
        this.assetMap = new HashMap<>();
        logger.info("New Portfolio");
    }

    public Map<String, Double> getAssetMap() {
        return assetMap;
    }

    public void setAssetMap(Map<String, Double> assetMap) {
        this.assetMap = assetMap;
    }
}
