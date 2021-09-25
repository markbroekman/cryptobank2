package com.example.cryptobank.security;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashHelper {

    public static final String SHA_256 = "SHA-256";
    public static final String ALGORITME_BESTAAT_NIET = "Het opgegeven algoritme bestaat niet";


    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return ByteArrayToHexHelper.encodeHexString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(ALGORITME_BESTAAT_NIET);
        }
    }

    public static String hash(String password, String salt){
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return ByteArrayToHexHelper.encodeHexString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(ALGORITME_BESTAAT_NIET);
        }
    }

    public static String hash(String password, String salt, String pepper){
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            md.update(pepper.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return ByteArrayToHexHelper.encodeHexString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(ALGORITME_BESTAAT_NIET);
        }
    }

}
