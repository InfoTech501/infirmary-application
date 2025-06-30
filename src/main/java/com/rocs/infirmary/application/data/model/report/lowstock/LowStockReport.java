package com.rocs.infirmary.application.data.model.report.lowstock;
/**
 * This class is for a product that has low stock.
 * It keeps the product name and how many are still available.
 */
public class LowStockReport {
    private String description;
    private int quantityAvailable;

    public LowStockReport(String description, int quantityAvailable) {
        this.description = description;
        this.quantityAvailable = quantityAvailable;
    }

    public LowStockReport() {

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
