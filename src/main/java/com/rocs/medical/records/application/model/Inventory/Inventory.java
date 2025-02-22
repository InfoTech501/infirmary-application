package com.rocs.medical.records.application.model.Inventory;

import java.sql.Timestamp;

public class Inventory {

    private int InventoryId;
    private String MedicineId;
    private String ItemType;
    private String Description;
    private int QuantityAvailable;

    private Timestamp ExpirationDate;



    public Inventory() {
    }


    public Inventory(int InventoryId, String MedicineId, String ItemType, String Description,int QuantityAvailable,Timestamp ExpirationDate) {
        this.InventoryId = InventoryId;
        this.MedicineId = MedicineId;
        this.ItemType = ItemType;
        this.Description = Description;
        this.QuantityAvailable = QuantityAvailable;
        this.ExpirationDate = ExpirationDate;


    }


    public int getInventoryId() {
        return InventoryId;
    }
    public void setInventoryId(int InventoryId) {
        this.InventoryId = InventoryId;
    }


    public String getMedicineId() {
        return MedicineId;
    }
    public void setMedicineId(String MedicineId) {
        this.MedicineId = MedicineId;
    }


    public String getItemType() {
        return ItemType;
    }
    public void setItemType(String ItemType) {
        this.ItemType = ItemType;
    }


    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }


    public int getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(int QuantityAvailable) {
        this.QuantityAvailable = QuantityAvailable;
    }



    public Timestamp getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Timestamp ExpirationDate) {
        this.ExpirationDate = ExpirationDate;
    }



}







