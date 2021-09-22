package com.example.cryptobank.database;

import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.dto.TransactionDto;

public interface TransactionDao {

    TransactionDto save(TransactionDto transaction);
    Transaction findByTransactionId(int transactionId);
    //ArrayList<Transaction> getAllTransactionsByIban(String iban);

    String findAssetForTransaction(int transactionId);

    String findBuyerAccountForTransaction(int transactionId);

    String findSellerAccountForTransaction(int transactionId);
}
