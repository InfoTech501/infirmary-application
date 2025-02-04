package main.com.rocs.infirimary.deskstop.application.model.patient;

import oracle.sql.DATE;

public class Patient {




    private int patientId;

    private String studentNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private String symptoms;

    private String addedRemarks;

    private String temperatureReadings;

    private String visitDate;

    private String timeIn;

    private String timeOut;

    private String medicationAdministered;

    private String nurseInCharge;


    public Patient(){

    }

    public Patient(
                       int patientId,
                       String studentNumber,
                       String firstName,
                       String middleName,
                       String lastName,
                       String symptoms,
                       String addedRemarks,
                       String temperatureReadings,
                       String visitDate,
                       String timeIn,
                       String timeOut,
                       String medicationAdministered,
                       String nurseInCharge) {



            this.patientId = patientId;
            this.studentNumber = studentNumber;
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.symptoms = symptoms;
            this.addedRemarks = addedRemarks;
            this.temperatureReadings = temperatureReadings;
            this.visitDate = visitDate;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.medicationAdministered = medicationAdministered;
            this.nurseInCharge = nurseInCharge;
    }

    //    id number(20,0) generated as identity
//    constraint PATIENT_NOT_NULL NOT NULL,
//    patient_id number(20,0)
//    constraint PATIENT_ID_NOT_NULL NOT NULL
//    constraint PATIENT_ID_UNIQUE unique,
//    student_number varchar2(20),
//    first_name varchar2(50 char),
//    middle_name varchar2(35 char),
//    last_name varchar2(35 char),
//    symptoms varchar2(255 char),
//    added_remarks varchar2(255 char),
//    temperature_readings varchar(10),
//    visit_date timestamp(6)d,
//    time_in timestamp(6),
//    time_out timestamp(6),
//    medications_administered varchar2(60 char),
//    nurse_in_charge varchar2(255 char),
//    constraint PATIENT_PK primary key(id));





    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
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

    public String getAddedRemarks() {
        return addedRemarks;
    }

    public void setAddedRemarks(String addedRemarks) {
        this.addedRemarks = addedRemarks;
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

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getMedicationAdministered() {
        return medicationAdministered;
    }

    public void setMedicationAdministered(String medicationAdministered) {
        this.medicationAdministered = medicationAdministered;
    }

    public String getNurseInCharge() {
        return nurseInCharge;
    }

    public void setNurseInCharge(String nurseInCharge) {
        this.nurseInCharge = nurseInCharge;
    }

}
