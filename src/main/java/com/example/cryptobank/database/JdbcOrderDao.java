package com.example.cryptobank.database;

import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Order;
import com.example.cryptobank.dto.OrderDto;
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
import java.util.ArrayList;
import java.util.List;


/**
 * @author Sarah-Jayne Nogarede
 * Dit is de JDBC DAO voor het object 'Order'. Deze DAO implementeert de methoden save, findByOrderId
 * en getAllByIban uit de OrderDao Interface.
 */
@Repository
public class JdbcOrderDao implements OrderDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcOrderDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcOrderDao");
    }

    private PreparedStatement insertOrderStatement(OrderDto orderDto, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into cryptobank.order (iban, " +
                "dateTimeCreated, abbreviation, assetAmount, desiredPrice, orderType) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, orderDto.getIban());
        preparedStatement.setString(2, String.valueOf(orderDto.getDateTimeCreated()));
        preparedStatement.setString(3, orderDto.getAssetAbbr());
        preparedStatement.setDouble(4, orderDto.getAssetAmount());
        preparedStatement.setDouble(5, orderDto.getDesiredPrice());
        preparedStatement.setInt(6, orderDto.getOrderType());
        return preparedStatement;
    }

    @Override
    public OrderDto save(OrderDto orderDto) {
        logger.info("orderDao.save aangeroepen");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertOrderStatement(orderDto, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        orderDto.setOrderId(newKey);
        return orderDto;
    }


    private static class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            int orderId = resultSet.getInt("orderId");
            BankAccount bankAccount = null;
            LocalDateTime dateTimeCreated = resultSet.getTimestamp("dateTimeCreated").toLocalDateTime();
            Asset asset = null;
            double assetAmount = resultSet.getDouble("assetAmount");
            double desiredPrice = resultSet.getDouble("desiredPrice");
            int orderType = resultSet.getInt("orderType");
            Order order = new Order(orderId, bankAccount, dateTimeCreated, asset, assetAmount, desiredPrice, orderType);
            return order;
        }

    } // end of nested class OrderRowMapper


    @Override
    public Order findByOrderId(int orderId) {
        String sql = "SELECT * from cryptobank.order where orderId = ?";
        List<Order> orderToFind = jdbcTemplate.query(sql, new OrderRowMapper(), orderId);
        if (orderToFind.size() == 1) {
            return orderToFind.get(0);
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAllByIban (String iban) {
        String sql = "SELECT * from cryptobank.order where iban = ?";
        List<Order> allOrders = jdbcTemplate.query(sql, new OrderRowMapper());
        return (ArrayList<Order>) allOrders;
    }

    @Override
    public String getAssetAbbrFromOrderId(int orderId) {
        String sql = "select abbreviation from cryptobank.order where orderId = ?";
        String assetAbbreviation = jdbcTemplate.queryForObject(sql, String.class, orderId);
        return assetAbbreviation;
    }

    public String getIbanFromOrderId(int orderId) {
        String sql = "select iban from cryptobank.order where orderId = ?";
        String iban = jdbcTemplate.queryForObject(sql, String.class, orderId);
        return iban;
    }

} // end of class JdbcOrderDao

