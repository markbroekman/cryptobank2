package com.example.cryptobank.service;

import com.example.cryptobank.domain.IbanGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
public class IbanGeneratorTest {
    private final Logger logger = LoggerFactory.getLogger(IbanGeneratorTest.class);

    @Autowired
    public IbanGeneratorTest() {
        logger.info("New IbanGeneratorTest");
    }

    @Test
    public void generate() {
        //Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("COKI").buildRandom();
        String iban = IbanGenerator.generate();
        logger.info("iban gemaakt: "+iban);
        assertThat(iban).contains("NL");
        assertThat(iban).hasSize(18);

    }

}
