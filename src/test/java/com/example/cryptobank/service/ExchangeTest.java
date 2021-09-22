package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ExchangeTest {
    private final Logger logger = LoggerFactory.getLogger(ExchangeTest.class);

    @Autowired
    public ExchangeTest() {
        logger.info("New ExchangeTest");
    }

    @Test
    void getCurrentRateByAbbreviation() {
        RootRepository mockRepo = Mockito.mock(RootRepository.class);
        ExchangeService exchangeService = new ExchangeService(mockRepo);
        Map<String, Double> testMap = new HashMap<>();
        testMap.put("HEX", 0.18);
        Mockito.when(mockRepo.getAllCurrentRates()).thenReturn(testMap);

        double actual = exchangeService.getCurrentRateByAbbreviation("HEX");
        assertThat(actual).isEqualTo(0.18);
    }

}
