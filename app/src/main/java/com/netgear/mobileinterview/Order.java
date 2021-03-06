package com.netgear.mobileinterview;

import java.util.HashMap;
import java.util.Map;

public class Order {
    public String orderId;
    public int customerId;
    public HashMap<Integer, OrderItem> items = new HashMap<>();

    int nLIId = 0;

    public Order(String orderId, int customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public int addOrderItem(String itemId, int quantity, int discount) {
        // Adding items to Order
        int orderItemId = nLIId;

        PurchasableItem purchasableItem = PurchasableItems.getInstance().getPurchasable(itemId);
        int stock = purchasableItem.stock;
        int newQuantity = quantity;
        if (stock < quantity) {
            newQuantity = quantity - stock;
        } else {
            purchasableItem.stock -= quantity;
        }

        OrderItem item = new OrderItem(orderItemId, itemId, newQuantity, discount);
        nLIId++;
        items.put(orderItemId, item);

        return orderItemId;
    }

    public OrderItem getOrderItem(int orderItemId) {
        OrderItem item = new OrderItem(
                items.get(orderItemId).id,
                items.get(orderItemId).itemId,
                items.get(orderItemId).quantity,
                items.get(orderItemId).discount);
        return item;
    }

    public void removeOrderItem(int orderItemId) {
        // Removing orderItemId from items
        items.remove(orderItemId);
    }

    public void updateOrderItem(int orderItemId, int quantity, int discount) {
        OrderItem item = items.get(orderItemId);
        item.quantity = quantity;
        item.discount = discount;
    }

    public int getTotal() {
        int total = 0;

        for (Map.Entry<Integer, OrderItem> integerOrderItemEntry : items.entrySet()) {
            OrderItem orderItem = (OrderItem) integerOrderItemEntry.getValue();
            PurchasableItem purchasableItem = PurchasableItems.getInstance().getPurchasable(orderItem.itemId);

            int temp = 0;
            int discount = 0;

            if (purchasableItem != null) {
                temp = purchasableItem.price * orderItem.quantity;
                discount = temp * orderItem.discount / 100;
            }
            total += temp - discount;
        }
        return total;
    }
}