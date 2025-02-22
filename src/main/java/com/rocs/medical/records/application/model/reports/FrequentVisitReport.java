package com.rocs.medical.records.application.model.reports;

import java.util.Date;

public class FrequentVisitReport {
    private int studentId;
    private String firstName;
    private String lastName;
    private String gradeLevel;
    private String symptoms;
    private int visitCount;
    private Date visitDate;

    public FrequentVisitReport(int studentId, String firstName, String lastName, String gradeLevel, String symptoms, int visitCount, Date visitDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gradeLevel = gradeLevel;
        this.symptoms = symptoms;
        this.visitCount = visitCount;
        this.visitDate = visitDate;
    }

    public FrequentVisitReport() {

    }

    public int getStudentId() {return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName;}

    public String getGradeLevel() { return gradeLevel; }

    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }

    public String getSymptoms() { return symptoms; }

    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public int getVisitCount() { return visitCount; }

    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }

    public Date getVisitDate() { return visitDate; }

    public void setVisitDate(Date visitDate) { this.visitDate = visitDate; }

    @Override
    public String toString() {
        return "FrequentVisitReport{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gradeLevel='" + gradeLevel + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", visitCount=" + visitCount +
                ", visitDate=" + visitDate +
                '}';
    }
}
