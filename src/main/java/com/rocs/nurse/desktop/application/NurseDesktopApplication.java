package com.rocs.nurse.desktop.application;


import com.rocs.nurse.desktop.application.app.facade.item.InventoryFacade;
import com.rocs.nurse.desktop.application.app.facade.item.impl.InventoryFacadeImpl;
import com.rocs.nurse.desktop.application.model.Inventory.Inventory;

import java.util.List;
import java.util.Scanner;




public class NurseDesktopApplication {

    public static void main(String[] args) {
        InventoryFacade inventoryFacade = new InventoryFacadeImpl();
        Scanner sc = new Scanner(System.in);

        System.out.println("Nurse Desktop App...");
        System.out.println("Enter 1 to View Inventory List");
        int input = sc.nextInt();
        sc.nextLine();
        switch (input) {
            case 1: {
                List<Inventory> Inventorys = inventoryFacade.getAllItems();

                if (Inventorys.isEmpty()) {
                    System.out.println("The list of items is empty.");
                } else {
                    System.out.println("LIST OF ITEMS:");
                    for (Inventory Inventory : Inventorys) {
                        System.out.println("Medicine ID:  "+Inventory.getMedicineId());
                        System.out.println("Medicine Name:  "+Inventory.getGenericName());
                        System.out.println("Brand:  "+Inventory.getMedicineBrand());
                        System.out.println("Dosage:  "+Inventory.getDosage());
                        System.out.println("Med Description:  "+Inventory.getMedDescription());
                        System.out.println("Quantity Available:  "+Inventory.getAvailableQuantity());
                        System.out.println("Quantity Used:  "+Inventory.getUsedQuantity());
                        System.out.println("Med Total Usage:  "+Inventory.getMedUsage());
                        System.out.println("Expiration Date:  "+Inventory.getExpiryDate());

                    }
                }
                break;

            }

            
        }
    }
}


