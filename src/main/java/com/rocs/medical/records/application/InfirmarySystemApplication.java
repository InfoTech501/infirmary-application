package com.rocs.medical.records.application;
import com.rocs.medical.records.application.app.facade.InventoryFacade;
import com.rocs.medical.records.application.app.facade.impl.InventoryFacadeImpl;
import com.rocs.medical.records.application.model.Inventory.Inventory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;




public class InfirmarySystemApplication {
    public static void main(String[] args) {

        InventoryFacade inventoryFacade = new InventoryFacadeImpl();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Infirmary System Application");
        System.out.println("Please select which report:");
        System.out.println("1 - Common Ailments Report");
        System.out.println("2 - Medication Trend Report");
        System.out.println("3 - Retrieve Student Medical Record");
        System.out.println("4 - View Inventory List");


        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        switch (choice) {
            case 4: {
                List<Inventory> Inventorys = inventoryFacade.getInventoryItems();

                if (Inventorys.isEmpty()) {
                    System.out.println("The list of items is empty.");
                } else {
                    System.out.println("LIST OF ITEMS:");
                    for (Inventory Inventory : Inventorys) {

                        System.out.println();
                        System.out.println("NameofMedicine:  " + Inventory.getDescription());
                        System.out.println("Stock:  " + Inventory.getQuantityAvailable());
                        System.out.println("Description:  " + Inventory.getItemType());
                        System.out.println("ExpirationDate:  " + Inventory.getExpirationDate());
                        System.out.println();

                    }
                }


            }


        }
    }
}



