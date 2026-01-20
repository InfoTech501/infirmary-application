package com.rocs.infirmary.application.data.model.report.lowstock;

public class LowStockItems {
    private String description;
    private final int quantityAvailable;

    public LowStockItems(String description, int quantityAvailable) {
        this.description = description;
        this.quantityAvailable = quantityAvailable;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
