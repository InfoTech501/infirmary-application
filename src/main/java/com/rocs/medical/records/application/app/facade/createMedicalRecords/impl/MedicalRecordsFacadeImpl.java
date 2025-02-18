package com.rocs.medical.records.application.app.facade.createMedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.createMedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MedicalRecordsFacadeImpl implements MedicalRecordsFacade {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Scanner input = new Scanner(System.in);

    @Override
    public boolean saveOrcancelMedicalRecord(MedicalRecords record) {
        System.out.println("Saving/Cancel record: " + record);
        return true;
    }

    @Override
    public MedicalRecords getMedicalRecordFromUserInput() {
        System.out.println("Add Student Record");
        MedicalRecords record = new MedicalRecords();

        record.setStudentId(getInt("Student ID: "));
        record.setAilmentId(getInt("Ailment ID: "));
        record.setMedHistoryId(getInt("Medical History ID: "));
        record.setNurseInChargeId(getInt("Nurse In Charge ID: "));
        record.setSymptoms(getString("Symptoms: "));
        record.setTemperatureReadings(getString("Temperature Readings: "));
        record.setVisitDate(getDateTime("Visit Date and Time (YYYY-MM-DD HH:mm): "));
        record.setTreatment(getString("Treatment: "));

        boolean exists = saveOrcancelMedicalRecord(record);

        if (exists) {
            if (confirmAction("Record exists. Update?")) {
                saveOrcancelMedicalRecord(record);
                System.out.println("Record updated.");
            }
        } else {
            if (confirmAction("Save record?")) {
                saveOrcancelMedicalRecord(record);
                System.out.println("Record saved.");
            }
        }
        return record;
    }

    private int getInt(String prompt) {
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

    private String getString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    private LocalDateTime getDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateTimeStr = input.nextLine();
            try {
                return LocalDateTime.parse(dateTimeStr, FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date/time format. Use yyyy-MM-dd HH:mm.");
            }
        }
    }

    private boolean confirmAction(String prompt) {
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
