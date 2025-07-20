package com.rocs.infirmary.application.data.model.person.student;

import java.util.Date;

public class Patient extends Student {

    private Long patientId;
    private Long studentId;
    private Long nurseInChargeId;
    private Long ailmentId;
    private Long medicineId;
    private Long medicalRecordId;
    private String symptoms;
    private String bloodPressure;
    private String temperatureReadings;
    private String treatment;
    private String medicineName;
    private String chiefComplaint;
    private String ailmentName;
    private String nurseInCharge;
    private int pulseRate;
    private int respiratoryRate;
    private int dispensingOut;
    private Date visitDate;

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getNurseInChargeId() { return nurseInChargeId; }
    public void setNurseInChargeId(Long nurseInChargeId) { this.nurseInChargeId = nurseInChargeId; }

    public Long getAilmentId() { return ailmentId; }
    public void setAilmentId(Long ailmentId) { this.ailmentId = ailmentId; }

    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }

    public Long getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(Long medicalRecordId) { this.medicalRecordId = medicalRecordId; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public String getTemperatureReadings() { return temperatureReadings; }
    public void setTemperatureReadings(String temperatureReadings) { this.temperatureReadings = temperatureReadings; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public String getChiefComplaint() { return chiefComplaint; }
    public void setChiefComplaint(String chiefComplaint) { this.chiefComplaint = chiefComplaint; }

    public String getAilmentName() { return ailmentName; }
    public void setAilmentName(String ailmentName) { this.ailmentName = ailmentName; }

    public String getNurseInCharge() {  return nurseInCharge; }
    public void setNurseInCharge(String nurseInCharge) { this.nurseInCharge = nurseInCharge; }

    public int getPulseRate() { return pulseRate; }
    public void setPulseRate(int pulseRate) { this.pulseRate = pulseRate; }

    public int getRespiratoryRate() { return respiratoryRate; }
    public void setRespiratoryRate(int respiratoryRate) { this.respiratoryRate = respiratoryRate; }

    public int getDispensingOut() { return dispensingOut; }
    public void setDispensingOut(int dispensingOut) { this.dispensingOut = dispensingOut; }

    public Date getVisitDate() { return visitDate; }
    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }
}

