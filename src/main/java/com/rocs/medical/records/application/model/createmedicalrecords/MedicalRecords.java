package com.rocs.medical.records.application.model.createmedicalrecords;

import java.time.LocalDateTime;

public class MedicalRecords {

    private String firstName;
    private String middleName;
    private String lastName;
    private String symptoms;
    private LocalDateTime visitDateTime;
    private double temperatureReadings;
    private String treatment;
    private String nurseInCharge;

    public MedicalRecords() {
    }

    public MedicalRecords(String firstName, String middleName, String lastName, String symptoms, LocalDateTime visitDateTime, double temperatureReadings, String treatment, String nurseInCharge) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.symptoms = symptoms;
        this.visitDateTime = visitDateTime;
        this.temperatureReadings = temperatureReadings;
        this.treatment = treatment;
        this.nurseInCharge = nurseInCharge;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(LocalDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public double getTemperatureReadings() {
        return temperatureReadings;
    }

    public void setTemperatureReadings(double temperatureReadings) {
        this.temperatureReadings = temperatureReadings;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNurseInCharge() {
        return nurseInCharge;
    }

    public void setNurseInCharge(String nurseInCharge) {
        this.nurseInCharge = nurseInCharge;
    }

    @Override
    public String toString() {
        return "MedicalRecords{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", visitDateTime=" + visitDateTime +
                ", temperatureReadings=" + temperatureReadings +
                ", treatment='" + treatment + '\'' +
                ", nurseInCharge='" + nurseInCharge + '\'' +
                '}';
    }
}