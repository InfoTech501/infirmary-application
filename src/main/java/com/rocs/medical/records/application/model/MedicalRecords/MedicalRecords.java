package com.rocs.medical.records.application.model.MedicalRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class MedicalRecords {

    private String studentNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String symptoms;
    private String remarks;
    private double temperature;
    private LocalDate visitDate;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private String medicationAdministered;
    private String nurseInCharge;
    private int patientId;

    public MedicalRecords() {
    }

    public MedicalRecords(int patientId, String studentNumber, String firstName, String middleName, String lastName, String symptoms, String remarks, double temperature, LocalDate visitDate, LocalTime timeIn, LocalTime timeOut, String medicationAdministered, String nurseInCharge) {
        this.patientId = patientId;
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.symptoms = symptoms;
        this.remarks = remarks;
        this.temperature = temperature;
        this.visitDate = visitDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.medicationAdministered = medicationAdministered;
        this.nurseInCharge = nurseInCharge;
    }


    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }

    public LocalTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }

    public String getMedicationAdministered() { return medicationAdministered; }
    public void setMedicationAdministered(String medicationAdministered) { this.medicationAdministered = medicationAdministered; }

    public String getNurseInCharge() { return nurseInCharge; }
    public void setNurseInCharge(String nurseInCharge) { this.nurseInCharge = nurseInCharge; }


}
