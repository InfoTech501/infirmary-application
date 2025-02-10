package com.rocs.nurse.desktop.application.model.Inventory;

import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

public class Inventory {

    private Number MedicineId;
    private String genericName;
    private String MedicineBrand;
    private String Dosage;
    private String MedDescription;
    private Number AvailableQuantity;
    private Number UsedQuantity;
    private Number MedUsage;
    private Timestamp ExpiryDate;





    public Inventory() {
    }


    public Inventory(Number MedicineId, String genericName, String MedicineBrand, String Dosage, String MedDescription,Number AvailableQuantity,Number UsedQuantity,Number MedUsage, TIMESTAMP expiryDate) {
        this.MedicineId = MedicineId;
        this.genericName = genericName;
        this.MedicineBrand = MedicineBrand;
        this.Dosage = Dosage;
        this.MedDescription = MedDescription;
        this.AvailableQuantity = AvailableQuantity;
        this.UsedQuantity = UsedQuantity;
        this.MedUsage = MedUsage;
        this.ExpiryDate = ExpiryDate;
    }


    public Number getMedicineId(){ return MedicineId;}
    public void setMedicineId(Number MedicineId){this.MedicineId =  MedicineId;}


    public String getGenericName(){return genericName;}
    public void setGenericName(String genericName){this.genericName = genericName; }


    public String getMedicineBrand(){return MedicineBrand;}
    public void setMedicineBrand(String MedicineBrand){this.MedicineBrand = MedicineBrand; }


    public String getDosage(){return Dosage;}
    public void setDosage(String Dosage){this.Dosage = Dosage; }


    public String getMedDescription(){return MedDescription;}
    public void setMedDiscription(String medDiscription){this.MedDescription = medDiscription;}



    public Number getAvailableQuantity(){return AvailableQuantity;}
    public void setAvailableQuantity(Number availableQuantity){this.AvailableQuantity = availableQuantity;}


    public Number getUsedQuantity(){return UsedQuantity;}
    public void setUsedQuantity(Number usedQuantity){this.UsedQuantity= usedQuantity;}


    public Number getMedUsage(){ return MedUsage;}
    public void setMedUsage(Number medUsage){this.MedUsage = medUsage;}


    public Timestamp getExpiryDate(){return ExpiryDate;}
    public  void setExpiryDate(Timestamp expiryDate){this.ExpiryDate = expiryDate;}
}


