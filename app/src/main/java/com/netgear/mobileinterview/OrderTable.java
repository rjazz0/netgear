package com.netgear.mobileinterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OrderTable {
    public static OrderTable instance;

    HashMap<String, Order> orders = new HashMap<>();
    HashMap<Integer, ArrayList<Order>> ordersByCustomer = new HashMap<>();

    public Order createOrder(int customerId) {
        String uuid = UUID.randomUUID().toString();
        Order order = new Order(uuid, customerId);
        orders.put(order.orderId, order);
        ArrayList<Order> byCustomer = ordersByCustomer.get(customerId);
        if(byCustomer != null && !byCustomer.isEmpty()){
            byCustomer.add(order);
            ordersByCustomer.put(customerId, byCustomer);
        } else {
            byCustomer = new ArrayList<Order>();
            byCustomer.add(order);
            ordersByCustomer.put(customerId, byCustomer);
        }
        return order;
    }

    public Order getOrder(UUID orderId) {
        return orders.get(orderId);
    }

    public ArrayList<Order> getOrdersByCustomer(int customerId) {
        ArrayList<Order> orders = ordersByCustomer.get(customerId);
        if(orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    public void reset() {
        orders.clear();
        ordersByCustomer.clear();
    }

    public static OrderTable getInstance() {
        if (OrderTable.instance == null) {
            instance = new OrderTable();
        }
        return instance;
    }
}