package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcBankAccountDao implements BankAccountDao {

    private JdbcTemplate jdbcTemplate;
    private final String SQL_SELECT_BALANCEBYIBAN = "SELECT balance FROM cryptobank.bankaccount WHERE IBAN = ?";
    private final String SQL_SELECT_ACCOUNTBYIBAN = "SELECT * FROM cryptobank.bankaccount WHERE IBAN = ?";
    private final String SQL_INSERT = "INSERT INTO cryptobank.bankaccount (iban, balance) VALUES (?,?)";
    private final String SQL_UPDATE = "UPDATE cryptobank.bankaccount SET balance = ? WHERE iban = ?";

    private final Logger logger = LoggerFactory.getLogger(JdbcBankAccountDao.class);

    @Autowired
    public JdbcBankAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcBankAccountDao");
    }

    private PreparedStatement insertBankAccount(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());
        return ps;
    }

    private PreparedStatement updateBankAccount(String iban, double updatedBalance, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
        ps.setDouble(1, updatedBalance);
        ps.setString(2, iban);
        return ps;
    }

    @Override
    public void save(BankAccount bankAccount) {
        jdbcTemplate.update(connection -> insertBankAccount(bankAccount, connection));
    }

    @Override
    public double getBalanceByIban(String iban) {
        if (!checkIbanExists(iban)) {
            logger.error("IBAN cannot be found");
            return 0.0;
        }
        double balanceRetrieved = jdbcTemplate.queryForObject(SQL_SELECT_BALANCEBYIBAN, double.class, new Object[] {iban});
        return balanceRetrieved;
    }

    @Override
    public double deposit(String iban, double amount) {
        if (!checkIbanExists(iban)) {
            logger.error("IBAN cannot be found");
            return 0.0;
        }
        double currentBalance = this.getBalanceByIban(iban);
        double updatedBalance = currentBalance + amount;
        int status = jdbcTemplate.update(connection -> updateBankAccount(iban, updatedBalance, connection));
        if (status == 0) {
            return currentBalance;
        } else {
            return updatedBalance;
        }
    }

    @Override
    public double withdraw(String iban, double amount) {
        if (!checkIbanExists(iban)) {
            logger.error("IBAN cannot be found");
            return 0.0;
        }
        double currentBalance = this.getBalanceByIban(iban);
        double updatedBalance = currentBalance - amount;
        int status = jdbcTemplate.update(connection -> updateBankAccount(iban, updatedBalance, connection));
        if (status == 0) {
            return currentBalance;
        } else {
            return updatedBalance;
        }
    }

    // TODO implementeren
    @Override
    public boolean checkIbanExists(String iban) {
        return true;
    }


    private static class BankAccountRowMapper implements RowMapper<BankAccount> {

        @Override
        public BankAccount mapRow(ResultSet resultSet, int i) throws SQLException {
            String iban = resultSet.getString("IBAN");
            double balance = resultSet.getDouble("balance");
            BankAccount bankAccount = new BankAccount(iban, balance);
            return bankAccount;
        }
    }

    public BankAccount findAccountByIban (String iban) {
        List<BankAccount> bankAccountList = jdbcTemplate.query(SQL_SELECT_ACCOUNTBYIBAN, new BankAccountRowMapper(), iban);
        if (bankAccountList.size() == 1) {
            return bankAccountList.get(0);
        }
        return null;
    }


}