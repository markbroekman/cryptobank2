package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.dto.OwnedAssetDto;
import com.example.cryptobank.dto.PortfolioDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {
    private final Logger logger = LoggerFactory.getLogger(PortfolioService.class);
    private RootRepository rootRepository;
    private ExchangeService exchangeService;


    public PortfolioService(RootRepository rootRepository, ExchangeService exchangeService) {
        this.rootRepository = rootRepository;
        this.exchangeService = exchangeService;
        logger.info("New PortfolioService");
    }

    public Portfolio getPortfolio (String iban){
        return rootRepository.getPortfolioByIban(iban);
    }

    public PortfolioDto showPortfolioDto (String iban, String firstName){
        Map<String, Double> assetMap = rootRepository.getPortfolioByIban(iban).getAssetMap();
        List<OwnedAssetDto> list = getList(assetMap);
        double totalValue = getTotalValuePortfolio(iban);
        return new PortfolioDto(firstName, list, totalValue);
    }

    public List<OwnedAssetDto> getList(Map<String, Double> assetMap){
        List<OwnedAssetDto> list = new ArrayList<>();
        for(Map.Entry<String, Double> entry: assetMap.entrySet()) {
            String abbreviation = entry.getKey();
            double ownedAssetAmount = entry.getValue();
            String assetName = rootRepository.getByAbbreviation(abbreviation).getName();

            double currentSinglePrice = exchangeService.getCurrentRateByAbbreviation(abbreviation);
            double subTotal = ownedAssetAmount*currentSinglePrice;
            list.add(new OwnedAssetDto(abbreviation, ownedAssetAmount, assetName, currentSinglePrice, subTotal));
        }
        return list;
    }


    public double getTotalValuePortfolio(String iban){
        double totalValue = 0;
        Portfolio portfolio = rootRepository.getPortfolioByIban(iban);
        Map<String, Double> assetMap = portfolio.getAssetMap();
        for (Map.Entry<String, Double> map : assetMap.entrySet()) {
            String abbr = map.getKey();
            Asset asset = rootRepository.getByAbbreviation(abbr);
            double rate = asset.getRate().getCryptoRate();
            double assetAmount = map.getValue();
            double assetValue = rate * assetAmount;
            totalValue += assetValue;
        }
        return totalValue;
    }

    public List<String> getAbbreviationsListByIban (String iban){
        return rootRepository.getAbbreviationsByIban(iban);
    }
}