package com.medicine.inventory.application.app.facade.medicine.impl;

import com.medicine.inventory.application.app.facade.medicine.MedicineInventoryFacade;
import com.medicine.inventory.application.data.Dao.medicine.MedicineInventoryDao;
import com.medicine.inventory.application.data.Dao.medicine.impl.MedicineInventoryDaoImpl;
import com.medicine.inventory.application.model.medicine.MedicineInventory;

import java.util.List;

public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao =  new MedicineInventoryDaoImpl();


    @Override
    public MedicineInventory getItemByMedicine_Id(String Medicine_Id){
        MedicineInventory medicine = this.medicineInventoryDao.findItemByMedicine_Id(Medicine_Id);
        return medicine;
    }
    @Override
    public List<MedicineInventory> getAllMedicine() {
        List<MedicineInventory> medicineList = this.medicineInventoryDao.findAllMedicine();
        return medicineList;
    }

    @Override
    public boolean addMedicine(MedicineInventory medicine) {
        return false;
    }

}


