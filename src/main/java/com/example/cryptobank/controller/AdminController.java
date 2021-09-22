package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private AdminService adminService;


    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        logger.info("New AdminController");
    }


    @PutMapping("/admin/register")
    public ResponseEntity<?> register(@RequestParam String username, String password) {
        logger.info("registerAdmin aangeroepen");
        try {
            Admin adminToRegister = adminService.register(username, password);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, adminToRegister.getAccessToken());
            return new ResponseEntity<>(adminToRegister.getUsername(),httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            String responseBody = null;
            if (e.toString().contains("Duplicate")) {
                responseBody = "username bestaat al";
            } else {
                responseBody = "";
            }
            return new ResponseEntity<>( responseBody
            , HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/admin/login")
    ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        logger.info("login admin aangeroepen");
        Admin adminToLogin = adminService.login(username, password);
        if (adminToLogin.getSalt() != null) {
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.set(HttpHeaders.AUTHORIZATION, adminToLogin.getAccessToken());
            responseHeader.set("refresh_token", adminToLogin.getRefreshToken());
            return new ResponseEntity<String>(adminToLogin.getAccessToken(), responseHeader, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("optyfen gauw", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/admin/get")
    ResponseEntity<?> getAdmin(@RequestHeader ("Authorization") String token) {
        logger.info("get admin aangeroepen");
        Admin adminToLogin = adminService.authenticate(token);

        if (adminToLogin != null) {
            return new ResponseEntity<String>(adminToLogin.toString() + " " +
                    adminToLogin.getAccessToken(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("optyfen gauw", HttpStatus.FORBIDDEN);
        }
    }
}