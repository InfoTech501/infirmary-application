package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.app.facade.medicineInventory.MedicineInventoryFacade;
import com.rocs.medical.records.application.data.dao.medicineInventory.MedicineInventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.MedicineInventoryDaoImpl;
import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;
import jdk.jfr.Timestamp;

import java.util.List;

public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao;

    public MedicineInventoryFacadeImpl(){
        this.medicineInventoryDao = new MedicineInventoryDaoImpl();
    }
    @Override
    public List<MedicineInventory> getAllMedicine() {
        List<MedicineInventory>medicineInventoryList = medicineInventoryDao.findAllMedicine();
        for (MedicineInventory inventory: medicineInventoryList){
            medicineInventoryList(inventory.getItem_name(),inventory.getQuantity_available(),inventory.getExpiration_date());
        }return medicineInventoryList;

    }

    public void medicineInventoryList(String itemName, int quantityAvailable, Timestamp expirationDate) {
        System.out.println("Welcome to Medicine Inventory");
        System.out.println("Medicine Name"+ itemName);
        System.out.println("Stuck Level "+ quantityAvailable);
        System.out.println("Expiration Date"+ expirationDate);

    }


}
