package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        logger.info("New TransactionController");
    }

    @GetMapping("/transactions")
    public Transaction findByTransactionId(@RequestParam int transactionId) {
        return transactionService.findByTransactionId(transactionId);
    }

   /* @GetMapping("/transactions/{transactionid}")
    public Transaction findByTransactionId(@PathVariable("transactionid") int transactionId) {
        return transactionService.findByTransactionId(transactionId);
    }*/

   /* @GetMapping("/transactions/iban")
    public ArrayList<Transaction> getAllByIban(@RequestParam String iban) {
        return transactionService.getAllByIban(iban);
    }*/

    /**
     * Tijdelijk endpoint om saveTransaction te testen.
     * Complete transaction -> vanuit order?
     */
    @PutMapping(value = "/transactions/save", produces = "application/json")
    public ResponseEntity<?> saveTransaction(@RequestBody Transaction transaction) {
        logger.info(transaction.toString());
        Transaction transactionToSave = transactionService.saveTransaction(transaction);
        if (transactionToSave == null) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(transactionToSave.toString(), HttpStatus.OK);
        }
    }

    /*@PutMapping("/transactions/complete")
    public ResponseEntity<?> completeTransactionToBank(@Requestbody Order orderToProcess) {
        Transaction transactionToComplete = transactionService.completeTransactionFromBank(orderToProcess);
        if (transactionToComplete == null) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(transactionToComplete.toString(), HttpStatus.OK);
        }
    }*/

    @PutMapping(value = "/transactions/complete", produces = "application/json")
    public ResponseEntity<?> completeTransactionFromBank(@RequestBody Order orderToProcess) {
        Transaction transactionToComplete = transactionService.completeTransaction(orderToProcess);
        if (transactionToComplete == null) {
            return new ResponseEntity<>("Failed to save transaction", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(transactionToComplete.toString(), HttpStatus.OK);
        }
    }

}