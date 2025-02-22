package com.rocs.medical.records.application.app.facade.medicineInventory;

import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;

import java.util.List;

public interface MedicineInventoryFacade {

    List<MedicineInventory> getAllMedicine();

}
