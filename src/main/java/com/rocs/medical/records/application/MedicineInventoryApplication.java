package com.rocs.medical.records.application;
import com.rocs.medical.records.application.app.facade.InventoryFacade;
import com.rocs.medical.records.application.app.facade.impl.InventoryFacadeImpl;
import com.rocs.medical.records.application.model.Inventory.Inventory;
import java.util.List;
import java.util.Scanner;




public class MedicineInventoryApplication {

    public static void main(String[] args) {
        InventoryFacade inventoryFacade = new InventoryFacadeImpl();
        Scanner sc = new Scanner(System.in);

        System.out.println("Nurse Desktop App...");
        System.out.println("Enter 1 to View Inventory List");
        int input = sc.nextInt();
        sc.nextLine();
        switch (input) {
            case 1: {
                List<Inventory> Inventorys = inventoryFacade.getInventoryItems();

                if (Inventorys.isEmpty()) {
                    System.out.println("The list of items is empty.");
                } else {
                    System.out.println("LIST OF ITEMS:");
                    for (Inventory Inventory : Inventorys) {

                        System.out.println();
                        System.out.println("NameofMedicine:  "+Inventory.getDescription());
                        System.out.println("Stock:  "+Inventory.getQuantityAvailable());
                        System.out.println("Description:  "+Inventory.getItemType());
                        System.out.println("ExpirationDate:  " + Inventory.getExpirationDate());
                        System.out.println();

                    }
                }
                break;

            }

            
        }
    }
}


