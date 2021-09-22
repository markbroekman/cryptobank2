package com.example.cryptobank.database;


import com.example.cryptobank.domain.Order;
import com.example.cryptobank.dto.OrderDto;

import java.util.ArrayList;

/**
 * @author Sarah-Jayne Nogarede
 * Dit is de DAO interface voor het object 'Order', waarin wordt aangegeven welke methoden een
 * OrderDao moet implementeren.
 */
public interface OrderDao {

    OrderDto save (OrderDto orderDto);
    Order findByOrderId (int orderId);
    ArrayList<Order> getAllByIban (String iban);
    String getAssetAbbrFromOrderId(int orderId);
    String getIbanFromOrderId(int orderId);

}
