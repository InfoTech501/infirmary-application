package com.rocs.medical.records.application;

import com.rocs.medical.records.application.app.facade.MedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.app.facade.MedicalRecords.impl.MedicalRecordsFacadeImpl;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MedicalRecordsApplication {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        MedicalRecordsFacade records = new MedicalRecordsFacadeImpl();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Medical Records System");
            System.out.println("1. Add Student Record");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        handleStudentRecord(records, input);
                        break;
                    case 2:
                        System.out.println("Goodbye!");
                        input.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }

    private static void handleStudentRecord(MedicalRecordsFacade records, Scanner input) {
        System.out.println("Add Student Record");

        MedicalRecords record = new MedicalRecords();

        record.setStudentId(getInt(input, "Student ID: "));
        record.setAilmentId(getInt(input, "Ailment ID: "));
        record.setMedHistoryId(getInt(input, "Medical History ID: "));
        record.setNurseInChargeId(getInt(input, "Nurse In Charge ID: "));
        record.setSymptoms(getString(input, "Symptoms: "));
        record.setTemperatureReadings(getString(input, "Temperature Readings: "));
        record.setVisitDate(getDateTime(input, "Visit Date and Time (YYYY-MM-DD HH:mm): "));
        record.setTreatment(getString(input, "Treatment: "));

        boolean exists = records.patient(record);

        if (exists) {
            if (confirmAction(input, "Record exists. Update?")) {
                boolean updated = records.patient(record);
                System.out.println(updated ? "Record updated." : "Update failed.");
            }
        } else {
            if (confirmAction(input, "Save record?")) {
                boolean saved = records.patient(record);
                System.out.println(saved ? "Record saved." : "Save failed.");
            }
        }
    }

    private static int getInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Enter a number.");
                input.next();
            }
        }
    }

    private static String getString(Scanner input, String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    private static LocalDateTime getDateTime(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateTimeStr = input.nextLine();
            try {
                return LocalDateTime.parse(dateTimeStr, FORMAT);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date/time format. Use YYYY-MM-DD HH:mm.");
            }
        }
    }

    private static boolean confirmAction(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String choice = input.nextLine().toLowerCase();
            if (choice.equals("yes") || choice.equals("no")) {
                return choice.equals("yes");
            } else {
                System.out.println("Invalid choice. Enter 'yes' or 'no'.");
            }
        }
    }
}