package com.rocs.medical.records.application.model.studentmedicalreport;

public class StudentMedicalReport {
    public int studentId;
    public String firstName;
    public String middleName;
    public String lastName;
    public String symptoms;
    public String addedRemarks;
    public String temperatureReadings;
    public String visitDateTimeIn;
    public String visitDateTimeOut;
    public String medicationsAdministered;

    public StudentMedicalReport(int studentId, String firstName, String middleName, String lastName,
                                String symptoms, String addedRemarks, String temperatureReadings,
                                String visitDateTimeIn, String visitDateTimeOut, String medicationsAdministered) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.symptoms = symptoms;
        this.addedRemarks = addedRemarks;
        this.temperatureReadings = temperatureReadings;
        this.visitDateTimeIn = visitDateTimeIn;
        this.visitDateTimeOut = visitDateTimeOut;
        this.medicationsAdministered = medicationsAdministered;
    }

    public String getFullName() {
        return (firstName != null ? firstName : "") +
                (middleName != null && !middleName.isEmpty() ? " " + middleName : "") +
                (lastName != null && !lastName.isEmpty() ? " " + lastName : "").trim();
    }
}
