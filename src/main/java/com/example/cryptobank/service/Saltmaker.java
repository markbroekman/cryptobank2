package com.example.cryptobank.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class Saltmaker {

    private static final int SALT_LENGHT = 8;
    private int saltLength;
    private SecureRandom secureRandom;

    public Saltmaker(int saltLength){
        this.saltLength = saltLength;
        secureRandom = new SecureRandom();
    }

    public Saltmaker(){
        this(SALT_LENGHT);
    }

    public String generateSalt() {
        int tempLengte = saltLength / 2;
        byte[] arr = new byte[saltLength % 2 == 0 ? tempLengte : tempLengte + 1];
        secureRandom.nextBytes(arr);
        String salt =  ByteArrayToHexHelper.encodeHexString(arr);
        return saltLength % 2 == 0 ? salt : salt.substring(1);
    }

    public void setSaltLength(int saltLength){
        this.saltLength = saltLength;
    }
}
