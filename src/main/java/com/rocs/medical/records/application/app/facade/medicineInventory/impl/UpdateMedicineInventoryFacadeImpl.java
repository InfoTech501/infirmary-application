package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.data.dao.medicineInventory.UpdateMedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.UpdateMedicineInventoryDaoImpl;
import com.rocs.medical.records.application.app.facade.medicineInventory.UpdateMedicineInventoryFacade;
import com.rocs.medical.records.application.model.inventory.UpdateMedicineInventory;

public class UpdateMedicineInventoryFacadeImpl implements UpdateMedicineInventoryFacade {
    private final UpdateMedicineInventoryDao updateMedicineDAO = new UpdateMedicineInventoryDaoImpl();

    @Override
    public boolean updateMedicineInventory(int newInventoryId, int newQuantity, String newDescription) {
        UpdateMedicineInventory medicine = new UpdateMedicineInventory(newInventoryId, newQuantity, newDescription);
        return this.updateMedicineDAO.updateMedicineInventory(medicine);
    }
}
