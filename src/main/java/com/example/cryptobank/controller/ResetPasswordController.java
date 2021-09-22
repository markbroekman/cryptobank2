package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Mail;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/forgot")
public class ResetPasswordController {
    private final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final static String URL = "http://localhost:8080/api/forgot/reset?token=";
    private final static String MAIL_SUBJECT = "reset password";
    private final static String MAIL_MESSAGE = "Click this link to reset your password. \n" +
            "After 30 minutes, this link will expire.\n\n";



    private AuthenticationService authenticationService;
    private CustomerService customerService;
    private AdminService adminService;
    private SendMailService sendMailService;


    @Autowired
    public ResetPasswordController(AuthenticationService authenticationService, CustomerService customerService,
                                   AdminService adminService, SendMailService sendMailService) {
         this.authenticationService = authenticationService;
         this.customerService = customerService;
         this.adminService = adminService;
         this.sendMailService = sendMailService;
        logger.info("New ResetPasswordController");
    }

    @PostMapping("/password")
    ResponseEntity<?> sendResetPasswordMail(@RequestParam String email) {
        logger.info("reset password mail aangeroepen");
        CustomerDto customer = customerService.findCustomerByEmail(email);
       if (customer != null) {
           String urlToken = URL + customer.getAccessToken();
           String message = MAIL_MESSAGE + urlToken;
           Mail mail = new Mail(customer.getEmail(), MAIL_SUBJECT, message);
           sendMailService.sendMail(mail);
       }
        return new ResponseEntity<String>("mail send", HttpStatus.OK);
    }

    @GetMapping("/reset")
    public ModelAndView loadResetPasswordPage(final ModelMap model, @RequestParam String token) {
        logger.info("reset link aangeroepen endpoint aangeroepen");

        CustomerDto customer = customerService.authenticateReset(token);
        if (customer != null) {
            String accessToken = customer.getAccessToken();
            model.addAttribute("token", accessToken);
            return new ModelAndView("redirect:/reset-password.html", model);
        } else {
            logger.info("customer was null");

            return new ModelAndView("redirect:/index.html", model);

        }
    }

    @PostMapping("/submit")
    public ResponseEntity<?> resetPassword(@RequestParam String password, @RequestHeader("Authorization") String accessToken) {
    logger.info("reset password aangeroepen");
    logger.info(password);
    CustomerDto customer = customerService.authenticate(accessToken);
        if (customer != null) {
            boolean passwordIsReset =  customerService.updatePassword(password, customer);
            if (passwordIsReset) {
                return new ResponseEntity<String>("password successfully updated", HttpStatus.OK);
            }
        } else {
        return new ResponseEntity<String>("password not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Unauthorized request", HttpStatus.UNAUTHORIZED);
    }
}