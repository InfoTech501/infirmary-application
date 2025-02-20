package com.rocs.medical.records.application.app.facade.medicineInventory;

import com.rocs.medical.records.application.model.Inventory.Inventory;

import java.util.List;

public interface InventoryFacade {

    /**
     *Displays all available medical supplies and details
     *
     * @return a list of Inventory Items(medicine) with details such as medicine name, stocks and Expiration date.
     */
    default void displayInventoryItems() {
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

    List<Inventory> getInventoryItems();
}