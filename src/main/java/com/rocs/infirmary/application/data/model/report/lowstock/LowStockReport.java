package com.rocs.infirmary.application.data.model.report.lowstock;

public class LowStockReport {
    private String description;
    private int quantityAvailable;

    public LowStockReport(String description, int quantityAvailable) {
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
