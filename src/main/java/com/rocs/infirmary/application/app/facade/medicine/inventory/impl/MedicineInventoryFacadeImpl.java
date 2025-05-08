package com.rocs.infirmary.application.app.facade.medicine.inventory.impl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.data.dao.medicine.inventory.impl.MedicineInventoryDaoImpl;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.List;
public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();

    @Override
    public List<Medicine> findAllMedicine() {
        return this.medicineInventoryDao.findAllMedicine();
    }
    @Override
    public boolean deleteMedicineByItemName(String itemName) {
        return medicineInventoryDao.deleteMedicine(itemName);
    }

    @Override
    public boolean IsAvailable(String itemName) {
        return medicineInventoryDao.isAvailable(itemName);
    }


}
