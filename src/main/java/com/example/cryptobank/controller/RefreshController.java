package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RefreshController {
    private final Logger logger = LoggerFactory.getLogger(RefreshController.class);
    private AdminService adminService;
    private CustomerService customerService;

    @Autowired
    public RefreshController(AdminService adminService, CustomerService customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
        logger.info("New RefreshController");
    }

    @GetMapping("/admin/refresh")
    public ResponseEntity<?> refreshAdminToken(@RequestHeader("Authorization") String token) {
        logger.info("admin refresh token aangeroepen");
        Admin adminToRefreshToken = adminService.authenticate(token);
        if (adminToRefreshToken != null) {
            adminService.refresh(adminToRefreshToken);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set(HttpHeaders.AUTHORIZATION, adminToRefreshToken.getAccessToken());
            responseHeader.set("refresh_token", adminToRefreshToken.getRefreshToken());
            return new ResponseEntity<String>(adminToRefreshToken.getAccessToken(), responseHeader, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("optyfen gauw, nu echt.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshCustomerToken(@RequestHeader("Authorization") String token) {
        logger.info("customer refresh token aangeroepen");
        CustomerDto customerToRefreshToken = customerService.authenticate(token);
        if (customerToRefreshToken != null) {
            customerService.refresh(customerToRefreshToken);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set(HttpHeaders.AUTHORIZATION, customerToRefreshToken.getAccessToken());
            responseHeader.set("refresh_token", customerToRefreshToken.getRefreshToken());
            return new ResponseEntity<String>(customerToRefreshToken.getAccessToken(), responseHeader, HttpStatus.OK);
        }

        else {
            return new ResponseEntity<String>("optyfen gauw, nu echt.", HttpStatus.FORBIDDEN);
        }

    }

}