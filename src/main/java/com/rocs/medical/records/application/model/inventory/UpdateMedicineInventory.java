package com.rocs.medical.records.application.model.inventory;

public class UpdateMedicineInventory {
    private int newQuantity;
    private final String newDescription;
    private int newInventoryId;

    public UpdateMedicineInventory(int newInventoryId, int newQuantity, String newDescription) {
        this.newInventoryId = newInventoryId;
        this.newQuantity = newQuantity;
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }

    public int getNewInventoryId(){
        return newInventoryId;
    }

    public void setNewInventoryId(int newInventoryId) {
        this.newInventoryId = newInventoryId;
    }
}
