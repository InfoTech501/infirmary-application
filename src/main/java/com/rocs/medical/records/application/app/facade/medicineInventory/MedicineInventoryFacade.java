package com.rocs.medical.records.application.app.facade.medicineInventory;

import com.rocs.medical.records.application.InfirmarySystemApplication;
import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;

import java.util.List;

public interface MedicineInventoryFacade {

    /**
     * An interface that manages the report of medication trend within the given report period
     * * @param endDate    The end date of the report period.
     * */
    List<MedicineInventory> getAllMedicine(Class<InfirmarySystemApplication> endDate);

    MedicineInventory getItemByMedicine_Id(String medicine_Id);
}
