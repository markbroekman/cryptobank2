package com.example.cryptobank.database;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.domain.Order;
import com.example.cryptobank.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPortfolioDao implements PortfolioDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioDao.class);

    private JdbcTemplate jdbcTemplate;
    private final String UPDATE_QUERY = "UPDATE ownedasset SET aantalEenheden = ? WHERE iban=? AND abbreviation=?";

    @Autowired
    public JdbcPortfolioDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcPortfolioDao");
    }

    // gebruik indien asset in kwestie nog niet eerder in de portfolio
    private PreparedStatement insertAssetInPortfolioStatement (TransactionDto transactionDto, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO ownedasset (IBAN, abbreviation, aantalEenheden) values (?, ?, ?)"
        );
        ps.setString(1, transactionDto.getIbanBuyer());
        ps.setString(2, transactionDto.getAssetAbbr());
        ps.setDouble(3, transactionDto.getAssetAmount());
        return ps;
    }

    @Override
    public void insertAssetIntoPortfolio(TransactionDto transactionDto){
        jdbcTemplate.update(connection -> insertAssetInPortfolioStatement(transactionDto, connection));
    }

    private PreparedStatement updatePortfolioStatementPositive (double updatedAmount, TransactionDto transactionDto,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedAmount);
        ps.setString(2, transactionDto.getIbanBuyer());
        ps.setString(3, transactionDto.getAssetAbbr());
        return ps;
    }

    @Override
    public double updateAssetAmountPositive(TransactionDto transactionDto) {
        String iban = transactionDto.getIbanBuyer();
        String abbr = transactionDto.getAssetAbbr();
        double transactionAssetAmount = transactionDto.getAssetAmount();
        double currentAmount = getAssetAmountByIbanAndAbbr(iban, abbr);
        double updatedAmount = currentAmount + transactionAssetAmount;
        int status = jdbcTemplate.update(connection -> updatePortfolioStatementPositive(updatedAmount, transactionDto, connection));
        if (status == 1) {
            return updatedAmount;
        } else {
            return currentAmount;
        }
    }

    // Bij verkoop van een deel van opgeslagen asset wordt de hoeveelheid verminderd
    private PreparedStatement updatePortfolioStatementNegative (double updatedAmount, TransactionDto transactionDto,
                                                                Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setDouble(1, updatedAmount);
        ps.setString(2, transactionDto.getIbanSeller());
        ps.setString(3, transactionDto.getAssetAbbr());
        return ps;
    }

    @Override
    public double updateAssetAmountNegative(TransactionDto transactionDto) {
        String iban = transactionDto.getIbanSeller();
        String abbr = transactionDto.getAssetAbbr();
        double transactionAssetAmount = transactionDto.getAssetAmount();
        double currentAmount = getAssetAmountByIbanAndAbbr(iban, abbr);
        double updatedAmount = currentAmount - transactionAssetAmount;
        int status = jdbcTemplate.update(connection -> updatePortfolioStatementNegative(updatedAmount, transactionDto, connection));
        if (status == 1) {
            return updatedAmount;
        } else {
            return currentAmount;
        }
    }

    private PreparedStatement deletePortfolioStatement (TransactionDto transactionDto, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM ownedasset_table WHERE iban = ?"
        );
        ps.setString(1, transactionDto.getIbanSeller());
        return ps;
    }

    @Override
    public void deleteAssetFromPortfolio(TransactionDto transactionDto){
        jdbcTemplate.update(connection -> deletePortfolioStatement(transactionDto, connection));
    }

    @Override
    public Map<String, Double> getAssetmapByIban (String iban){
        String sql = "SELECT abbreviation, aantalEenheden FROM ownedasset WHERE iban = ?";
        Map<String, Double> results = new HashMap<>();
        jdbcTemplate.query(sql, (ResultSet rs) -> {
            results.put(rs.getString("abbreviation"),
                        rs.getDouble("aantalEenheden"));
        }, iban);
        return results;
    }

    public double getAssetAmountByIbanAndAbbr (String iban, String abbr){
        String sql = "SELECT aantalEenheden FROM ownedasset WHERE IBAN = ? AND abbreviation = ?";
        try {
            return jdbcTemplate.queryForObject(sql, double.class, iban, abbr);
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Empty result");
            // TODO Empty result afvangen
            return -1.0;
        }
    }

    public List<String> getAbbreviationsByIban(String iban){
        String sql = "SELECT abbreviation FROM ownedasset WHERE iban = ?";
        List<String> abbrList = jdbcTemplate.queryForList(sql, String.class, iban);
        return abbrList;
    }


}
