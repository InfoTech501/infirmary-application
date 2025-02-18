package com.rocs.medical.records.application.app.facade.createMedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.createMedicalRecords.createMedicalRecordsFacade;
import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.createMedicalRecordsDao;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.impl.createMedicalRecordsDaoImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class createMedicalRecordsFacadeImpl implements createMedicalRecordsFacade {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Scanner input = new Scanner(System.in);
    private final createMedicalRecordsDao medicalRecordsDao = new createMedicalRecordsDaoImpl();


    @Override
    public boolean addMedicalRecord(MedicalRecords record) {
        return medicalRecordsDao.createMedicalRecord(record);
    }

    @Override
    public MedicalRecords getMedicalRecordFromUserInput() {
        System.out.println("Add Student Record");
        MedicalRecords record = new MedicalRecords();

        record.setFirstName(getString("First Name: "));
        record.setMiddleName(getString("Middle Name: "));
        record.setLastName(getString("Last Name: "));
        record.setSymptoms(getString("Symptoms: "));
        record.setVisitDateTime(getDateTime("Visit Date and Time (YYYY-MM-DD HH:mm): "));
        record.setTemperatureReadings(getDouble("Temperature Readings: "));
        record.setTreatment(getString("Treatment: "));
        record.setNurseInCharge(getString("Nurse In Charge: "));

        if (addMedicalRecord(record)) {
            System.out.println("Record created and saved successfully.");
        } else {
            System.out.println("Failed to create and save record.");
        }
        return record;
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
                System.out.println("Invalid date/time format. Use YYYY-MM-DD HH:mm.");
            }
        }
    }

    private double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Enter a number.");
                input.next();
            }
        }
    }
}