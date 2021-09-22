package com.example.cryptobank.database;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.dto.AssetDto;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Asset', waarin wordt aangegeven welke methoden een
 * AssetDao moet implementeren.
 */
public interface AssetDao {

    Asset save(Asset asset);
    Asset findByAbbreviation(String abbreviation);
    Asset findByName(String name);
    ArrayList<Asset> getAll();
    ArrayList<String> getListAbbreviations();

}
