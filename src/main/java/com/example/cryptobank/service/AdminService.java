package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    private RootRepository rootRepository;
    private LoginService loginService;
    private RegistrationService registrationService;
    private AuthenticationService authenticationService;


    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    public AdminService(RootRepository rootRepository, LoginService loginService,
                        RegistrationService registrationService, AuthenticationService authenticationService) {
        this.rootRepository = rootRepository;
        this.loginService = loginService;
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
        logger.info("New AdminService");
    }


    public Admin register(String username, String password) {
        Admin attemptToRegister = registrationService.register(username, password);
        return attemptToRegister;
    }

    public Admin login(String username, String password) {
        Admin adminAttempToLogin = loginService.loginAdmin(username, password);
        return adminAttempToLogin;
    }

    public Admin authenticate(String token) {
        return authenticationService.authenticateAdminToken(token);
    }

    public void refresh(Admin adminToRefreshToken) {
        authenticationService.refreshAdminToken(adminToRefreshToken);
    }
}