package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.app.facade.medicineInventory.MedicineInventoryFacade;
import com.rocs.medical.records.application.data.dao.medicineInventory.MedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.MedicineInventoryImplDao;
import com.rocs.medical.records.application.model.inventory.Inventory;
import com.rocs.medical.records.application.model.medicine.Medicine;

import java.util.List;

public class MedicineInventoryImplFacade implements MedicineInventoryFacade {


    private MedicineInventoryDao medicineInventoryDao=  new MedicineInventoryImplDao();



    @Override

    public Inventory getItemByInventory_Id(int inventory_id){
        Inventory medicineInventory = this.medicineInventoryDao.findItemByInventory_Id(inventory_id);
        return medicineInventory;
    }
    @Override
    public List<Inventory> getAllMedicine(String Medicine_Name) {
        List<Inventory> medicineInventoryList = this.medicineInventoryDao.findAllMedicine(Medicine_Name);
        return medicineInventoryList;
    }

    @Override
    public Medicine getMedicineByMedicine_Id(String medicine_Id) {
        Medicine medicineInventory = this.medicineInventoryDao.findMedicineByMedicine_Id(medicine_Id);
        return medicineInventory;
    }

    @Override
    public List<Medicine> getAllItem_Name(String item_Name) {
        List<Medicine> medicineInventoryList = this.medicineInventoryDao.findAllItem(item_Name);
        return medicineInventoryList;
    }


}
