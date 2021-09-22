package com.example.cryptobank.security;


public class TokenKeyService {
    private static final String ADMIN_KEY = "tercesNimda";
    private static final String CUSTOMER_KEY = "remotsucTerces";


    public static String getAdminKey() {
        return ADMIN_KEY;
    }

    public static String getCustomerKey() {
        return CUSTOMER_KEY;
    }
}
