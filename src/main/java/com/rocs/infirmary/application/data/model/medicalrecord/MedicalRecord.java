package com.rocs.infirmary.application.data.model.medicalrecord;

import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;

/**
 * MedicalRecord model class representing medical record.
 */
public class MedicalRecord extends Student {

    private Student student;
    private Long medicineId;
    private Long medicalRecordId;
    private String symptoms;
    private String bloodPressure;
    private String temperatureReadings;
    private String treatment;
    private String medicineName;
    private String nurseInCharge;
    private int pulseRate;
    private int respiratoryRate;
    private int dispensingOut;
    private Date visitDate;

    /**
     * This gets the student.
     * @return the Student.
     */
    public Student getStudent() {
        return student;
    }
    /**
     * This sets the student.
     * @param student is the Student to be set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * This gets the medicine id.
     * @return the Medicine id.
     */
    public Long getMedicineId() {
        return medicineId;
    }
    /**
     * This sets the medicine id.
     * @param medicineId is the Medicine id to be set.
     */
    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    /**
     * This gets the medical record id.
     * @return the Medical Record id.
     */
    public Long getMedicalRecordId() {
        return medicalRecordId;
    }
    /**
     * This sets the medical record id.
     * @param medicalRecordId is the Medical Record id to be set.
     */
    public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    /**
     * This gets the symptoms.
     * @return the Symptoms.
     */
    public String getSymptoms() {
        return symptoms;
    }
    /**
     * This sets the symptoms.
     * @param symptoms is the Symptoms to be set.
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * This gets the blood pressure.
     * @return the Blood Pressure.
     */
    public String getBloodPressure() {
        return bloodPressure;
    }
    /**
     * This sets the blood pressure.
     * @param bloodPressure is the Blood Pressure to be set.
     */
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    /**
     * This gets the temperature readings.
     * @return the Temperature Readings.
     */
    public String getTemperatureReadings() {
        return temperatureReadings;
    }
    /**
     * This sets the temperature readings.
     * @param temperatureReadings is the Temperature Readings to be set.
     */
    public void setTemperatureReadings(String temperatureReadings) {
        this.temperatureReadings = temperatureReadings;
    }

    /**
     * This gets the treatment.
     * @return the Treatment.
     */
    public String getTreatment() {
        return treatment;
    }
    /**
     * This sets the treatment.
     * @param treatment is the Treatment to be set.
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * This gets the medicine name.
     * @return the Medicine Name.
     */
    public String getMedicineName() {
        return medicineName;
    }
    /**
     * This sets the medicine name.
     * @param medicineName is the Medicine Name to be set.
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * This gets the nurse in charge.
     * @return the Nurse In Charge.
     */
    public String getNurseInCharge() {
        return nurseInCharge;
    }
    /**
     * This sets the nurse in charge.
     * @param nurseInCharge is the Nurse In Charge to be set.
     */
    public void setNurseInCharge(String nurseInCharge) {
        this.nurseInCharge = nurseInCharge;
    }

    /**
     * This gets the pulse rate.
     * @return the Pulse Rate.
     */
    public int getPulseRate() {
        return pulseRate;
    }
    /**
     * This sets the pulse rate.
     * @param pulseRate is the Pulse Rate to be set.
     */
    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }

    /**
     * This gets the respiratory rate.
     * @return the Respiratory Rate.
     */
    public int getRespiratoryRate() {
        return respiratoryRate;
    }
    /**
     * This sets the respiratory rate.
     * @param respiratoryRate is the Respiratory Rate to be set.
     */
    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    /**
     * This gets the dispensing out.
     * @return the Dispensing Out.
     */
    public int getDispensingOut() {
        return dispensingOut;
    }
    /**
     * This sets the dispensing out.
     * @param dispensingOut is the Dispensing Out to be set.
     */
    public void setDispensingOut(int dispensingOut) {
        this.dispensingOut = dispensingOut;
    }

    /**
     * This gets the visit date.
     * @return the Visit Date.
     */
    public Date getVisitDate() {
        return visitDate;
    }
    /**
     * This sets the visit date.
     * @param visitDate is the Visit Date to be set.
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
}

