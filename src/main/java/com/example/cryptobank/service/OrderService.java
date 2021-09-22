package com.example.cryptobank.service;

import com.example.cryptobank.database.RootRepository;
import com.example.cryptobank.domain.Order;
import com.example.cryptobank.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private RootRepository rootRepository;
    private ExchangeService exchangeService;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(RootRepository rootRepository, ExchangeService exchangeService) {
        this.rootRepository = rootRepository;
        this.exchangeService = exchangeService;
        logger.info("New OrderService");
    }

    public OrderDto getOrderDto(Order order) {
        int orderId = order.getOrderId();
        String iban = order.getBankAccount().getIban();
        String assetAbbr = order.getAsset().getAbbreviation();
        double assetAmount = order.getAssetAmount();
        double desiredPrice = order.getDesiredPrice();
        LocalDateTime dateTimeCreated = order.getDateTimeCreated();
        int orderType = order.getOrderType();
        return new OrderDto(orderId, iban, assetAbbr, assetAmount, desiredPrice, dateTimeCreated, orderType);
    }

    public OrderDto assembleOrderDto(String iban, String assetAbbr, double assetAmount, int orderType) {
        OrderDto orderDto = new OrderDto();
        orderDto.setIban(iban);
        orderDto.setAssetAbbr(assetAbbr);
        orderDto.setAssetAmount(assetAmount);
        orderDto.setDesiredPrice(exchangeService.getCurrentRateByAbbreviation(assetAbbr.toUpperCase()));
        orderDto.setDateTimeCreated(LocalDateTime.now());
        orderDto.setOrderType(orderType);
        return orderDto;
    }

    public OrderDto saveOrder(String iban, String assetAbbr, double assetAmount, int orderType) {
        OrderDto orderToSave = assembleOrderDto(iban, assetAbbr, assetAmount, orderType);
        return rootRepository.saveOrder(orderToSave);
    }

    public OrderDto findByOrderId(int orderId) {
        Order orderToFind = rootRepository.findByOrderId(orderId);
        return getOrderDto(orderToFind);
    }

    public List<OrderDto> getAllOrdersByIban(String iban) {
        ArrayList<Order> ordersByIban = rootRepository.getAllOrdersByIban(iban);
        List<OrderDto> orderDtosByIban = new ArrayList<>();
        for (Order order: ordersByIban) {
            orderDtosByIban.add(getOrderDto(order));
        }
        return orderDtosByIban;
    }
}