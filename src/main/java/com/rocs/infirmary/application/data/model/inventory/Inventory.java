package com.rocs.infirmary.application.data.model.inventory;

public class Inventory {
    private long inventoryId;
    private int quantity;
    private long medicineId;
    private String itemType;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantityAvailable(int quantity) {
        this.quantity = quantity;
    }

    public long getMedicineId() {return medicineId; }

    public void setMedicineId(long medicineId) { this.medicineId = medicineId; }

    public String getItemType () { return itemType;}

    public void setItemType(String itemType) { this.itemType = itemType; }

}