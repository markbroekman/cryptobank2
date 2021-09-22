package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction {
    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    private int transactionId;
    private Asset asset;
    private double assetAmount;
    private double assetPrice; // per unit
    private BankAccount buyerAccount;
    private BankAccount sellerAccount;
    private double transactionCost;
    private LocalDateTime dateTimeTransaction;

    public Transaction(int transactionId, Asset asset, double assetAmount, double assetPrice, BankAccount buyerAccount,
                       BankAccount sellerAccount, double transactionCost, LocalDateTime dateTimeTransaction) {
        this.transactionId = transactionId;
        this.asset = asset;
        this.assetAmount = assetAmount;
        this.assetPrice = assetPrice;
        this.buyerAccount = buyerAccount;
        this.sellerAccount = sellerAccount;
        this.transactionCost = transactionCost;
        this.dateTimeTransaction = dateTimeTransaction;
        logger.info("New Transaction");
    }

    public Transaction() {
        logger.info("Empty transaction");
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public double getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(double assetPrice) {
        this.assetPrice = assetPrice;
    }

    public BankAccount getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(BankAccount buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public BankAccount getSellerAccount() {
        return sellerAccount;
    }

    public void setSellerAccount(BankAccount sellerAccount) {
        this.sellerAccount = sellerAccount;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public LocalDateTime getDateTimeTransaction() {
        return dateTimeTransaction;
    }

    public void setDateTimeTransaction(LocalDateTime dateTimeTransaction) {
        this.dateTimeTransaction = dateTimeTransaction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", asset=" + asset +
                ", assetAmount=" + assetAmount +
                ", assetPrice=" + assetPrice +
                ", buyerAccount=" + buyerAccount +
                ", sellerAccount=" + sellerAccount +
                ", transactionCost=" + transactionCost +
                ", dateTimeTransaction=" + dateTimeTransaction +
                '}';
    }
}