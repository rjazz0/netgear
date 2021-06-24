package com.netgear.mobileinterview;

public class OrderItem {
    public int id;
    public String itemId;
    public int quantity = 0;
    public int discount = 0;

    public OrderItem(int id, String itemId, int quantity, int discount) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        if (discount > 100) {
            this.discount = 0;
        } else {
            this.discount = discount;
        }
    }
}