package com.example.cryptobank.security;

public interface CreateToken {

    String createAccessToken(String username, String key);
    String createRefreshToken(String username, String key);
    String createResetToken(String username, String key, String salt);
}
