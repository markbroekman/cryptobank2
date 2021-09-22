package com.example.cryptobank.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailError extends Exception {
    private final Logger logger = LoggerFactory.getLogger(EmailError.class);

    public EmailError() {
        logger.info("New EmailError");
    }

    public EmailError(String message) {
        super(message);
    }

}