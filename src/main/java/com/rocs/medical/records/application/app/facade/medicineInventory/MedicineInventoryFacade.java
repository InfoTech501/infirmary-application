package com.rocs.medical.records.application.app.facade.medicineInventory;

import com.rocs.medical.records.application.model.inventory.Inventory;
import com.rocs.medical.records.application.model.medicine.Medicine;

import java.util.List;

public interface MedicineInventoryFacade {
    Inventory getItemByInventory_Id(int inventory_id);

    List<Inventory> getAllMedicine(String medicine_Name);

    Medicine getMedicineByMedicine_Id(String medicine_Id);

    List<Medicine> getAllItem_Name(String item_Name);

}
