package com.example.cryptobank.database;

import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class JdbcCryptoCurrencyRateDAOTest {

    private CryptoCurrencyRateDAO cryptoCurrencyRateDAOTest;
    private final Logger logger = LoggerFactory.getLogger(JdbcCryptoCurrencyRateDAO.class);

    @Autowired
    public JdbcCryptoCurrencyRateDAOTest(CryptoCurrencyRateDAO cryptoCurrencyRateDAO) {
        this.cryptoCurrencyRateDAOTest = cryptoCurrencyRateDAO;
        logger.info("New Jdbc CryptoCurrencyDAOtest");
    }

    @Test
    void saveCryptoCurrencyTest() {
        CryptoCurrencyRate brankoCoin = new CryptoCurrencyRate("SAR", 20000 , LocalDateTime.now());
        CryptoCurrencyRate actual = cryptoCurrencyRateDAOTest.save(brankoCoin);
        logger.info("brankoCoin is : " + String.valueOf(brankoCoin));
        logger.info("de opgeslagen coin is: " + String.valueOf(actual));
        assertThat(actual).isEqualTo(brankoCoin);

    }

    // TODO: 26/08/2021 nog een fatsoenlijke test voor schrijven. deze test laat alleen zien dat de datetime correct wordt geformat ván en náár de database 
    @Test
    void getAll() {
        ArrayList<CryptoCurrencyRate> cryptoCurrencyRateArrayList = cryptoCurrencyRateDAOTest.getAll();
        CryptoCurrencyRate testje = cryptoCurrencyRateArrayList.get(1);
        logger.info(testje.getDateTime().toString());
    }

    @Test
    void findByAbbreviation() {
        ArrayList<CryptoCurrencyRate> brankoCoin = cryptoCurrencyRateDAOTest.findByAbbreviation("BRA");
        CryptoCurrencyRate expected = new CryptoCurrencyRate("BRA", 1200, LocalDateTime.now());
        logger.info("branko coin test met localdate als string: " + brankoCoin.toString());
        logger.info(expected.toString());
        assertThat(brankoCoin.get(0).getAbbreviation()).isEqualTo(expected.getAbbreviation());
    }
}