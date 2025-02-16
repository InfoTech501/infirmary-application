package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.medicineInventory.MedicineInventoryFacade;
import com.rocs.medical.records.application.app.facade.medicineInventory.impl.MedicineInventoryImplFacade;
import com.rocs.medical.records.application.model.inventory.Inventory;

import java.util.List;
import java.util.Scanner;

public class MedicineInventoryApplicationSystem {
    private static String medicine_Name;

    public static void main(String[] args) {

        MedicineInventoryFacade medicineInventoryReportFacade = new MedicineInventoryImplFacade();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Medicine Inventory system...");
        System.out.println("Please enter your selection:");
        System.out.println("1. Medicine inventory Application");
        System.out.println("2. Return");
        System.out.println("Press other keys to exit.");
        System.out.print("Enter you choice: ");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                int choice;
            {

                System.out.println("Medicine Inventory ");
                System.out.println("Please select an option:");
                System.out.println("1. Display all Medicine Names");
                System.out.println("2. Search for an Medicine Name");
                System.out.print("Enter you choice: ");
                choice = sc.nextInt();

            }
            switch (choice) {
                case 1: {
                    sc.nextLine();

                    List<Inventory> medicineInventories = (medicineInventoryReportFacade.getAllMedicine(medicine_Name));

                    if (medicineInventories.isEmpty()) {
                        System.out.println("The list of medicine Name is empty.");
                    } else {
                        System.out.println("List of medicine Name:");
                        for (Inventory medicineInventory : medicineInventories) {
                            System.out.println(medicineInventory.getInventory_Id());
                        }
                    }
                    break;
                }
                case 2: {

                    sc.nextLine();

                    System.out.println("Enter the Medicine Name of the medicine to search: ");

                    int inventory_id = Integer.parseInt(sc.nextLine());
                    Inventory medicineInventory = medicineInventoryReportFacade.getItemByInventory_Id(inventory_id);

                    if (medicineInventory != null) {
                        System.out.println("Medicine found: " + medicineInventory.getInventory_Id() + " " + medicineInventory.getMedicine_id());
                    } else {
                        System.out.println("No medicine found.");
                    }
                    break;
                }

                default:
                    throw new IllegalStateException("Unexpected value: " + choice);


            }
            System.out.println("exit the application...");
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + input);
        }

    }
}
