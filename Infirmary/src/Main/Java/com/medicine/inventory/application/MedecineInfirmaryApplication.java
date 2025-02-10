package com.medicine.inventory.application;

import com.medicine.inventory.application.app.facade.MedicineFacade;
import com.medicine.inventory.application.app.facade.impl.MedicineFacadeImpl;

import java.util.Scanner;

public class MedecineInfirmaryApplication {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the ID of the inventory item to delete: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a numeric ID.");
                return;
            }

            int id = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            System.out.print("Are you sure you want to delete item ID " + id + "? (Type 'Confirm' to proceed or 'Cancel' to abort): ");
            String confirmation = sc.nextLine().trim();

            if (!confirmation.equalsIgnoreCase("Confirm")) {
                System.out.println("Action aborted. Item remains in inventory.");
                return;
            }

            MedicineFacade med = new MedicineFacadeImpl();
            boolean result = med.deleteById(id);

            if (result) {
                System.out.println("Item successfully deleted.");
            } else {
                System.out.println("Item not found or cannot be deleted.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}



//
//        Medicine medicine = medicineDao.deleteById(id);
//        if (medicine == null) {
//            System.out.println("Medicine not found in inventory.");
//            return;
//        }
//
//        System.out.println("Are you sure you want to delete this item? This action cannot be undone.");
//        System.out.println("Type 'Confirm' to proceed or 'Cancel' to abort.");
//        String confirmation = sc.nextLine().trim();
//
//        if (confirmation.equalsIgnoreCase("Confirm")) {
//            boolean result = medicineDao.deleteById(id);
//            if (result) {
//                System.out.println("Item successfully deleted.");
//            } else {
//                System.out.println("Error: Medicine could not be deleted.");
//            }
//        } else {
//            System.out.println("Deletion aborted. Item remains in inventory.");
//        }
//    }

