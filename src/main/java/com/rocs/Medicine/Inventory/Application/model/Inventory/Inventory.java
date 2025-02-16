package com.rocs.Medicine.Inventory.Application.model.Inventory;


public class Inventory {

    private int InventoryId;
    private String MedicineId;
    private String ItemType;
    private String Description;
    private int QuantityAvailable;




    public Inventory() {
    }


    public Inventory(int InventoryId, String MedicineId, String ItemType, String Description,int QuantityAvailable) {
        this.InventoryId = InventoryId;
        this.MedicineId = MedicineId;
        this.ItemType = ItemType;
        this.Description = Description;
        this.QuantityAvailable = QuantityAvailable;


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




}





