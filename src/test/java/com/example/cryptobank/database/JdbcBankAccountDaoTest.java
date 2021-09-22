package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class JdbcBankAccountDaoTest {

    private BankAccountDao bankAccountDaoUnderTest;

    @Autowired
    public JdbcBankAccountDaoTest(BankAccountDao dao) {
        this.bankAccountDaoUnderTest = dao;
    }

    @Test
    public void bankAccountDaoNotNull() {
        assertThat(bankAccountDaoUnderTest).isNotNull();
    }

    @Test
    void save() {
    }

    @Test
    void getBalanceByIban() {
        double balanceBank = bankAccountDaoUnderTest.getBalanceByIban("NL24COKI3309054260");
        double expected = 5000000;
        assertThat(balanceBank).isEqualTo(expected);
    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

   /* @Test
    void findAccountByIban() {
        BankAccount bank = bankAccountDaoUnderTest.findAccountByIban("NL24COKI3309054260");
        BankAccount expected = new BankAccount("NL24COKI3309054260", 5000000);
        assertThat(bank).isEqualTo(expected);
    }*/
}