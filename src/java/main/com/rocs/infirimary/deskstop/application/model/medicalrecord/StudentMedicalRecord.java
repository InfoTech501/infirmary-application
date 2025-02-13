package main.com.rocs.infirimary.deskstop.application.model.medicalrecord;

import main.com.rocs.infirimary.deskstop.application.model.studentdetails.StudentDetails;

public class StudentMedicalRecord {
     private int ID;

     private long LRN;

    private int personID;

    private int studentID;

    private int ailmentID;

    private int medHistoryID;

    private int nurseInChargeID;

    private String symptoms;

    private String temperatureReadings;

    private String visitDate;

    private String treatment;

    private StudentDetails studentDetails;

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public int getPersonID() {
        return personID;
    }
    public long getLRN() {
        return LRN;
    }

    public void setLRN(long LRN) {
        this.LRN = LRN;
    }


    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getAilmentID() {
        return ailmentID;
    }

    public void setAilmentID(int ailmentID) {
        this.ailmentID = ailmentID;
    }

    public int getMedHistoryID() {
        return medHistoryID;
    }

    public void setMedHistoryID(int medHistoryID) {
        this.medHistoryID = medHistoryID;
    }

    public int getNurseInChargeID() {
        return nurseInChargeID;
    }

    public void setNurseInChargeID(int nurseInChargeID) {
        this.nurseInChargeID = nurseInChargeID;
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

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public StudentMedicalRecord(){

    }


}
