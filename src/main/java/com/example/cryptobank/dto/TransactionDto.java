package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TransactionDto {

    private int transactionId;
    private String ibanBuyer;
    private String ibanSeller;
    private String assetAbbr;
    private double assetAmount;
    private double singleAssetPrice;
    private double transactionCost;
    private LocalDateTime dateTimeProcessed;
    private final Logger logger = LoggerFactory.getLogger(TransactionDto.class);

    public TransactionDto(int transactionId, String ibanBuyer, String ibanSeller, String assetAbbr, double assetAmount,
                          double singleAssetPrice, double transactionCost, LocalDateTime dateTimeProcessed) {
        this.transactionId = transactionId;
        this.ibanBuyer = ibanBuyer;
        this.ibanSeller = ibanSeller;
        this.assetAbbr = assetAbbr;
        this.assetAmount = assetAmount;
        this.singleAssetPrice = singleAssetPrice;
        this.transactionCost = transactionCost;
        this.dateTimeProcessed = dateTimeProcessed;
        logger.info("New TransactionDto");
    }

    public TransactionDto() {
        logger.info("New empty TransactionDto");
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getIbanBuyer() {
        return ibanBuyer;
    }

    public void setIbanBuyer(String ibanBuyer) {
        this.ibanBuyer = ibanBuyer;
    }

    public String getIbanSeller() {
        return ibanSeller;
    }

    public void setIbanSeller(String ibanSeller) {
        this.ibanSeller = ibanSeller;
    }

    public String getAssetAbbr() {
        return assetAbbr;
    }

    public void setAssetAbbr(String assetAbbr) {
        this.assetAbbr = assetAbbr;
    }

    public double getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public double getSingleAssetPrice() {
        return singleAssetPrice;
    }

    public void setSingleAssetPrice(double singleAssetPrice) {
        this.singleAssetPrice = singleAssetPrice;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public LocalDateTime getDateTimeProcessed() {
        return dateTimeProcessed;
    }

    public void setDateTimeProcessed(LocalDateTime dateTimeProcessed) {
        this.dateTimeProcessed = dateTimeProcessed;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "ibanBuyer='" + ibanBuyer + '\'' +
                ", ibanSeller='" + ibanSeller + '\'' +
                ", assetAbbr='" + assetAbbr + '\'' +
                ", assetAmount=" + assetAmount +
                ", singleAssetPrice=" + singleAssetPrice +
                ", transactionCost=" + transactionCost +
                ", dateTimeProcessed=" + dateTimeProcessed +
                '}';
    }
}