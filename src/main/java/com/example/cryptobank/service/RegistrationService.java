package com.example.cryptobank.service;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.IbanGenerator;
import com.example.cryptobank.error.EmailError;
import com.example.cryptobank.error.SocialSecurityError;
import com.example.cryptobank.error.UsernameError;
import com.example.cryptobank.security.*;
import com.example.cryptobank.database.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RegistrationService {
    private RootRepository rootRepository;
    private CreateToken createToken;
    private List<Customer> customers;
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    public RegistrationService(RootRepository rootRepository, CreateToken createToken) {
        this.rootRepository = rootRepository;
        this.createToken = createToken;
        logger.info("New RegistrationService");
    }

    public Admin register(String username, String password) {
        Admin attemptToRegister = new Admin(username,password);
        Admin adminInDatabase = rootRepository.findAdminByUsername(username);

        if(adminInDatabase == null || attemptToRegister.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = new Saltmaker().generateSalt();attemptToRegister.setPassword(
                    HashHelper.hash(password, salt, PepperService.getPepper()));
            attemptToRegister.setSalt(salt);
            attemptToRegister.setAccessToken(createToken.createAccessToken(attemptToRegister.getUsername(),
                    TokenKeyService.getAdminKey()));
            attemptToRegister.setRefreshToken(createToken.createRefreshToken(attemptToRegister.getUsername(),
                    TokenKeyService.getAdminKey()));
            return rootRepository.saveTransaction(attemptToRegister);
        }
        return attemptToRegister;
    }


    public Customer register(Customer customerToRegister) throws Exception {
        String salt = new Saltmaker().generateSalt();
        checkIfCustomerCanBeRegistered(customerToRegister);
        customerToRegister.setPassword(HashHelper.hash(
                customerToRegister.getPassword(), salt, PepperService.getPepper()));
        customerToRegister.setSalt(salt);
        String iban = IbanGenerator.generate();
        customerToRegister.setBankAccount(new BankAccount(iban));
        String accessToken = createToken.createAccessToken(
                customerToRegister.getUsername(), TokenKeyService.getCustomerKey());
        String refreshToken = createToken.createRefreshToken(
                customerToRegister.getUsername(), TokenKeyService.getCustomerKey());
        customerToRegister.setAccessToken(accessToken);
        customerToRegister.setRefreshToken(refreshToken);
        // TODO: 17/09/2021  Deze methodenaam is niet ok. @Anne, wil jij hier nog eens naar kijken
        return rootRepository.saveTransaction(customerToRegister);
    }


    public void checkIfCustomerCanBeRegistered(Customer customerToCheck) throws Exception {
        customers = rootRepository.getAllCustomers();
        String username = customerToCheck.getUsername();
        int socialSecurityNumber = customerToCheck.getSocialSecurityNumber();
        String email = customerToCheck.getEmail();

        for (Customer customerInDatabase : customers){
            checkIfSocialSecurityNumberExists(socialSecurityNumber, customerInDatabase.getSocialSecurityNumber());
            checkIfUsernameExists(username, customerInDatabase.getUsername());
            checkIfEmailExists(email, customerInDatabase.getEmail());
        }
    }

    private void checkIfEmailExists(String email, String emailInDatabase) throws EmailError {
        if (email.equals(emailInDatabase)) {
            logger.info("Username already exists");
            throw new EmailError("Email already exists");
        }
    }

    private void checkIfUsernameExists(String username, String usernameInDatabase) throws UsernameError {
        if (username.equals(usernameInDatabase)) {
            logger.info("Username already exists");
            throw new UsernameError("Username already exists");
        }
    }

    public void checkIfSocialSecurityNumberExists(
        int socialSecurityNumberToCheck, int socialSecurityNumberInDatabase) throws SocialSecurityError {
        if (socialSecurityNumberToCheck == socialSecurityNumberInDatabase){
            logger.info("Social security number already exists.");
            throw new SocialSecurityError("Social security number already exists.");
        }
    }
}
