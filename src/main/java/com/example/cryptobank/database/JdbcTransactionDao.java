package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTransactionDao implements TransactionDao {

    private JdbcTemplate jdbcTemplate;
    private final String SQL_INSERT_QUERY = "INSERT INTO cryptobank.transaction" +
            "(abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed)" +
            " VALUES (?,?,?,?,?,?,?)";
    private final String SQL_SELECTBYID = "SELECT * FROM cryptobank.transaction WHERE transactionId = ?";

    private final Logger logger = LoggerFactory.getLogger(JdbcTransactionDao.class);

    @Autowired
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcTransactionDao");
    }

    private PreparedStatement insertTransactionStatement(TransactionDto transaction, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, transaction.getAssetAbbr());
        preparedStatement.setDouble(2, transaction.getAssetAmount());
        preparedStatement.setDouble(3, transaction.getSingleAssetPrice());
        preparedStatement.setString(4, transaction.getIbanBuyer());
        preparedStatement.setString(5, transaction.getIbanSeller());
        preparedStatement.setDouble(6, transaction.getTransactionCost());
        preparedStatement.setString(7, String.valueOf(transaction.getDateTimeProcessed()));
        return preparedStatement;
    }

    @Override
    public TransactionDto save(TransactionDto transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        transaction.setTransactionId(newKey);
        return transaction;
    }


    private static class TransactionRowMapper implements RowMapper<Transaction> {

        @Override
        public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
            int transactionId = resultSet.getInt("transactionId");
            Asset asset = null;
            double assetAmount = resultSet.getDouble("assetAmount");
            double assetPrice = resultSet.getDouble("assetPrice");
            BankAccount buyerAccount = null;
            BankAccount sellerAccount = null;
            double transactioncost = resultSet.getDouble("transactioncost");
            LocalDateTime dateTimeProcessed = resultSet.getTimestamp("datetimeprocessed").toLocalDateTime();
            Transaction transaction = new Transaction(transactionId, asset, assetAmount, assetPrice,
                    buyerAccount, sellerAccount, transactioncost, dateTimeProcessed);
            return transaction;
        }
    }

    @Override
    public Transaction findByTransactionId(int transactionId) {
        List<Transaction> transactionList = jdbcTemplate.query(SQL_SELECTBYID, new TransactionRowMapper(), transactionId);
        if (transactionList.size() == 1) {
            return transactionList.get(0);
        }
        return null;
    }

    @Override
    public String findAssetForTransaction(int transactionId) {
        String sql = "SELECT abbreviation FROM cryptobank.transaction WHERE transactionId = ?";
        String assetAbbr = jdbcTemplate.queryForObject(sql, String.class, transactionId);
        return assetAbbr;
    }

    @Override
    public String findBuyerAccountForTransaction(int transactionId) {
        String sql = "SELECT ibanBuyer FROM cryptobank.transaction WHERE transactionId = ?";
        String ibanBuyer = jdbcTemplate.queryForObject(sql, String.class, transactionId);
        return ibanBuyer;
    }

    @Override
    public String findSellerAccountForTransaction(int transactionId) {
        String sql = "SELECT ibanSeller FROM cryptobank.transaction WHERE transactionId = ?";
        String ibanSeller = jdbcTemplate.queryForObject(sql, String.class, transactionId);
        return ibanSeller;
    }
}