package com.medicine.inventory.application.app.facade.medicine;

import com.medicine.inventory.application.model.medicine.MedicineInventory;

import java.util.List;

public interface MedicineInventoryFacade {

    boolean addMedicine(MedicineInventory medicine);

    List<MedicineInventory> getAllMedicine();

    MedicineInventory getItemByMedicine_Id(String medicineId);
}
