package com.example.cryptobank.database;


import com.example.cryptobank.domain.Admin;

public interface AdminDAO {

    Admin save(Admin admin);

    Admin findByUsername(String username);

}
