package com.rocs.infirmary.application.data.model.person.student;

import com.rocs.infirmary.application.data.model.person.Person;

import java.util.Date;

public class Student extends Person {

    private int studentSectionId;
    private Long studentId;
    private int studentGuardianId;
    private Long lrn;
    private String symptoms;
    private String temperatureReadings;
    private Date visitDate;
    private String treatment;
    private Long id;
    private Long ailmentId;
    private String ailmentName;
    private String medHistoryId;
    private Long nurseInChargeId;
    private Long setMedicalRecordId ;
    private Long getMedicaRecordStatus;
    private String gradeLevel;
    private String studentAdviser;
    private String nurseInCharge;
    private String bloodPressure;
    private int pulseRate;
    private int respiratoryRate;
    private String chiefComplaint;
    private String medicineName;
    private int dispensingOut;
    private Long medicineId;

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

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getLrn() { return lrn; }
    public void setLrn(Long lrn) { this.lrn = lrn; }

    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTemperatureReadings() {
        return temperatureReadings;
    }
    public void setTemperatureReadings(String temperatureReadings) { this.temperatureReadings = temperatureReadings; }

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

    public void setId(Long id) { this.id = id; }

    public Long getAilmentId() { return ailmentId; }
    public void setAilmentId(Long ailmentId) { this.ailmentId = ailmentId; }

    public String getAilmentName() { return ailmentName; }
    public void setAilmentName(String ailmentName) { this.ailmentName = ailmentName;}

    public void setMedHistoryId(String medHistoryId) {
        this.medHistoryId = medHistoryId;
    }

    public Long getNurseInChargeId() { return nurseInChargeId; }
    public void setNurseInChargeId(Long nurseInChargeId) {
        this.nurseInChargeId = nurseInChargeId;
    }

    public Long getSetMedicalRecordId() { return setMedicalRecordId; }
    public void setMedicalRecordId(Long medicalRecordIdId) { this.setMedicalRecordId = medicalRecordIdId; }

    public Long getMedicalRecordStatus() { return getMedicaRecordStatus; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public int getPulseRate() { return pulseRate; }
    public void setPulseRate(int pulseRate) { this.pulseRate = pulseRate; }

    public int getRespiratoryRate() { return respiratoryRate; }
    public void setRespiratoryRate(int respiratoryRate) { this.respiratoryRate = respiratoryRate; }

    public String getChiefComplaint() { return chiefComplaint; }
    public void setChiefComplaint(String chiefComplaint) { this.chiefComplaint = chiefComplaint; }

    public int getDispensingOut() { return dispensingOut; }
    public void setDispensingOut(int dispensingOut) { this.dispensingOut = dispensingOut; }

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
