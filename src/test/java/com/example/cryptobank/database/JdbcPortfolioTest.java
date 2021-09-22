package com.example.cryptobank.database;
import static org.assertj.core.api.Assertions.*;

import com.example.cryptobank.domain.BankAccount;
import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.ExchangeService;
import com.example.cryptobank.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
//@ActiveProfiles("test")
public class JdbcPortfolioTest {
    private final Logger logger = LoggerFactory.getLogger(JdbcPortfolioTest.class);

    private PortfolioDao portfolioDao;
    private RootRepository rootRepository;


    public JdbcPortfolioTest(PortfolioDao portfolioDao, RootRepository rootRepository) {
        super();
        this.portfolioDao = portfolioDao;
        this.rootRepository = rootRepository;
        logger.info("New JdbcPortfolioTest");
    }

    @Test
    public void portfolioDaoNotNull(){
        assertThat(portfolioDao).isNotNull();
    }

//    @Test
//    public void
}