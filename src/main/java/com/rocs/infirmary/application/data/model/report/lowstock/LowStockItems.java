package com.rocs.infirmary.application.data.model.report.lowstock;

public class LowStockItems {
    private String description;
    private int quantityAvailable;

    public LowStockItems(String description, int quantityAvailable) {
        this.description = description;
        this.quantityAvailable = quantityAvailable;
    }

    public LowStockItems() {

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

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}
