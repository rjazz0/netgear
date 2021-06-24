package com.netgear.mobileinterview;


public class PurchasableItem {
    public String itemId;
    public String sku;
    public String description;
    public int price = 0;  // In USD, X 100 so 9.99 is 999
    public int stock = 0;

    public PurchasableItem(String itemId, String sku, String description, int price, int stock) {
        this.itemId = itemId;
        this.sku = sku;
        this.description = description;
        if (price < 0) {
            this.price = 0;
        } else {
            this.price = price;
        }
        if (stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }

}