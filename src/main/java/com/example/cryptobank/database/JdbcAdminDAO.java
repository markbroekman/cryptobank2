package com.example.cryptobank.database;

import com.example.cryptobank.domain.Admin;
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
public class JdbcAdminDAO implements AdminDAO {

    private final Logger logger = LoggerFactory.getLogger(JdbcAdminDAO.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcAdminDAO");
    }

    private PreparedStatement insertAdminStatement(Admin admin, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into admin (username, password, salt) values (?, ?, ?)"
        );
        preparedStatement.setString(1, admin.getUsername());
        preparedStatement.setString(2, admin.getPassword());
        preparedStatement.setString(3, admin.getSalt());
        return preparedStatement;
    }

    @Override
    public Admin save(Admin admin) {
        logger.debug("adminDao.save aangeroepen");
        jdbcTemplate.update(connection -> insertAdminStatement(admin, connection));
        return admin;
    }

    private static class AdminRowMapper implements RowMapper<Admin> {

        @Override
        public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            Admin admin = new Admin(username, password, salt);
            return admin;
        }
    }

    @Override
    public Admin findByUsername(String username) {
        String sql = "SELECT * from admin where username = ?";
        List<Admin> adminToFind = jdbcTemplate.query(sql, new AdminRowMapper(), username);
        if (adminToFind.size() == 1) {
            return adminToFind.get(0);
        }
        return null;
    }

}