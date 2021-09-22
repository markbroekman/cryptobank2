package com.example.cryptobank.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class BankTest {
    private final Logger logger = LoggerFactory.getLogger(BankTest.class);

    @Autowired
    public BankTest() {
        logger.info("New BankTest");
    }

    /**
     * The createBank() test is made for testing if the correct constant IBAN_BANK is used for the bank account of the Bank
     */
    @Test
    void createBank(){
        // Bank bank = new Bank();
        Bank bank = Bank.getInstance();
        String iban = bank.getBankAccount().getIban();
        String expected = Bank.getBankIban();
        assertThat(iban).isEqualTo(expected);
    }

}
