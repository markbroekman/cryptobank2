package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bank {
    private final static String BANKNAME = "CryptoKnights";
    private BankAccount bankAccount;
    private final Logger logger = LoggerFactory.getLogger(Bank.class);
    private final static String BANK_IBAN = "NL24COKI3309054260";

   /* public Bank() {
        this.bankAccount = new BankAccount();
        this.bankAccount.setIban(BANK_IBAN);
        logger.info("New Bank");
    }*/

    private static Bank uniqueInstance;

    private Bank() {
        this.bankAccount = new BankAccount();
        this.bankAccount.setIban(BANK_IBAN);
        logger.info("New Bank uniqueInstance");
    }

    public static synchronized Bank getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Bank();
        }
        return uniqueInstance;
    }

    public static String getBankName() {
        return BANKNAME;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static String getBankIban() {
        return BANK_IBAN;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", BANKNAME, BANK_IBAN);
    }
//bankAccount moet aangemaakt worden, vervolgens iban setten via setIban() met de final static BANK_IBAN

}
