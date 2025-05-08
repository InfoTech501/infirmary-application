package com.rocs.infirmary.application.app.facade.medicine.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.List;

public interface MedicineInventoryFacade {
    /**
     *Displays all available medical supplies and details.
     * Displays a list of Inventory Items(medicine) with details such as medicine name, stocks and Expiration date.
     */

    List<Medicine> findAllMedicine();

    boolean deleteMedicineByItemName(String itemName);

    boolean IsAvailable(String itemName);
}
