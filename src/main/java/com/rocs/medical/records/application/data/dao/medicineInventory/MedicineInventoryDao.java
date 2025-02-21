package com.rocs.medical.records.application.data.dao.medicineInventory;

import com.rocs.medical.records.application.InfirmarySystemApplication;
import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;

import java.util.List;

public interface MedicineInventoryDao {

    MedicineInventory findItemByMedicine_Id(String Medicine_Id);

    List<MedicineInventory> findAllMedicine(Class<InfirmarySystemApplication> endDate);

}
