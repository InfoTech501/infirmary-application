package com.rocs.medical.records.application.model.MedicalRecords;

import java.time.LocalDateTime;

public class MedicalRecords {

    private int studentId;
    private int ailmentId;
    private int medHistoryId;
    private int nurseInChargeId;
    private String symptoms;
    private String temperatureReadings;
    private LocalDateTime visitDate;
    private String treatment;

    public MedicalRecords() {
    }

    public MedicalRecords(int studentId, int ailmentId, int medHistoryId, int nurseInChargeId, String symptoms, String temperatureReadings, LocalDateTime visitDate, String treatment) {
        this.studentId = studentId;
        this.ailmentId = ailmentId;
        this.medHistoryId = medHistoryId;
        this.nurseInChargeId = nurseInChargeId;
        this.symptoms = symptoms;
        this.temperatureReadings = temperatureReadings;
        this.visitDate = visitDate;
        this.treatment = treatment;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAilmentId() {
        return ailmentId;
    }

    public void setAilmentId(int ailmentId) {
        this.ailmentId = ailmentId;
    }

    public int getMedHistoryId() {
        return medHistoryId;
    }

    public void setMedHistoryId(int medHistoryId) {
        this.medHistoryId = medHistoryId;
    }

    public int getNurseInChargeId() {
        return nurseInChargeId;
    }

    public void setNurseInChargeId(int nurseInChargeId) {
        this.nurseInChargeId = nurseInChargeId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTemperatureReadings() {
        return temperatureReadings;
    }

    public void setTemperatureReadings(String temperatureReadings) {
        this.temperatureReadings = temperatureReadings;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

}