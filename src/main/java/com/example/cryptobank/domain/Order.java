package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Order {
    private final Logger logger = LoggerFactory.getLogger(Order.class);

    private int orderId;
    private BankAccount bankAccount;
    private LocalDateTime dateTimeCreated;
    private Asset asset;
    private double assetAmount;
    private double desiredPrice; // per unit
    private int orderType;


    public Order(int orderId, BankAccount bankAccount, LocalDateTime dateTimeCreated,
                 Asset asset, double assetAmount, double desiredPrice, int orderType) {
        this.orderId = orderId;
        this.bankAccount = bankAccount;
        this.dateTimeCreated = dateTimeCreated;
        this.asset = asset;
        this.assetAmount = assetAmount;
        this.desiredPrice = desiredPrice;
        this.orderType = orderType;
        logger.info("New Order");
    }

    public Order(BankAccount bankAccount, LocalDateTime dateTimeCreated, Asset asset,
                 double assetAmount, double desiredPrice, int orderType) {
        this(0, bankAccount, dateTimeCreated, asset, assetAmount, desiredPrice, orderType);
    }

    public Order() {
        logger.info("Empty Order");
    }

    //TODO klopt equals method nog wel na aanpassen attributen?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.assetAmount, assetAmount) != 0) return false;
        if (!dateTimeCreated.equals(order.dateTimeCreated)) return false;
        return asset.equals(order.asset);
    }

    //TODO klopt hashcode method nog wel na aanpassen attributen?
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = dateTimeCreated.hashCode();
        result = 31 * result + asset.hashCode();
        temp = Double.doubleToLongBits(assetAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", bankAccount=" + bankAccount +
                ", dateTimeCreated=" + dateTimeCreated +
                ", asset=" + asset +
                ", assetAmount=" + assetAmount +
                ", desiredPrice=" + desiredPrice +
                ", orderType=" + orderType +
                '}';
    }
} // end of class Order

