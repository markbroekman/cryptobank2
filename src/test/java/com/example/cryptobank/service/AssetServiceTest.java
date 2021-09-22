package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


class AssetServiceTest {

    @Test
    void getByAbbreviation() {
        RootRepository mockRepo = Mockito.mock(RootRepository.class);
        AssetService assetService = new AssetService(mockRepo);
        CryptoCurrencyRate currencyRate = new CryptoCurrencyRate("Ada", 2.65, LocalDateTime.now());
        Asset assetToFind = new Asset("ADA", "Cardano", "ADA", currencyRate);
        Mockito.when(mockRepo.getByAbbreviation("ADA")).thenReturn(assetToFind);

        Asset actual = assetService.getByAbbreviation("ADA");
        assertThat(actual).isEqualTo(assetToFind);
    }

}