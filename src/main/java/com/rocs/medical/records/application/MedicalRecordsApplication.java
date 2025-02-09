package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.MedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.app.facade.MedicalRecords.impl.MedicalRecordsFacadeImpl;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MedicalRecordsApplication {

    public static void main(String[] args) {

        MedicalRecordsFacade medcidalrecordsFacade = new MedicalRecordsFacadeImpl();
        Scanner sc = new Scanner(System.in);

        //Logic of Starting a Medical record
        System.out.println("Welcome to Medical Records System");
        System.out.println("Please enter your selection:");
        System.out.println("1. Student Record Application");
        System.out.println("2. Return");
        System.out.println("Press other keys to exit.");
        System.out.print("Enter your choice: ");

        if (sc.hasNextInt()) {
            int input = sc.nextInt();
            sc.nextLine();

            //Logic of creating or adding a new medical record.
            switch (input) {
                case 1:
                    int choice;
                    System.out.println("Medical Records");
                    System.out.println("Please select an option:");
                    System.out.println("1. Add a Record");
                    System.out.print("Enter your choice: ");

                    if (sc.hasNextInt()) {
                        choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {
                            case 1: {
                                System.out.println("Adding a record");

                                MedicalRecords medicalRecords = new MedicalRecords();

                                // Input of creating a medical record.
                                medicalRecords.setPatientId(getInputInt(sc, "Enter Patient ID: "));
                                medicalRecords.setStudentNumber(getInputString(sc, "Enter Student Number: "));
                                medicalRecords.setFirstName(getInputString(sc, "Enter First Name: "));
                                medicalRecords.setMiddleName(getInputString(sc, "Enter Middle Name: "));
                                medicalRecords.setLastName(getInputString(sc, "Enter Last Name: "));
                                medicalRecords.setSymptoms(getInputString(sc, "Enter Symptoms: "));
                                medicalRecords.setRemarks(getInputString(sc, "Enter Added Remarks: "));
                                medicalRecords.setTemperature(getInputDouble(sc, "Enter Temperature Readings: "));
                                medicalRecords.setVisitDate(getInputDate(sc, "Enter Visit Date (YYYY-MM-DD): "));
                                medicalRecords.setTimeIn(getInputTime(sc, "Enter Time In (HH:mm): "));
                                medicalRecords.setTimeOut(getInputTime(sc, "Enter Time Out (HH:mm): "));
                                medicalRecords.setMedicationAdministered(getInputString(sc, "Enter Medications Administered: "));
                                medicalRecords.setNurseInCharge(getInputString(sc, "Enter Nurse In Charge: "));

                                MedicalRecordsFacadeImpl impl = new MedicalRecordsFacadeImpl();
                                boolean recordExists = impl.patient(medicalRecords);

                                // If records exist this logic will alert the Nurse.
                                if (recordExists) {
                                    System.out.println("This record already exists.");
                                    if (getSaveOrCancel(sc, "Do you want to update the existing record?")) {
                                        boolean updateResult = impl.patient(medicalRecords);
                                        System.out.println(updateResult ? "Record updated successfully." : "Failed to update record.");
                                    } else {
                                        System.out.println("Record update cancelled.");
                                    }
                                } else {
                                    if (getSaveOrCancel(sc, "Do you want to save this record?")) {
                                        boolean saveResult = impl.patient(medicalRecords);
                                        System.out.println(saveResult ? "Record saved successfully." : "Failed to save record.");
                                    } else {
                                        System.out.println("Record creation cancelled.");
                                    }
                                }
                                break;
                            }
                            default:
                                System.out.println("Invalid choice for adding a record.");
                        }
                    } else {
                        System.out.println("Invalid choice format.");
                    }
                    break;

                case 2:
                    System.out.println("Returning to the previous screen.");
                    break;

                default:
                    System.out.println("Exiting the application.");
                    break;
            }
        } else {
            System.out.println("Invalid input format.");
        }

        sc.close();
    }


    // If conditions are not meet while using the Medical record program.
    private static int getInputInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } else {
                System.out.println("Invalid input format. Please enter an integer.");
                sc.next(); // Clear invalid input
            }
        }
    }

    //Displays when the input of digit is not valid.
    private static double getInputDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                System.out.println("Invalid input format. Please enter a number.");
                sc.next(); // Clear invalid input
            }
        }
    }

    //Displays the String input of the variables.
    private static String getInputString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    //Displays the Date input of the variables.
    private static LocalDate getInputDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = sc.nextLine();
            try {
                return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    //Displays the LocalTime input of the variables.
    private static LocalTime getInputTime(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String timeStr = sc.nextLine();
            try {
                return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:mm.");
            }
        }
    }

    //Displays the Save or Cancle save of the Data created.
    private static boolean getSaveOrCancel(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String choice = sc.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                return true;
            } else if (choice.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            }
        }
    }
}