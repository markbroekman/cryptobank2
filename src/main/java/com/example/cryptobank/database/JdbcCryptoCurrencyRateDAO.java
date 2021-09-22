package com.example.cryptobank.database;

import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCryptoCurrencyRateDAO implements CryptoCurrencyRateDAO{

    private final Logger logger = LoggerFactory.getLogger(JdbcCryptoCurrencyRateDAO.class);
    private JdbcTemplate jdbcTemplate;

    public JdbcCryptoCurrencyRateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New CryptoCurrencyRateDAO");
    }


    private PreparedStatement insertCryptoCurrencyStatement(CryptoCurrencyRate cryptoCurrencyRate, Connection connection)
        throws SQLException {
        String sql = "insert into crypto_currency_rate (abbreviation, datetime, value) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cryptoCurrencyRate.getAbbreviation());
        preparedStatement.setString(2, String.valueOf(cryptoCurrencyRate.getDateTime()));
        preparedStatement.setDouble(3, cryptoCurrencyRate.getCryptoRate());
        return preparedStatement;
    }

    private PreparedStatement updateCryptoCurrencyStatement(CryptoCurrencyRate cryptoCurrencyRate, Connection connection)
            throws SQLException {
        String sql = "UPDATE crypto_currency_rate SET value=?, datetime=? WHERE abbreviation =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, cryptoCurrencyRate.getCryptoRate());
        preparedStatement.setString(2, String.valueOf(cryptoCurrencyRate.getDateTime()));
        preparedStatement.setString(3, cryptoCurrencyRate.getAbbreviation());
        return preparedStatement;
    }



    @Override
    public CryptoCurrencyRate save(CryptoCurrencyRate cryptoCurrencyRate) {
        logger.info("cryptoCurrencySaver aangeroepen");
        jdbcTemplate.update(connection -> insertCryptoCurrencyStatement(cryptoCurrencyRate, connection));
        return cryptoCurrencyRate;
    }


    private static class CryptoCurrencyRateRowMapper implements RowMapper<CryptoCurrencyRate> {

        @Override
        public CryptoCurrencyRate mapRow(ResultSet resultSet, int i) throws SQLException {
            String abbreviation = resultSet.getString("abbreviation");
            LocalDateTime dateTime = resultSet.getTimestamp("datetime").toLocalDateTime();
            double cryptoRate = resultSet.getDouble("value");
            return new CryptoCurrencyRate(abbreviation, cryptoRate, dateTime);
        }
    }

    @Override
    public ArrayList<CryptoCurrencyRate> getAll() {
        String sql = "SELECT * FROM crypto_currency_rate";
        List<CryptoCurrencyRate> allCurrencies = jdbcTemplate.query(sql, new CryptoCurrencyRateRowMapper());
        return (ArrayList<CryptoCurrencyRate>) allCurrencies;
    }

    @Override
    public ArrayList<CryptoCurrencyRate> findByAbbreviation(String abbreviation) {
        List<CryptoCurrencyRate> allCurrencies = jdbcTemplate.query(
                "select * from crypto_currency_rate where abbreviation = ?",
                new CryptoCurrencyRateRowMapper(), abbreviation);
        return (ArrayList<CryptoCurrencyRate>) allCurrencies;
    }

    @Override
    public Map<String, Double> getAllCurrentRates (){
        String sql = "SELECT * FROM Current_Rate";
        Map<String, Double> results = new HashMap<>();
        jdbcTemplate.query(sql, (ResultSet rs) -> {
            results.put(rs.getString("abbreviation"),
                    rs.getDouble("value"));
        });
        return results;
    }

    @Override
    public CryptoCurrencyRate update(CryptoCurrencyRate cryptoCurrencyRate) {
        jdbcTemplate.update(connection -> updateCryptoCurrencyStatement(cryptoCurrencyRate, connection));
        return cryptoCurrencyRate;
    }

}