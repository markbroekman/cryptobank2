package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomerDto {

    private String username;
    private String password;
    private String accessToken;
    private String refreshToken;
    private String firstName;
    private String iban;
    private String email;
    private final Logger logger = LoggerFactory.getLogger(CustomerDto.class);

    public CustomerDto() {
        logger.info("New CustomerDto");
    }

    public CustomerDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CustomerDto(String username, String accessToken, String firstName, String iban, String email) {
        this.username = username;
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.iban = iban;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "username='" + username + '\'' +
                ", accesstoken='" + accessToken + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}