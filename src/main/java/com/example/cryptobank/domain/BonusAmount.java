package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BonusAmount {
    private final Logger logger = LoggerFactory.getLogger(BonusAmount.class);
    private static double amountBonus = 0.0;
    public BonusAmount() {

        logger.info("New BonusAmount");
    }

    public static double getAmountBonus() {
        return amountBonus;
    }

    public static void setAmountBonus(double amountBonus) {
        BonusAmount.amountBonus = amountBonus;
    }
}