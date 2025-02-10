package com.medicine.inventory.application.model.Medicine;

import com.medicine.inventory.application.data.dao.Impl.MedicineDoaImpl;
import java.util.Scanner;


public class Medicine {

    private String MedicineID;

    private String MedicineName;

    private String Brand;


    private String Dosage;

    private String MedDescription;


    private Number QuantityAvailable;

    private Number QuantityUsed;
    private Number MedTotalUsage;

    public Medicine(String medicineID, String medicineName, String brand, String dosage, String medDescription, Number quantityAvailable, Number quantityUsed, Number medTotalUsage) {
        MedicineID = medicineID;
        MedicineName = medicineName;
        Brand = brand;
        Dosage = dosage;
        MedDescription = medDescription;
        QuantityAvailable = quantityAvailable;
        QuantityUsed = quantityUsed;
        MedTotalUsage = medTotalUsage;
    }

    public String getMedicineID() {
        return MedicineID;
    }

    public void setMedicineID(String medicineID) {
        MedicineID = medicineID;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public String getMedDescription() {
        return MedDescription;
    }

    public void setMedDescription(String medDescription) {
        MedDescription = medDescription;
    }

    public Number getQuantityAvailable() {
        return QuantityAvailable;
    }

    public void setQuantityAvailable(Number quantityAvailable) {
        QuantityAvailable = quantityAvailable;
    }

    public Number getQuantityUsed() {
        return QuantityUsed;
    }

    public void setQuantityUsed(Number quantityUsed) {
        QuantityUsed = quantityUsed;
    }

    public Number getMedTotalUsage() {
        return MedTotalUsage;
    }

    public void setMedTotalUsage(Number medTotalUsage) {
        MedTotalUsage = medTotalUsage;
    }
}
