package com.example.cryptobank.database;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.dto.TransactionDto;

import java.util.List;
import java.util.Map;

public interface PortfolioDao {

    Map<String, Double> getAssetmapByIban (String iban);

    double updateAssetAmountPositive(TransactionDto transactionDto);

    double updateAssetAmountNegative(TransactionDto transactionDto);

    List<String> getAbbreviationsByIban(String iban);

    double getAssetAmountByIbanAndAbbr(String iban, String assetAbbr);

    void insertAssetIntoPortfolio(TransactionDto transactionDto);

    void deleteAssetFromPortfolio(TransactionDto transactionDto);
}
