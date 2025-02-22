package com.rocs.medical.records.application.app.facade.updateMedicineInventory.impl;

import com.rocs.medical.records.application.data.dao.updateMedicineInventory.UpdateMedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.updateMedicineInventory.impl.UpdateMedicineInventoryDaoImpl;
import com.rocs.medical.records.application.app.facade.updateMedicineInventory.UpdateMedicineInventoryFacade;
import com.rocs.medical.records.application.model.inventory.UpdateMedicineInventory;

public class UpdateMedicineInventoryFacadeImpl implements UpdateMedicineInventoryFacade {
    private final UpdateMedicineInventoryDao updateMedicineDAO = new UpdateMedicineInventoryDaoImpl();

    @Override
    public boolean updateMedicineInventory(String newName, int newQuantity, String newUnit, String newExpiry) {
        UpdateMedicineInventory medicine = new UpdateMedicineInventory(newName, newQuantity, newUnit, newExpiry);
        return this.updateMedicineDAO.updateMedicineInventory(medicine);
    }
}