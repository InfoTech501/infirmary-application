package com.rocs.medical.records.application.model.inventory;

public class Inventory {

    private int inventory_id;

    private String medicine_id;

    private String item_type;


    private String description;

    private  int quantity_Available;


    public Inventory() {
    }

    public Inventory(int inventory_id, String medicine_id, String item_type,
                     String description, int quantity_Available) {
        this.inventory_id = inventory_id;
        this. medicine_id= medicine_id;
        this.item_type = item_type;

        this.description = description;
        this.quantity_Available =quantity_Available;

    }

    public int getInventory_Id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {this.inventory_id = inventory_id;}

    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  int getQuantity_Available(){return quantity_Available;}

    public void setQuantity_Available(int quantity_Available){
        this.quantity_Available = quantity_Available;
    }


}
