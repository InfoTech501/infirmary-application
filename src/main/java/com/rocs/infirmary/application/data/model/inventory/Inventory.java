package com.rocs.infirmary.application.data.model.inventory;

public class Inventory {
    private Long inventoryId;
    private int quantity;
    private Long medicineId;
    private String itemType;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantityAvailable(int quantity) {
        this.quantity = quantity;
    }

    public Long getMedicineId() {return medicineId; }

    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }

    public String getItemType () { return itemType;}

    public void setItemType(String itemType) { this.itemType = itemType; }

}