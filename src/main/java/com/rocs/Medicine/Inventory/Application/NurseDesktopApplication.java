package com.rocs.Medicine.Inventory.Application;


import com.rocs.Medicine.Inventory.Application.app.facade.InventoryFacade;
import com.rocs.Medicine.Inventory.Application.app.facade.impl.InventoryFacadeImpl;
import com.rocs.Medicine.Inventory.Application.model.Inventory.Inventory;

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
                        System.out.println("InventoryID:  "+Inventory.getInventoryId());
                        System.out.println("MedicineID:  "+Inventory.getMedicineId());
                        System.out.println("ItemType:  "+Inventory.getItemType());
                        System.out.println("Description:  "+Inventory.getDescription());
                        System.out.println("QuantityAvailable:  "+Inventory.getQuantityAvailable());
                        System.out.println();

                    }
                }
                break;

            }

            
        }
    }
}


