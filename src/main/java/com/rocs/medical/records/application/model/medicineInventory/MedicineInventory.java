package com.rocs.medical.records.application.model.medicineInventory;

import jdk.jfr.Timestamp;

public class MedicineInventory {
    private int inventory_id;

    private String medicine_id;

    private String item_type;

    private String description;

    private int id;

    private String item_name;

    private Timestamp expiration_date;

    private int quantity_available;

    public MedicineInventory() {
    }

    public MedicineInventory(int inventory_id, String medicine_id, String item_type,
                             String description, int quantity_available, int id, String item_name, Timestamp expiration_date) {
        this.inventory_id = inventory_id;
        this.medicine_id = medicine_id;
        this.item_type = item_type;
        this.description = description;
        this.quantity_available = quantity_available;
        this.id = id;
        this.item_name = item_name;
        this.expiration_date = expiration_date;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

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

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }
    @Override
    public String toString(){
        return "Medicine Inventory{" +
                ", inventory_id='" + inventory_id + '\'' +
                ", medicine_id=" + medicine_id +
                ", item_type=" + item_type +
                ", description=" + description +
                ", quantity_available=" + quantity_available +
                ", id=" + id +
                ", medicine_id=" + medicine_id +
                ", item_name=" + item_name +
                ", expiration_date=" + expiration_date +
                '}';
    }
}


