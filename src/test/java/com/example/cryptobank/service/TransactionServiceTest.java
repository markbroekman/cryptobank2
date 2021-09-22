package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.dto.TransactionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class TransactionServiceTest {

    private RootRepository mockRepository;
    private TransactionService transactionService;
    private SendMailService sendMailService;

    /*@BeforeAll
    public void testSetup() {
        mockRepository= Mockito.mock(RootRepository.class);
        transactionService = new TransactionService(mockRepository);
    }*/

    @Test
    void findByTransactionId() {
//        TransactionDto testTransactionDto= new TransactionDto(1, "NL69COKI5000000003", "NL24COKI3309054260",
//                "BTC", 2, 461.23, 5.25, LocalDateTime.parse("2021-09-01 10:47:08"));
//        Mockito.when(mockRepository.findByTransactionId(1)).thenReturn(testTransactionDto);
    }

    @Test
    void saveTransaction() {

    }
}