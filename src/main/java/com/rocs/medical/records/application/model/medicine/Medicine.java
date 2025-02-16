package com.rocs.medical.records.application.model.medicine;

import jdk.jfr.Timestamp;

public class Medicine {


   private String medicine_id;

    private int id;

    private String item_name;

    private Timestamp expiration_Date;

    public Medicine(){}

    public Medicine (int id,String medicine_id, String item_name,Timestamp expiration_Date){
        this.id = id;
        this.medicine_id = medicine_id;
        this.item_name = item_name;
        this.expiration_Date = expiration_Date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Timestamp getExpiration_Date() {
        return expiration_Date;
    }

    public void setExpiration_Date(Timestamp expiration_Date) {
        this.expiration_Date = expiration_Date;
    }
}
