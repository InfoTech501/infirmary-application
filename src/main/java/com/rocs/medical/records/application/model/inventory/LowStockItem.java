package com.rocs.medical.records.application.model.inventory;

public class LowStockItem {
    private String description;
    private int quantityAvailable;

    public LowStockItem(String description, int quantityAvailable) {
        this.description = description;
        this.quantityAvailable = quantityAvailable;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
}
