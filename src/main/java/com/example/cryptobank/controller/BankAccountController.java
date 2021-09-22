package com.example.cryptobank.controller;

import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    private BankAccountService bankAccountService;

    private final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
        logger.info("New BankAccountController");
    }

    // Controllerklasse voor @GetMapping en @PutMapping om met de webkant te praten
    // Admin kan bedragen op rekeningen wijzigen (via webapplicatie)
    // Use case: Admin kan geld storten op/opnemen van willekeurige rekening

    // Klant kan eigen rekening inzien, geld storten, ermee betalen

    @GetMapping("/bankaccounts/balance")
    public double getBalanceByIban(@RequestParam String iban) {
        double balanceRetrieved = bankAccountService.getBalanceByIban(iban);
        logger.info("getBalanceByIban aangeroepen");
        return balanceRetrieved;
    }

    @GetMapping("/bankaccounts")
    public BankAccount getBankAccountByIban(@RequestParam String iban) {
        return bankAccountService.getBankAccountByIban(iban);
    }


    /**
     * Methode deposit vraagt om IBAN en het bij te schrijven bedrag.
     * Het huidige bedrag op de rekening wordt opgehaald en verrekend met het bij te schrijven bedrag.
     * Verrekenen gebeurt in JdbcBankAccountDao.
     * Na bijschrijving wordt het geactualseerde bedrag geretourneerd.
     */
    @PutMapping("/bankaccounts/deposit")
    public double deposit(@RequestParam String iban, double amount) {
        double balanceUpdated = bankAccountService.deposit(iban, amount);
        logger.info("deposit aangeroepen");
        return balanceUpdated;
    }

    @PutMapping("/bankaccounts/withdraw")
    public double withdraw(@RequestParam String iban, double amount) {
        double balanceUpdated = bankAccountService.withdraw(iban, amount);
        logger.info("withdraw aangeroepen");
        return balanceUpdated;
    }


}