package com.example.cryptobank.domain;

public class Admin extends User{


    public Admin(String username, String password, String salt) {
        super(username, password, salt);
    }

    public Admin(String username, String password) {
        this(username, password, null);
    }


    @Override
    public int compareTo(User o) {
        return 0;
    }

}
