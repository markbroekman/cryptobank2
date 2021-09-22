package com.example.cryptobank.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocialSecurityError extends Exception {
    private final Logger logger = LoggerFactory.getLogger(SocialSecurityError.class);

    public SocialSecurityError() {
        logger.info("New SocialSecurityError");
    }

    public SocialSecurityError(String message) {
        super(message);
    }


}