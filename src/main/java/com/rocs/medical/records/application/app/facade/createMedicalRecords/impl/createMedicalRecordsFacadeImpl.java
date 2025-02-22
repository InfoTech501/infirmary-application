package com.rocs.medical.records.application.app.facade.createMedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.createMedicalRecords.createMedicalRecordsFacade;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.createMedicalRecordsDao;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.impl.createMedicalRecordsDaoImpl;
import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

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
    public MedicalRecords addStudentMedicalRecord() {
        System.out.println("Add Student Medical Record");
        MedicalRecords record = new MedicalRecords();

        record.setFirstName(getString("First Name: "));
        record.setMiddleName(getString("Middle Name: "));
        record.setLastName(getString("Last Name: "));
        record.setSymptoms(getString("Symptoms: "));
        record.setVisitDateTime(getDateTime("Visit Date and Time (YYYY-MM-DD HH:mm): "));
        record.setTemperatureReadings(getDouble("Temperature Readings: "));
        record.setTreatment(getString("Treatment: "));

        Integer nurseId = getInteger("Nurse In Charge ID: ");
        record.setNurseInChargeId(nurseId);

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

    private Integer getInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Enter an integer.");
                input.next();
            }
        }
    }

    public void findMedicalInformationByLRN(long LRN) {
        MedicalRecords record = medicalRecordsDao.findMedicalInformationByLRN(LRN);

        if (record == null) {
            System.out.println("No Student Found");
        } else {
            System.out.println("Firstname             : " + record.getFirstName());
            System.out.println("Middlename            : " + record.getMiddleName());
            System.out.println("Lastname              : " + record.getLastName());
            System.out.println("Symptoms              : " + record.getSymptoms());
            System.out.println("Temperature Readings  : " + record.getTemperatureReadings());
            System.out.println("Visit Date            : " + record.getVisitDateTime());
            System.out.println("Treatment             : " + record.getTreatment());
            System.out.println("Nurse In Charge ID  : " + record.getNurseInChargeId());
        }
    }
}