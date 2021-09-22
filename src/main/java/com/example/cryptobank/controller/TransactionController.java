package com.example.cryptobank.controller;

import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.dto.OrderDto;
import com.example.cryptobank.dto.TransactionDto;
import com.example.cryptobank.service.CustomerService;
import com.example.cryptobank.service.OrderService;
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
    private CustomerService customerService;
    private OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService,
                                 OrderService orderService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.orderService = orderService;
        logger.info("New TransactionController");
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> findByTransactionId(@RequestParam int transactionId, @RequestHeader("Authorization") String accessToken) {
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        TransactionDto transactionDto = transactionService.findByTransactionId(transactionId);
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

    @PostMapping(value = "/completetransaction", produces = "application/json")
    public ResponseEntity<?> completeTransaction(@RequestParam int orderId, @RequestHeader("Authorization") String accessToken) {
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        OrderDto orderToProcess = orderService.findByOrderId(orderId);
        TransactionDto transactionCompleted = transactionService.completeTransaction(orderToProcess);
        if (transactionCompleted == null) {
            return new ResponseEntity<>("Failed to save transaction", HttpStatus.OK);
        }
        return new ResponseEntity<>("Transaction saved and completed", HttpStatus.CREATED);
    }



}