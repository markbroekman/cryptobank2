package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.*;
import com.example.cryptobank.dto.OrderDto;
import com.example.cryptobank.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private RootRepository rootRepository;
    private SendMailService mailService;
    private Bank bank = Bank.getInstance();
    private final int BUY_NOW_ORDER = 1;    // TODO enum van maken
    private final int SELL_NOW_ORDER = 2;
    private final int BUY_LATER_ORDER = 3;
    private final int SELL_LATER_ORDER = 4;
    private final double TRANSACTION_RATE = 0.03;   // TODO transaction rate instelbaar maken

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, SendMailService mailService) {
        this.mailService = mailService;
        this.rootRepository = rootRepository;
        logger.info("New TransactionService");
    }

    public TransactionDto getTransactionDto(Transaction transaction) {
        int transactionId = transaction.getTransactionId();
        String ibanBuyer = transaction.getBuyerAccount().getIban();
        String ibanSeller = transaction.getSellerAccount().getIban();
        String assetAbbr = transaction.getAsset().getAbbreviation();
        double assetAmount = transaction.getAssetAmount();
        double singleAssetPrice = transaction.getAssetPrice();
        double transactionCost = transaction.getTransactionCost();
        LocalDateTime dateTimeProcessed = transaction.getDateTimeTransaction();
        return new TransactionDto(transactionId, ibanBuyer, ibanSeller, assetAbbr, assetAmount,
                singleAssetPrice, transactionCost, dateTimeProcessed);
    }

    public TransactionDto findByTransactionId(int transactionId) {
        Transaction transaction = rootRepository.findByTransactionId(transactionId);
        return getTransactionDto(transaction);
    }

    public TransactionDto completeTransaction(OrderDto orderToProcess) {
        TransactionDto transactionToComplete = assembleNewTransactionDto(orderToProcess);

        if (validateCreditLimitBuyer(orderToProcess)
                && validatePortfolioSellerContainsAsset(orderToProcess)
                && validateAssetAmountSeller(transactionToComplete)) {

            updateBankAccount(transactionToComplete, orderToProcess);
            updatePortfolio(transactionToComplete);
            saveTransaction(transactionToComplete);
            logger.info("Transactie opgeslagen");
            sendConfirmationMailTransaction(transactionToComplete);
            return transactionToComplete;
        }

        logger.info("Transactie NIET opgeslagen");
        return null;
    }

    private String getIbanBuyer(OrderDto orderToProcess) {
        String ibanBuyer;
        int orderType = orderToProcess.getOrderType();
        switch (orderType) {
            case BUY_NOW_ORDER:
                // koop nu van bank:
                ibanBuyer = orderToProcess.getIban();
                logger.info("OrderType: 1 (buy now from bank)");
                break;
            case SELL_NOW_ORDER:
                // verkoop nu aan bank
                ibanBuyer = bank.getBankAccount().getIban();
                logger.info("OrderType: 2 (Sell now to bank)");
                break;
            case BUY_LATER_ORDER:
            case SELL_LATER_ORDER:
            default:
                logger.error("getIbanBuyer() orderType does not exist: " + orderType);
                return null;
        }
        return ibanBuyer;
    }

    private String getIbanSeller(OrderDto orderToProcess) {
        String ibanSeller;
        int orderType = orderToProcess.getOrderType();
        switch (orderType) {
            case BUY_NOW_ORDER:
                // koop nu van bank:
                ibanSeller = bank.getBankAccount().getIban();
                logger.info("OrderType: 1 (buy now from bank)");
                break;
            case SELL_NOW_ORDER:
                // verkoop nu aan bank
                ibanSeller = orderToProcess.getIban();
                logger.info("OrderType: 2 (Sell now to bank)");
                break;
            case BUY_LATER_ORDER:
            case SELL_LATER_ORDER:
            default:
                logger.error("getIbanSeller() orderType does not exist: " + orderType);
                return null;
        }
        return ibanSeller;
    }

    private double calculateAssetCost(OrderDto orderToProcess) {
        double assetCost = orderToProcess.getDesiredPrice() * orderToProcess.getAssetAmount();
        return assetCost;
    }

    private double calculateTransactionCost(OrderDto orderToProcess) {
        double transactionCost = calculateAssetCost(orderToProcess) * TRANSACTION_RATE;
//        if (orderToProcess.getOrderType()==SELL_NOW_ORDER{
//            transactionCost=transactionCost*-1;
//        }
        return transactionCost;
    }

    private double calculateAmountToPayReceive(OrderDto orderToProcess) {
        int orderType = orderToProcess.getOrderType();
        double assetCost = calculateAssetCost(orderToProcess);
        double transactionCost = calculateTransactionCost(orderToProcess);
        double amountToPayReceive = 0;
        switch (orderType) {
            case BUY_NOW_ORDER:
                // Customer is buyer and pays asset cost as well as transaction cost
                amountToPayReceive = assetCost + transactionCost;
                break;
            case SELL_NOW_ORDER:
                // Bank is buyer and pays asset cost minus transaction cost
                amountToPayReceive = assetCost - transactionCost;
                break;
            case BUY_LATER_ORDER:
            case SELL_LATER_ORDER:
            default:
                logger.error("calculateAmountToPayReceive() orderType does not exist: " + orderType);
        }
        return amountToPayReceive;
    }

    private boolean validateCreditLimitBuyer(OrderDto orderToProcess) {
        logger.info("Check if buyer has enough money");
        double balanceBuyer = rootRepository.getBalanceByIban(getIbanBuyer(orderToProcess));
        double amountToPay = calculateAmountToPayReceive(orderToProcess);
        if (amountToPay >= balanceBuyer) {
            logger.info("Insufficient funds !!!");
            return false;
        }
        return true;
    }

    private boolean validatePortfolioSellerContainsAsset(OrderDto orderToProcess) {
        String assetAbbr = orderToProcess.getAssetAbbr();
        String ibanSeller = getIbanSeller(orderToProcess);
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanSeller);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        logger.info("Portfolio seller does not contain asset");
        return false;
    }

    // nog dubbel
    private boolean checkIfPortfolioBuyerContainsAsset(TransactionDto transactionToComplete) {
        String assetAbbr = transactionToComplete.getAssetAbbr();
        String ibanBuyer = transactionToComplete.getIbanBuyer();
        List<String> assetAbbrList = rootRepository.getAbbreviationsByIban(ibanBuyer);
        for (String abbreviation: assetAbbrList) {
            if (abbreviation.equals(assetAbbr)) {
                return true;
            }
        }
        logger.info("Portfolio buyer does not contain asset");
        return false;
    }

    private boolean validateAssetAmountSeller(TransactionDto transactionToComplete) {
        logger.info("Check if seller has sufficient assets");
        double amountToRemove = transactionToComplete.getAssetAmount();
        String assetAbbr = transactionToComplete.getAssetAbbr();
        String ibanSeller = transactionToComplete.getIbanSeller();
        double amountInPortfolio = rootRepository.getAssetAmountByIbanAndAbbr(ibanSeller, assetAbbr);
        if (amountInPortfolio >= amountToRemove) {
            return true;
        }
        logger.info("Not enough Assets to sell !!!");
        return false;
    }

    //Hier nog transaction en order mee
    private void updateBankAccount(TransactionDto transactionToComplete, OrderDto orderToProcess) {
        double amountToPayReceive = calculateAmountToPayReceive(orderToProcess);
        double updatedBalanceBuyer = rootRepository.withdraw(transactionToComplete.getIbanBuyer(), amountToPayReceive);
        double updatedBalanceSeller = rootRepository.deposit(transactionToComplete.getIbanSeller(), amountToPayReceive);
        logger.info("BalanceBuyer update: " + updatedBalanceBuyer + ", BalanceSeller updated: " + updatedBalanceSeller
                + ", with: " + amountToPayReceive);
    }

    private void updatePortfolio(TransactionDto transactionToComplete) {
        String assetAbbr = transactionToComplete.getAssetAbbr();
        double assetAmount = transactionToComplete.getAssetAmount();
        boolean portfolioBuyerContainsAsset = checkIfPortfolioBuyerContainsAsset(transactionToComplete);
        if (!portfolioBuyerContainsAsset) {
            rootRepository.insertAssetIntoPortfolio(transactionToComplete);
            logger.info("New asset inserted into ownedAsset table: " + assetAbbr);
        }
        rootRepository.updateAssetAmountNegative(transactionToComplete);
        rootRepository.updateAssetAmountPositive(transactionToComplete);
        logger.info("Asset removed from portfolio seller & added to portfolio buyer: " + assetAbbr + ", " + assetAmount);
    }

    private TransactionDto assembleNewTransactionDto(OrderDto orderToProcess) {
        TransactionDto transactionToComplete = new TransactionDto();
        transactionToComplete.setAssetAbbr(orderToProcess.getAssetAbbr());
        transactionToComplete.setAssetAmount(orderToProcess.getAssetAmount());
        transactionToComplete.setSingleAssetPrice(orderToProcess.getDesiredPrice());
        transactionToComplete.setIbanBuyer(getIbanBuyer(orderToProcess));
        transactionToComplete.setIbanSeller(getIbanSeller(orderToProcess));
        transactionToComplete.setTransactionCost(calculateTransactionCost(orderToProcess));
        transactionToComplete.setDateTimeProcessed(LocalDateTime.now());
        return transactionToComplete;
    }

    public TransactionDto saveTransaction(TransactionDto transaction) {
        return rootRepository.saveTransaction(transaction);
    }

    //TODO mailadres van koper/verkoper ophalen via customer (getCustomerByIban)
    public void sendConfirmationMailTransaction(TransactionDto transactionDto) {
        Mail mail = new Mail();
        mail.setRecipient("anne.van.der.veer@hva.nl");
        mail.setSubject("Confirmation transaction Cryptoknights");
        mail.setMessage(transactionDto.toString());
        mailService.sendMail(mail);
    }

}