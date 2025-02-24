package com.rocs.medical.records.application.app.facade.medicineInventory;

import com.rocs.medical.records.application.model.medicineInventory.Inventory;
import java.util.List;

public interface InventoryFacade {
    /**
     *Displays all available medical supplies and details
     *
     * @return a list of Inventory Items(medicine) with details such as medicine name, stocks and Expiration date.
     */
    List<Inventory> getInventoryItems();

    void displayInventoryItems();
}

