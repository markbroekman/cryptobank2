package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Address;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.RegistrationDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class CustomerController {

    private CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        logger.info("New CustomerController");
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<String> register(@RequestBody RegistrationDto customerRegistrationDto) {
        logger.info(customerRegistrationDto.toString());
        Customer customerToRegister = new Customer(customerRegistrationDto.getUsername().toLowerCase(),
                customerRegistrationDto.getPassword(), customerRegistrationDto.getFirstName(),
                customerRegistrationDto.getLastName(), customerRegistrationDto.getDateOfBirth(),
                customerRegistrationDto.getSocialSecurityNumber(), customerRegistrationDto.getStreet(),
                customerRegistrationDto.getZipcode(), customerRegistrationDto.getHousenumber(),
                customerRegistrationDto.getAddition(), customerRegistrationDto.getCity(),
                customerRegistrationDto.getEmail().toLowerCase());
        logger.info(customerRegistrationDto.toString());
        try {
            Customer customerRegistered = customerService.register(customerToRegister);
            if (customerRegistered.getSalt() != null) {
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set(HttpHeaders.AUTHORIZATION, customerRegistered.getAccessToken());
                responseHeaders.set("refresh_token", customerRegistered.getRefreshToken());
                return new ResponseEntity<String>(customerRegistered.getFirstName(), responseHeaders, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Could not process request", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        logger.info("login customer aangeroepen");

        CustomerDto customerToLogin = customerService.login(username, password);

        if (customerToLogin.getAccessToken() == null) {
            return new ResponseEntity<>("username/password incorrect", HttpStatus.UNAUTHORIZED);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.AUTHORIZATION, customerToLogin.getAccessToken());
            responseHeaders.set("refresh_token", customerToLogin.getRefreshToken());
            return new ResponseEntity<>(customerToLogin.toString(), responseHeaders, HttpStatus.OK);
        }
    }
}
