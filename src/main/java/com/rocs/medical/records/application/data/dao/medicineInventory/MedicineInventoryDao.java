package com.rocs.medical.records.application.data.dao.medicineInventory;

import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;

import java.util.List;

public interface MedicineInventoryDao {

    List<MedicineInventory> findAllMedicine();

}
