package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PortfolioDto {
    private String customerName;
    private List<OwnedAssetDto> list;
    private final Logger logger = LoggerFactory.getLogger(PortfolioDto.class);
    private double totalPortfolioValue;

    public PortfolioDto(String customerName, List<OwnedAssetDto> list, double totalPortfolioValue) {
        this.customerName = customerName;
        this.list = list;
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public PortfolioDto() {
        logger.info("New PortfolioDto");
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OwnedAssetDto> getList() {
        return list;
    }

    public void setList(List<OwnedAssetDto> list) {
        this.list = list;
    }

    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public void setTotalPortfolioValue(double totalPortfolioValue) {
        this.totalPortfolioValue = totalPortfolioValue;
    }

    @Override
    public String toString() {
        return "PortfolioDto{" +
                "customerName='" + customerName + '\'' +
                ", list=" + list +
                ", totalPortfolioValue=" + totalPortfolioValue +
                '}';
    }
}