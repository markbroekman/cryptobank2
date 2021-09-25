package com.example.cryptobank.service;

import com.example.cryptobank.database.CustomerDAO;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private LoginService loginService;
    private CustomerDAO customerDAO;
    private RootRepository rootRepository;
    private RegistrationService registrationService;
    private AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(LoginService loginService, CustomerDAO customerDAO,
                           RootRepository rootRepository, RegistrationService registrationService,
                           AuthenticationService authenticationService) {
        this.loginService = loginService;
        this.customerDAO = customerDAO;
        this.rootRepository = rootRepository;
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
        logger.info("New CustomerService");
    }


    public Customer register(Customer customerToRegister) throws Exception {
        return registrationService.register(customerToRegister);
    }



    public CustomerDto login(String username, String password) {
        return loginService.loginCustomer(username, password);
    }

    public CustomerDto authenticate(String token) {
        return authenticationService.authenticateCustomerToken(token);
    }

    public CustomerDto authenticateReset(String token) {
        return authenticationService.authenticateResetCustomerToken(token);
    }

    public void refresh(CustomerDto customerToRefreshToken) {
        authenticationService.refreshCustomerToken(customerToRefreshToken);
    }

    public CustomerDto findCustomerByEmail(String email) {
        Customer customer = rootRepository.findCustomerByEmail(email);
        if (customer != null) {
            CustomerDto customerDto = new CustomerDto(customer.getUsername(), customer.getPassword());
            authenticationService.createCustomerPasswordResetToken(customer);
            customerDto.setEmail(email);
            customerDto.setAccessToken(customer.getAccessToken());
            return customerDto;
        }
        return null;
    }

    public boolean updatePassword(String password, CustomerDto customer) {
        String salt = new Saltmaker().generateSalt();
        customer.setPassword(HashHelper.hash(password, salt, PepperService.getPepper()));

        return rootRepository.updatePassword(customer, salt);
    }
}
