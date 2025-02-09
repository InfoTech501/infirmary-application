package com.medicine.inventory.application.data.Dao.medicine;

import com.medicine.inventory.application.model.medicine.MedicineInventory;

import java.util.List;

public interface MedicineInventoryDao {

    MedicineInventory findItemByMedicine_Id(String Medicine_Id);

    List<MedicineInventory> findAllMedicine();



}
