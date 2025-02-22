package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.app.facade.medicineInventory.InventoryFacade;
import com.rocs.medical.records.application.data.dao.medicineInventory.InventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.InventoryDaoImpl;
import com.rocs.medical.records.application.model.Inventory.Inventory;

import java.util.List;

public class InventoryFacadeImpl implements InventoryFacade {

    private InventoryDao inventoryDao = new InventoryDaoImpl();

    @Override
    public List<Inventory> getInventoryItems() {
        return this.inventoryDao.getInventoryItems();
    }

    //
    public void displayInventoryItems() {
        List<Inventory> inventoryItems = getInventoryItems();

        if (inventoryItems.isEmpty()) {
            System.out.println("The list of items is empty.");
        } else {
            System.out.println("LIST OF ITEMS:");
            for (Inventory inventory : inventoryItems) {
                System.out.println("\nName of Medicine: " + inventory.getDescription() +
                        "\nStock: " + inventory.getQuantityAvailable() +
                        "\nDescription: " + inventory.getItemType() +
                        "\nExpiration Date: " + inventory.getExpirationDate() + "\n");
            }
        }
    }
}




