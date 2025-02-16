package com.rocs.medical.records.application.data.dao.medicineInventory;

import com.rocs.medical.records.application.model.inventory.Inventory;
import com.rocs.medical.records.application.model.medicine.Medicine;

import java.util.List;

public interface MedicineInventoryDao {
    Inventory findItemByInventory_Id(int Inventory_Id);

    List<Inventory> findAllMedicine(String Medicine_Name);

    Medicine findMedicineByMedicine_Id(String Medicine_Id);

    List<Medicine> findAllItem(String Item_Name);

}
