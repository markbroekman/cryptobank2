package com.example.cryptobank.controller;

import com.example.cryptobank.dto.AssetDto;
import com.example.cryptobank.service.AssetService;
import com.example.cryptobank.domain.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AssetController {

    private AssetService assetService;

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
        logger.info("New AssetController");
    }

    @GetMapping("/assets")
    public ResponseEntity<?> showAssets() {
        ArrayList<AssetDto> allAssets = assetService.getAssets();
        logger.info("Show assets aangeroepen");
        return new ResponseEntity<>(allAssets, HttpStatus.OK);
    }

    @GetMapping("/assets/{abbreviation}")
    public Asset showSpecificAsset(@PathVariable("abbreviation") String abbreviation) {
        Asset assetToShow = assetService.getByAbbreviation(abbreviation);
        logger.info("Show specific asset aangeroepen " + assetToShow);
        return assetToShow;
    }

    @GetMapping("/assets/abbreviations")
    public ArrayList showListAbbreviations() {
        return assetService.getListAbbreviations();
    }



    // TODO endpoint buy asset naar hiertoe verplaatsen vanuit transactioncontroller
    // /assets/{abbreviation}/buy
    // /asset/bte/buy

} // end of class AssetController
