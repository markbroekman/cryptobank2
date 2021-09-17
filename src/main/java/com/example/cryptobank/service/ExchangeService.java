package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class ExchangeService {
    private RootRepository rootRepository;
    private Map<String, Double> currentRates;

    @Autowired
    public ExchangeService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
        this.currentRates = new HashMap<>();
    }

    public double getCurrentRateByAbbreviation(String abbreviation) {
        // query voor alle Rates van alle Assets (Map aanmaken en vullen)
        // Map. Entryset. Als getKey.equals abbrev. -> extract value (currentRate van Asset in kwestie)
        currentRates = rootRepository.getAllCurrentRates();
        currentRates.forEach((key, value) ->     System.out.println(key + value));
        return currentRates.get(abbreviation);
    }

    public Map<String, Double> getRates() {
        return rootRepository.getAllCurrentRates();
    }
}
