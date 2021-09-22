package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.dto.AssetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de service klasse voor het object 'Asset'. Deze klasse bevat de methoden...//TODO aanvullen
 */
@Service
public class AssetService {

    private RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public AssetService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        logger.info("New AssetService");
    }

    public ArrayList<AssetDto> getAssets() {
        ArrayList<Asset> allAssets = rootRepository.getAll();
        Map<String, Double> currentRates = rootRepository.getAllCurrentRates();
        System.out.println("**** currentRates is: " + currentRates);
        ArrayList<AssetDto> allAssetsWithRate = new ArrayList<>();
        for (Asset asset : allAssets) {
            allAssetsWithRate.add(new AssetDto(asset.getAbbreviation(), asset.getName(), asset.getDescription(),
                    currentRates.get(asset.getAbbreviation())));
        }
        return allAssetsWithRate;
    }

    public Asset getByAbbreviation(String abbreviation) {
        Asset assetToShow = rootRepository.getByAbbreviation(abbreviation);
        return assetToShow;
    }

    public ArrayList<String> getListAbbreviations() {
        ArrayList<String> listAbbreviations = rootRepository.getListAbbreviations();
        return listAbbreviations;
    }

}
