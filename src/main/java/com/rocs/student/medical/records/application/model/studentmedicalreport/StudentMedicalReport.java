package com.rocs.student.medical.records.application.model.studentmedicalreport;

/**
 * Represents a student and their medical records.
 */
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

    /**
     * Constructs a Student object with the given details.
     *
     * @param studentId             The unique student ID.
     * @param firstName             The first name of the student.
     * @param middleName            The middle name of the student (nullable).
     * @param lastName              The last name of the student.
     * @param symptoms              The symptoms reported by the student.
     * @param addedRemarks          Additional remarks recorded by the nurse.
     * @param temperatureReadings   The student's temperature readings.
     * @param visitDateTimeIn       The timestamp of the student's check-in.
     * @param visitDateTimeOut      The timestamp of the student's check-out.
     * @param medicationsAdministered The medications given to the student.
     */
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
    /**
     * Returns the full name of the student.
     *
     * @return The concatenated full name (First, Middle, Last).
     */

    public String getFullName() {
        return (firstName != null ? firstName : "") +
                (middleName != null && !middleName.isEmpty() ? " " + middleName : "") +
                (lastName != null && !lastName.isEmpty() ? " " + lastName : "").trim();
    }
}
