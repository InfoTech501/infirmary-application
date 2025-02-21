package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.InfirmarySystemApplication;
import com.rocs.medical.records.application.app.facade.medicineInventory.MedicineInventoryFacade;
import com.rocs.medical.records.application.data.dao.medicineInventory.MedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.MedicineInventoryDaoImpl;
import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;

import java.util.List;

public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();

    @Override
    public List<MedicineInventory> getAllMedicine(Class<InfirmarySystemApplication> endDate) {
        return this.medicineInventoryDao.findAllMedicine(endDate);

    }

    @Override
    public MedicineInventory getItemByMedicine_Id(String medicine_Id) {
        MedicineInventory medicineInventory = this.medicineInventoryDao.findItemByMedicine_Id(medicine_Id);
        return null;
    }


}
