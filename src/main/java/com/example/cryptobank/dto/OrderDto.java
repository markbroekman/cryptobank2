package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class OrderDto {

    private int orderId;
    private String iban;
    private String assetAbbr;
    private double assetAmount;
    private double desiredPrice;
    private LocalDateTime dateTimeCreated;
    private int orderType;

    private final Logger logger = LoggerFactory.getLogger(OrderDto.class);

    public OrderDto(int orderId, String iban, String assetAbbr, double assetAmount, double desiredPrice, LocalDateTime dateTimeCreated, int orderType) {
        this.orderId = orderId;
        this.iban = iban;
        this.assetAbbr = assetAbbr;
        this.assetAmount = assetAmount;
        this.desiredPrice = desiredPrice;
        this.dateTimeCreated = dateTimeCreated;
        this.orderType = orderType;
        logger.info("New OrderDto");
    }

    public OrderDto() {
        logger.info("New empty OrderDto");
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", iban='" + iban + '\'' +
                ", assetAbbr='" + assetAbbr + '\'' +
                ", assetAmount=" + assetAmount +
                ", desiredPrice=" + desiredPrice +
                ", dateTimeCreated=" + dateTimeCreated +
                ", orderType=" + orderType +
                '}';
    }
}