package com.rocs.medical.records.application.app.facade.updateMedicineInventory.impl;

import com.rocs.medical.records.application.data.dao.updateMedicineInventory.UpdateMedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.updateMedicineInventory.impl.UpdateMedicineInventoryDaoImpl;
import com.rocs.medical.records.application.app.facade.updateMedicineInventory.UpdateMedicineInventoryFacade;
import com.rocs.medical.records.application.model.update.UpdateMedicineInventory;

public class UpdateMedicineInventoryFacadeImpl implements UpdateMedicineInventoryFacade {
    private final UpdateMedicineInventoryDao updateMedicineDAO = new UpdateMedicineInventoryDaoImpl();

    @Override
    public boolean updateMedicineInventory(UpdateMedicineInventory medicine) {
        return this.updateMedicineDAO.updateMedicineInventory(medicine);
    }
}
