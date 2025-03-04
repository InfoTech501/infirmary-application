package com.rocs.medical.records.application.model.medicineInventory;

import java.sql.Timestamp;

public class Inventory {

    private int inventoryId;
    private String medicineId;
    private String itemType;
    private String description;
    private int quantityAvailable;

    private Timestamp expirationDate;



    public Inventory() {
    }


    public Inventory(int InventoryId, String MedicineId, String ItemType, String Description,int QuantityAvailable,Timestamp ExpirationDate) {
        this.inventoryId = InventoryId;
        this.medicineId = MedicineId;
        this.itemType = ItemType;
        this.description = Description;
        this.quantityAvailable = QuantityAvailable;
        this.expirationDate = ExpirationDate;


    }


    public int getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int InventoryId) {
        this.inventoryId = InventoryId;
    }


    public String getMedicineId() {
        return medicineId;
    }
    public void setMedicineId(String MedicineId) {
        this.medicineId = MedicineId;
    }


    public String getItemType() {
        return itemType;
    }
    public void setItemType(String ItemType) {
        this.itemType = ItemType;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String Description) {
        this.description = Description;
    }


    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int QuantityAvailable) {
        this.quantityAvailable = QuantityAvailable;
    }



    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp ExpirationDate) {
        this.expirationDate = ExpirationDate;
    }



}



