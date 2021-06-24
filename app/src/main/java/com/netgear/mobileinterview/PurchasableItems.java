package com.netgear.mobileinterview;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PurchasableItems {
    private static PurchasableItems instance;

    public HashMap<String, PurchasableItem> purchasables = new HashMap<>();

    public PurchasableItem createPurchasable(String sku, String description, int price, int stock) throws Exception {
        if (getPurchasableSku(sku) != null) {
            throw new Exception("SkuAlreadyExists");
        }
        String uuid = UUID.randomUUID().toString();
        PurchasableItem item = new PurchasableItem(uuid, sku, description, price, stock);
        purchasables.put(item.itemId, item);
        return new PurchasableItem(uuid, sku, description, price, stock);
    }

    public PurchasableItem getPurchasable(String itemId) {
        return purchasables.get(itemId);
    }

    public PurchasableItem getPurchasableSku(String sku) {
        for (Map.Entry<String, PurchasableItem> purchasableItemEntry : purchasables.entrySet()) {
            PurchasableItem item = (PurchasableItem) purchasableItemEntry.getValue();
            if (item.sku.equals(sku)) {
                return item;
            }
        }
        return null;
    }

    public PurchasableItem updatePurchasable(String sku, int price, int stock) throws Exception {
        PurchasableItem purchasableItem = getPurchasableSku(sku);
        if (purchasableItem != null) {
            purchasableItem.stock = stock;
            purchasableItem.price = price;
            purchasables.put(purchasableItem.itemId, purchasableItem);
        } else {
            throw new Exception("SkuNotFound");
        }
        return purchasableItem;
    }

    public void removePurchasable(UUID itmId) {
        // Removing itmId from purchasables
        purchasables.remove(itmId);
    }

    public void reset() {
        purchasables.clear();
    }

    public static PurchasableItems getInstance() {
        if (PurchasableItems.instance == null) {
            instance = new PurchasableItems();
        }
        return instance;
    }
}