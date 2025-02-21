package com.rocs.medical.records.application.model.update;

public class UpdateMedicineInventory {
    private String newName;
    private int newQuantity;
    private final String newUnit;
    private String newExpiry;

    public UpdateMedicineInventory(String newName, int newQuantity, String newUnit, String newExpiry) {
        this.newName = newName;
        this.newQuantity = newQuantity;
        this.newUnit = newUnit;
        this.newExpiry = newExpiry;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewUnit() {
        return newUnit;
    }

    public void setNewUnit(String newUnit) {
        this.newName = newUnit;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getNewExpiry() {
        return newExpiry;
    }

    public void setNewExpiry(String newExpiry) {
        this.newExpiry = newExpiry;
    }

}
