package com.example.cryptobank.controller;

import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.dto.OwnedAssetDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.PortfolioService;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.PortfolioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PortfolioController {
    private final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    private PortfolioService portfolioService;
    private CustomerService customerService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, CustomerService customerService) {
        this.portfolioService = portfolioService;
        this.customerService = customerService;
        logger.info("New PortfolioController");
    }

    @GetMapping("/portfolio")
    public ResponseEntity<?> getPortfolio (@RequestHeader("Authorization") String accessToken){
        logger.info("get portfolio aangeroepen");
        CustomerDto customer = customerService.authenticate(accessToken);
        logger.info(String.valueOf(customer));
        if (customer != null){
            PortfolioDto portfolioDto = portfolioService.showPortfolioDto(customer.getIban(), customer.getFirstName());
            List<OwnedAssetDto> ownedAssetsList = portfolioDto.getList();
            logger.info(ownedAssetsList.toString());
            return new ResponseEntity<>(ownedAssetsList, HttpStatus.OK);
            //return new ResponseEntity<String>(portfolioDto.toString(), HttpStatus.OK);
        } else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/portfolio/portfoliovalue")
    public ResponseEntity<?> getPortfolioValue (@RequestHeader("Authorization") String accessToken){
        logger.info("Portfolio value called");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer != null){
        String iban = customer.getIban();
        double portfolioValue = portfolioService.getTotalValuePortfolio(iban);
        return new ResponseEntity<>(portfolioValue, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/portfolio/abbreviations")
    public ResponseEntity<?> getAbbreviations (@RequestHeader("Authorization") String accessToken){
        logger.info("Portfolio value called");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer != null){
            List<String> abbreviationsList = portfolioService.getAbbreviationsListByIban(customer.getIban());
            return new ResponseEntity<>(abbreviationsList, HttpStatus.OK);
        } else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }
}