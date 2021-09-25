package com.example.cryptobank.security;

import com.example.cryptobank.domain.Admin;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private RootRepository rootRepository;
    private CreateToken createToken;
    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginService(RootRepository rootRepository, CreateToken createToken) {
        this.rootRepository = rootRepository;
        this.createToken = createToken;
        logger.info("New LoginService");
    }

    public Admin loginAdmin(String username, String password) {
        Admin attemptToLogin = new Admin(username, password);
        Admin adminInDatabase = rootRepository.findAdminByUsername(username);

        if(adminInDatabase != null && attemptToLogin.getUsername().equals(adminInDatabase.getUsername())) {
            String salt = adminInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(attemptToLogin.getPassword(), salt, PepperService.getPepper());

            if(authenticate(adminInDatabase.getPassword(), hashedPassword)) {
                attemptToLogin.setPassword(hashedPassword);
                attemptToLogin.setSalt(salt);
                attemptToLogin.setAccessToken(createToken.createAccessToken(
                        username, TokenKeyService.getAdminKey()));
                attemptToLogin.setRefreshToken(createToken.createRefreshToken(
                        username, TokenKeyService.getAdminKey()));
                return attemptToLogin;
            }
        }
        return attemptToLogin;
    }

    public boolean authenticate(String hashInDatabase, String hashedPassword) {
        return hashInDatabase.equals(hashedPassword);
    }

    public CustomerDto loginCustomer(String username, String password) {
        String token = null;
        CustomerDto user = new CustomerDto(username, password);

        Customer customerInDatabase = rootRepository.findCustomerByUsername(username);

        if (customerInDatabase != null && user.getUsername().equals(customerInDatabase.getUsername())) {
            String salt = customerInDatabase.getSalt();
            String hashedPassword = HashHelper.hash(user.getPassword(), salt, PepperService.getPepper());

            if (authenticate(customerInDatabase.getPassword(), hashedPassword)) {
                user.setAccessToken(createToken.createAccessToken(
                        user.getUsername(), TokenKeyService.getCustomerKey()));
                user.setRefreshToken(createToken.createRefreshToken(
                        user.getUsername(), TokenKeyService.getCustomerKey()));
                user.setFirstName(customerInDatabase.getFirstName());
                user.setIban(customerInDatabase.getBankAccount().getIban());
                return user;
            }
        }
        return user;
    }
}