package com.rocs.medical.records.application.model.medicineInfirmary;

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
