package com.rocs.infirmary.application.data.model.person.student;

import com.rocs.infirmary.application.data.model.person.Person;

import java.util.Date;

public class Student extends Person {

    private int studentSectionId;
    private int studentId;
    private int studentGuardianId;
    private String lrn;
    private String symptoms;
    private String temperatureReadings;
    private String bloodPressure;
    private Long pulseRate;
    private Long respiratoryRate;
    private Date visitDate;
    private String treatment;
    private Long id;
    private int ailmentId;
    private String medHistoryId;
    private Long nurseInChargeId;
    private Long setMedicalRecordId ;
    private int MedicalRecordStatus;
    private String gradeLevel;
    private String studentAdviser;
    private String nurseInCharge;
    private String nurseInChargeFirstName;
    private String nurseInChargeLastName;

    public String getNurseInCharge() {
        return nurseInCharge;
    }

    public void setNurseInCharge(String nurseInCharge) {
        this.nurseInCharge = nurseInCharge;
    }

    public String getStudentAdviser() {
        return studentAdviser;
    }

    public void setStudentAdviser(String studentAdviser) {
        this.studentAdviser = studentAdviser;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setLrn(String lrn) {
        this.lrn = lrn;
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

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getLrn() {
        return lrn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAilmentId(int ailmentId) {
        this.ailmentId = ailmentId;
    }

    public void setMedHistoryId(String medHistoryId) {
        this.medHistoryId = medHistoryId;
    }

    public void setNurseInChargeId(Long nurseInChargeId) {
        this.nurseInChargeId = nurseInChargeId;
    }

    public void setMedicalRecordId(Long medicalRecordIdId) {
        this.setMedicalRecordId = medicalRecordIdId;
    }

    public int getMedicalRecordStatus() {
        return MedicalRecordStatus;
    }

    public void setMedicalRecordStatus(int medicalRecordStatus) {
        MedicalRecordStatus = medicalRecordStatus;
    }

    public String getNurseInChargeLastName() {
        return nurseInChargeLastName;
    }

    public void setNurseInChargeLastName(String nurseInChargeLastName) {
        this.nurseInChargeLastName = nurseInChargeLastName;
    }

    public String getNurseInChargeFirstName() {
        return nurseInChargeFirstName;
    }

    public void setNurseInChargeFirstName(String nurseInChargeFirstName) {
        this.nurseInChargeFirstName = nurseInChargeFirstName;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Long getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(Long pulseRate) {
        this.pulseRate = pulseRate;
    }

    public Long getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Long respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }
}
