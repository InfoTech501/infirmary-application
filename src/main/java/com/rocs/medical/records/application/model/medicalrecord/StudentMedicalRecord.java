package com.rocs.medical.records.application.model.medicalrecord;

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


    private String firstName;

    private String middleName;

    private String lastName;

    private int age;

    private String gender;

    private String email;

    private String address;

    private String contactNumber;

    private String ailment;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
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



    public void setStudentID(int studentID) {
        this.studentID = studentID;
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
