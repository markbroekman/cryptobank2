package com.example.cryptobank.service;

import com.example.cryptobank.domain.Mail;

import javax.mail.MessagingException;

public interface SendMailService {
    void sendMail(Mail mail);

    void sendMailWithAttachments(Mail mail) throws MessagingException;
}
