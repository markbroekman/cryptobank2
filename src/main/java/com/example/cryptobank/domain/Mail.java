package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {
    private final Logger logger = LoggerFactory.getLogger(Mail.class);
    private String recipient;
    private String subject;
    private String message;

    public Mail(String recipient, String subject, String message) {
        logger.info("New Mail");
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public Mail() {
        logger.info("New Mail");
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}