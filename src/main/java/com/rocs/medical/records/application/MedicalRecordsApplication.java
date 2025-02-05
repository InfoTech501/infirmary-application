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

        MedicalRecordsFacade itemFacade = new MedicalRecordsFacadeImpl();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Medical Records System");
        System.out.println("Please enter your selection:");
        System.out.println("1. Student Record Application");
        System.out.println("2. Return");
        System.out.println("Press other keys to exit.");
        System.out.print("Enter your choice: ");

        if (sc.hasNextInt()) {
            int input = sc.nextInt();
            sc.nextLine();

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

                                System.out.print("Enter Patient ID: ");
                                if(sc.hasNextInt()){
                                    medicalRecords.setPatientId(sc.nextInt());
                                }else{
                                    System.out.println("Invalid Patient ID format.");
                                    break; //Exit the add record process
                                }

                                System.out.print("Enter First Name: ");
                                medicalRecords.setFirstName(sc.nextLine());
                                System.out.print("Enter Middle Name: ");
                                medicalRecords.setMiddleName(sc.nextLine());
                                System.out.print("Enter Last Name: ");
                                medicalRecords.setLastName(sc.nextLine());
                                System.out.print("Enter Symptoms: ");
                                medicalRecords.setSymptoms(sc.nextLine());
                                System.out.print("Enter Added Remarks: ");
                                medicalRecords.setRemarks(sc.nextLine());

                                System.out.print("Enter Temperature Readings: ");
                                if(sc.hasNextDouble()){
                                    medicalRecords.setTemperature(sc.nextDouble());
                                    sc.nextLine();
                                }else{
                                    System.out.println("Invalid temperature format.");
                                    break;
                                }

                                System.out.print("Enter Visit Date (YYYY-MM-DD): ");
                                String visitDateStr = sc.nextLine();
                                try {
                                    LocalDate visitDate = LocalDate.parse(visitDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    medicalRecords.setVisitDate(visitDate);
                                } catch (Exception e) {
                                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                                    break;
                                }

                                System.out.print("Enter Time In (HH:mm): ");
                                String timeInStr = sc.nextLine();
                                try {
                                    LocalTime timeIn = LocalTime.parse(timeInStr, DateTimeFormatter.ofPattern("HH:mm"));
                                    medicalRecords.setTimeIn(timeIn);
                                } catch (Exception e) {
                                    System.out.println("Invalid time format. Please use HH:mm.");
                                    break;
                                }

                                System.out.print("Enter Time Out (HH:mm): ");
                                String timeOutStr = sc.nextLine();
                                try {
                                    LocalTime timeOut = LocalTime.parse(timeOutStr, DateTimeFormatter.ofPattern("HH:mm"));
                                    medicalRecords.setTimeOut(timeOut);
                                } catch (Exception e) {
                                    System.out.println("Invalid time format. Please use HH:mm.");
                                    break;
                                }


                                System.out.print("Enter Medications Administered: ");
                                medicalRecords.setMedicationAdministered(sc.nextLine());
                                System.out.print("Enter Nurse In Charge: ");
                                medicalRecords.setNurseInCharge(sc.nextLine());

                                boolean result = MedicalRecordsFacade.patient(medicalRecords);

                                if (result) {
                                    System.out.println("Record successfully added.");
                                } else {
                                    System.out.println("Record cannot be added.");
                                }

                                break;
                            }
                            default:
                                System.out.println("Invalid choice for adding a record.");
                        }
                    }else{
                        System.out.println("Invalid choice format.");
                    }
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
}