package com.rocs.infirmary.application.data.model.inventory;

public class Inventory {
    private int inventoryId;
    private int quantity;
    private String medicineId;
    private String itemType;



    public int getInventoryId() {
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

    public String getMedicineId() {return medicineId; }

    public void setMedicineId(String medicineId) { this.medicineId = medicineId; }

    public String getItemType () { return itemType;}

    public void setItemType(String itemType) { this.itemType = itemType; }

}
