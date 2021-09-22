package com.example.cryptobank.service;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class makes a random IBAN according to the CryptoKnights format and has one static method generate() which
 * returns a string
 * @author Mark Broekman
 * */
@Service
public class IbanGenerator {
    private final static String BANKCODE = "COKI";

    private final Logger logger = LoggerFactory.getLogger(IbanGenerator.class);

    public IbanGenerator() {
        logger.info("New IbanGenerator");
    }

    /**
     * Generates a random IBAN according to Dutch banking standards
     * @return a random IBAN as a String
     */
    public static String generate() {
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode(BANKCODE).buildRandom();

        return iban.toString();
    }



}
