package com.medicine.inventory.application.model.medicine;

import java.sql.Timestamp;

public class MedicineInventory {
    private String medicine_Id;

    private String medicine_Name;

    private String brand;

    private String dosage;

    private String med_Description;

    private  int quantity_Available;

    private int quantity_Used;

    private int med_Total_Usage;

    private Timestamp expiration_Date;
    private int expirationDate;

    public MedicineInventory() {
    }

    public MedicineInventory(String medicine_Id, String medicine_Name, String brand, String dosage,
                             String med_Description, int quantity_Used, int quantity_Available,
                             int med_Total_Usage, Timestamp expiration_Date) {
        this.medicine_Id = medicine_Id;
        this. medicine_Name= medicine_Name;
        this.brand = brand;
        this.dosage = dosage;
        this.med_Description = med_Description;
        this.quantity_Available =quantity_Available;
        this.quantity_Used = quantity_Used;
        this.med_Total_Usage = med_Total_Usage;
        this.expiration_Date = expiration_Date;
    }

    public String getMedicine_Id() {
        return medicine_Id;
    }

    public void setMedicine_Id(String medicine_Id) {this.medicine_Id = medicine_Id;}

    public String getMedicine_Name() {
        return medicine_Name;
    }

    public void setMedicine_Name(String medicine_Name) {
        this.medicine_Name = medicine_Name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getMed_Description() {
        return med_Description;
    }

    public void setMed_Description(String med_Description) {
        this.med_Description = med_Description;
    }

    public  int getQuantity_Available(){return quantity_Available;}

    public void setQuantity_Available(int quantity_Available){this.quantity_Available = quantity_Available;}
    
    public int getQuantity_Used() {
        return quantity_Used;
    }

    public void setQuantity_Used(int quantity_Used) {
        this.quantity_Used = quantity_Used;
    }

    public int getMed_Total_Usage() {
        return med_Total_Usage;
    }

    public void setMed_Total_Usage(int med_Total_Usage) {
        this.med_Total_Usage = med_Total_Usage;
    }

    public Timestamp getExpiration_Date() {
        return expiration_Date;
    }

    public void setExpiration_Date(Timestamp expiration_Date)
    {
        this.expiration_Date = expiration_Date;
    }

    public void setExpiration_Date(int expirationDate) {

        this.expirationDate = expirationDate;
    }

    }

