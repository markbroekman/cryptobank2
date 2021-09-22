package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PortfolioServiceTest {
    private final Logger logger = LoggerFactory.getLogger(PortfolioServiceTest.class);


    @Autowired
    public PortfolioServiceTest() {
        logger.info("New PortfolioServiceTest");
    }

    @Test
    public void getPortfolioTest(){
        RootRepository mockRepo = Mockito.mock(RootRepository.class);

        Customer customer = new Customer(new BankAccount("NL00TEST0000000000", 5000000));

        String iban = customer.getBankAccount().getIban();

        Map<String, Double> assetMap = new HashMap<>();
        assetMap.put("BTC", 0.35);
        assetMap.put("DOGE", 500.00);
        Portfolio portfolio = new Portfolio();
        portfolio.setAssetMap(assetMap);
        customer.setPortfolio(portfolio);
        System.out.println(customer);

        System.out.println("MAP MAP " + customer.getPortfolio().getAssetMap());

        Mockito.when(mockRepo.getPortfolioByIban(iban)).thenReturn(portfolio);
        Portfolio expectedPortfolio = mockRepo.getPortfolioByIban(iban);

        assertThat(customer.getPortfolio()).isEqualTo(expectedPortfolio);
    }

    @Test
    public void getTotalValue(){

    }

}