package com.example.cryptobank.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsernameError extends Exception {
    private final Logger logger = LoggerFactory.getLogger(UsernameError.class);

    public UsernameError() {
        logger.info("New UsernameError");
    }

    public UsernameError(String message) {
        super(message);
    }

}