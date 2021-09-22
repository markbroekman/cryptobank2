package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwnedAssetDto {
    private final Logger logger = LoggerFactory.getLogger(OwnedAssetDto.class);

    String abbreviation;
    double ownedAssetAmount;
    String assetName;
    double currentSinglePrice;
    double subTotal;

    public OwnedAssetDto(String abbreviation, double ownedAssetAmount, String assetName,
                         double currentSinglePrice, double subTotal) {
        this.abbreviation = abbreviation;
        this.ownedAssetAmount = ownedAssetAmount;
        this.assetName = assetName;
        this.currentSinglePrice = currentSinglePrice;
        this.subTotal = subTotal;
        logger.info("New OADto");
    }

    public OwnedAssetDto() {

    }


    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public double getOwnedAssetAmount() {
        return ownedAssetAmount;
    }

    public void setOwnedAssetAmount(double ownedAssetAmount) {
        this.ownedAssetAmount = ownedAssetAmount;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public double getCurrentSinglePrice() {
        return currentSinglePrice;
    }

    public void setCurrentSinglePrice(double currentSinglePrice) {
        this.currentSinglePrice = currentSinglePrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "OADto{" +
                "abbreviation='" + abbreviation + '\'' +
                ", ownedAssetAmount=" + ownedAssetAmount +
                ", assetName='" + assetName + '\'' +
                ", currentSinglePrice=" + currentSinglePrice +
                ", subTotal=" + subTotal +
                '}';
    }
}