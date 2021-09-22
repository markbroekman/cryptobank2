package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Order;
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

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private CustomerService customerService;
    private TransactionService transactionService;
    private final double TRANSACTION_RATE = 0.03;
    private final int BUY_NOW_ORDER = 1;
    private final int SELL_NOW_ORDER = 2;
    private final int BUY_LATER_ORDER = 3;
    private final int SELL_LATER_ORDER = 4;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService, TransactionService transactionService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        logger.info("New OrderController");
    }

    @PostMapping(value = "/buyassetlater", produces = "application/json")
    public ResponseEntity<?> buyAssetlater(@RequestParam String assetAbbr, @RequestParam double assetAmount, @RequestHeader("Authorization") String accessToken) {
        logger.info("/buyAssetlater aangeroepen");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        String iban = customer.getIban();
        int orderType = BUY_LATER_ORDER;
        OrderDto orderSaved = orderService.saveOrder(iban, assetAbbr, assetAmount, orderType);
        if (orderSaved == null) {
            return new ResponseEntity<String>("Failed to save order", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Order saved", HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/buyassetnow", produces = "application/json")
    public ResponseEntity<?> buyAssetnow(@RequestParam String assetAbbr, double assetAmount, @RequestHeader("Authorization") String accessToken) {
        logger.info("/buyassetnow aangeroepen");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        OrderDto orderSaved = orderService.saveOrder(customer.getIban(), assetAbbr, assetAmount, BUY_NOW_ORDER);
        // TODO /completetransaction in TransactionController apart aanroepen
        TransactionDto transactionCompleted = transactionService.completeTransaction(orderSaved);
        if (orderSaved == null) {
            return new ResponseEntity<String>("Failed to save order", HttpStatus.OK);
        } else if (transactionCompleted == null) {
            // TODO frontend afvangen: te weinig geld om te kopen/assets om te verkopen. Nieuwe koop doen?
            return new ResponseEntity<>("Failed to save transaction", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Transaction saved and completed", HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/sellassetnow", produces = "application/json")
    public ResponseEntity<?> sellAssetnow(@RequestParam String assetAbbr, double assetAmount, @RequestHeader("Authorization") String accessToken) {
        logger.info("/sellassetnow aangeroepen");
        CustomerDto customer = customerService.authenticate(accessToken);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        OrderDto orderSaved = orderService.saveOrder(customer.getIban(), assetAbbr, assetAmount, SELL_NOW_ORDER);
        // TODO /completetransaction in TransactionController apart aanroepen
        TransactionDto transactionCompleted = transactionService.completeTransaction(orderSaved);
        if (orderSaved == null) {
            return new ResponseEntity<String>("Failed to save order", HttpStatus.OK);
        } else if (transactionCompleted == null) {
            // TODO frontend afvangen: te weinig geld om te kopen/assets om te verkopen. Nieuwe koop doen?
            return new ResponseEntity<>("Failed to save transaction", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Transaction saved and completed", HttpStatus.CREATED);
        }
    }

    @GetMapping("/orders")
    public OrderDto findByOrderId(@RequestParam int orderId) {
        return orderService.findByOrderId(orderId);
    }

    @GetMapping("/orders/iban")
    public List<OrderDto> getAllOrdersByIban(@RequestParam String iban) {
        return orderService.getAllOrdersByIban(iban);
    }

    @GetMapping("/orders/transactionrate")
    public double getTransactionRate() {
        return TRANSACTION_RATE;
    }

} // end of class OrderController