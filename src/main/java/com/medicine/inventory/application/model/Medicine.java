package com.medicine.inventory.application.model;

public class Medicine {
    private String medicineId;
    private String medicineName;
    private String brand;
    private String dosage;
    private String medDescription;
    private int quantityAvailable;
    private int quantityUsed;
    private int medTotalUsage;

    public Medicine(String medicineId, String medicineName, String brand, String dosage, String medDescription, int quantityAvailable, int quantityUsed, int medTotalUsage) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.brand = brand;
        this.dosage = dosage;
        this.medDescription = medDescription;
        this.quantityAvailable = Math.max(quantityAvailable, 0);
        this.quantityUsed = Math.max(quantityUsed, 0);
        this.medTotalUsage = Math.max(medTotalUsage, 0);
    }


    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
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

    public String getMedDescription() {
        return medDescription;
    }

    public void setMedDescription(String medDescription) {
        this.medDescription = medDescription;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = Math.max(quantityAvailable, 0);
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = Math.max(quantityUsed, 0);
    }

    public int getMedTotalUsage() {
        return medTotalUsage;
    }

    public void setMedTotalUsage(int medTotalUsage) {
        this.medTotalUsage = Math.max(medTotalUsage, 0);
    }


    public void useMedicine(int amount) {
        if (amount > 0 && amount <= quantityAvailable) {
            quantityAvailable -= amount;
            quantityUsed += amount;
            medTotalUsage += amount;
        } else {
            System.out.println("Invalid amount. Check availability.");
        }
    }

    public void restockMedicine(int amount) {
        if (amount > 0) {
            quantityAvailable += amount;
        } else {
            System.out.println("Invalid amount. Restock value must be positive.");
        }
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineId='" + medicineId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", brand='" + brand + '\'' +
                ", dosage='" + dosage + '\'' +
                ", medDescription='" + medDescription + '\'' +
                ", quantityAvailable=" + quantityAvailable +
                ", quantityUsed=" + quantityUsed +
                ", medTotalUsage=" + medTotalUsage +
                '}';
    }
}
