package com.rocs.infirmary.application.model.lowStockMedicine;

public class LowStockMedicine {
    private String description;
    private int quantityAvailable;

    public LowStockMedicine(String description, int quantityAvailable) {
        this.description = description;
        this.quantityAvailable = quantityAvailable;
    }

    public String getDescription() { return description; }
    public int getQuantityAvailable() { return quantityAvailable; }
}


