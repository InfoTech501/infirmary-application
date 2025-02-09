package com.medicine.inventory.application;

import com.medicine.inventory.application.app.facade.medicine.MedicineInventoryFacade;
import com.medicine.inventory.application.app.facade.medicine.impl.MedicineInventoryFacadeImpl;
import com.medicine.inventory.application.model.medicine.MedicineInventory;


import java.util.List;
import java.util.Scanner;
public class MedicineInventoryApplication {
    public static void main(String[] args) {

        MedicineInventoryFacade medicineInventoryFacade = new MedicineInventoryFacadeImpl();
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
                System.out.println("3. Add an Medicine");
                System.out.println("4. Update an Medicine");
                System.out.println("5. Delete an Medicine");
                System.out.print("Enter you choice: ");
                choice = sc.nextInt();

            }
            switch (choice) {
                case 1: {
                    List<MedicineInventory> medicine =  medicineInventoryFacade .getAllMedicine();

                    if (medicine.isEmpty()) {
                        System.out.println("The list of medicine Name is empty.");
                    } else {
                        System.out.println("List of medicine Name:");
                        for (MedicineInventory medicineInventory : medicine) {
                            System.out.println(medicineInventory.getMedicine_Id());
                        }
                    }
                    break;
                }
                case 2: {

                    sc.nextLine();

                    System.out.println("Enter the Medicine Name of the medicine to search: ");

                    String Medicine_Id = sc.nextLine();
                    MedicineInventory medicineInventory = medicineInventoryFacade.getItemByMedicine_Id(Medicine_Id);

                    if (medicineInventory != null) {
                        System.out.println("Medicine found: " + medicineInventory.getMedicine_Id() + " " + medicineInventory.getMedicine_Name());
                    } else {
                        System.out.println("No medicine found.");
                    }
                    break;
                }

                case 3: {
                    sc.nextLine();

                    System.out.println("add medicine");
                    System.out.println("Enter medicine id: ");
                    String medicine_Id = sc.nextLine();
                    System.out.println("Enter medicine name: ");
                    String medicine_Name= sc.nextLine();
                    System.out.println("Enter medicine brand: ");
                    String brand = sc.nextLine();
                    System.out.println("Enter medicine dosage: ");
                    String dosage = sc.nextLine();
                    System.out.println("Enter medicine description: ");
                    String med_Description= sc.nextLine();
                    System.out.println("Enter medicine usage: ");
                    int quantity_Available= sc.nextInt();
                    System.out.println("Enter medicine used: ");
                    int quantity_Used= sc.nextInt();
                    System.out.println("Enter medicine usage: ");
                    int med_total_usage = sc.nextInt();
                    System.out.println("Enter expiration date");
                    int expiration_Date = sc.nextInt();

                    MedicineInventory medicineInventory = new MedicineInventory();
                    medicineInventory.setMedicine_Id(medicine_Id);
                    medicineInventory.setMedicine_Name(medicine_Name);
                    medicineInventory.setBrand(brand);
                    medicineInventory.setDosage(dosage);
                    medicineInventory.setMed_Description(med_Description);
                    medicineInventory.setQuantity_Available(quantity_Available);
                    medicineInventory.setQuantity_Used(quantity_Used);
                    medicineInventory.setMed_Total_Usage(med_total_usage);
                    medicineInventory.setExpiration_Date(expiration_Date);


                    boolean result = medicineInventoryFacade.addMedicine(medicineInventory);

                    if(result) {
                        System.out.println("Medicine successfully added.");
                    } else {
                        System.out.println("Medicine cannot be added.");
                    }

                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);


            }
            System.out.println("exit the application...");
        }

    }
}