package com.rocs.medical.records.application.app.facade.medicineInventory;

public interface UpdateMedicineInventoryFacade {
    boolean updateMedicineInventory(int newInventoryId, int newQuantity, String newDescription);
}
