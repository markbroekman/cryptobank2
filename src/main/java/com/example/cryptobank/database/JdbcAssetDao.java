package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import com.example.cryptobank.dto.AssetDto;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de JDBC DAO voor het object 'Asset'. Deze DAO implementeert de methoden save, findByAbbreviation, findByName, findAsset
 * en getAll uit de AssetDao Interface.
 */
@Repository
public class JdbcAssetDao implements AssetDao {

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAssetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New JdbcAssetDao");
    }

    private PreparedStatement insertAssetStatement(Asset asset, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into asset (abbreviation, nameCrypto, " +
                "description) values (?, ?, ?)");
        preparedStatement.setString(1, asset.getAbbreviation());
        preparedStatement.setString(2, asset.getName());
        preparedStatement.setString(3, asset.getDescription());
        return preparedStatement;
    }

    @Override
    public Asset save(Asset asset) {
        logger.info("assetDao.save aangeroepen");
        jdbcTemplate.update(connection -> insertAssetStatement(asset, connection));
        return asset;
    }

    private static class AssetRowMapper implements RowMapper<Asset> {

        @Override
        public Asset mapRow(ResultSet resultSet, int i) throws SQLException {
            String abbreviation = resultSet.getString("abbreviation");
            String name = resultSet.getString("nameCrypto");
            String description = resultSet.getString("description");
            CryptoCurrencyRate rate = null;
            Asset asset = new Asset(abbreviation, name, description, rate);
            return asset;
        }

    } // end of nested class AssetRowMapper



    @Override
    public Asset findByAbbreviation(String abbreviation) {
        String sql = "SELECT * from asset where abbreviation = ?";
        List<Asset> assetToFind = jdbcTemplate.query(sql, new AssetRowMapper(), abbreviation);
        if (assetToFind.size() == 1) {
            return assetToFind.get(0);
        }
        return null;
    }

    @Override
    public Asset findByName(String name) {
        String sql = "SELECT * from asset where nameCrypto = ?";
        List<Asset> assetToFind = jdbcTemplate.query(sql, new AssetRowMapper(), name);
        if (assetToFind.size() == 1) {
            return assetToFind.get(0);
        }
        return null;
    }


    //TODO List -> ArrayList en RowMapper, check hoe dit precies zit. Waarom moet ik hem casten naar Arraylist?
    @Override
    public ArrayList<Asset> getAll() {
        String sql = "SELECT * from asset";
        List<Asset> allAssets = jdbcTemplate.query(sql, new AssetRowMapper());
        return (ArrayList<Asset>) allAssets;
    }

    public ArrayList<String> getListAbbreviations() {
        String sql = "SELECT abbreviation from asset";
        List<String> allAbbreviations = jdbcTemplate.queryForList(sql, String.class);
        return (ArrayList<String>) allAbbreviations;
    }

} // end of class JdbcAssetDao
