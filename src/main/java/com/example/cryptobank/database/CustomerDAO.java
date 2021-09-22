package com.example.cryptobank.database;


import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.CustomerDto;

import java.util.List;

public interface CustomerDAO {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    List<Customer> getAll();

    Customer findCustomerByEmail(String email);

    boolean updatePassword(CustomerDto customerDto, String salt);
}
