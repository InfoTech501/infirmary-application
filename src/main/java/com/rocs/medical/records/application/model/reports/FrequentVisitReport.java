package com.rocs.medical.records.application.model.reports;

<<<<<<< HEAD
import com.rocs.medical.records.application.model.person.Person;

import java.util.Date;
import java.util.List;

public class FrequentVisitReport {
    private int studentId;
=======
import java.util.Date;

public class FrequentVisitReport {
    private int studentId;
    private String firstName;
    private String lastName;
>>>>>>> cec7215 (frequent visit update)
    private String gradeLevel;
    private String symptoms;
    private int visitCount;
    private Date visitDate;

<<<<<<< HEAD
    public FrequentVisitReport(int studentId, String gradeLevel, String symptoms, int visitCount, Date visitDate) {
        this.studentId = studentId;
=======
    public FrequentVisitReport(int studentId, String firstName, String lastName, String gradeLevel, String symptoms, int visitCount, Date visitDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
>>>>>>> cec7215 (frequent visit update)
        this.gradeLevel = gradeLevel;
        this.symptoms = symptoms;
        this.visitCount = visitCount;
        this.visitDate = visitDate;
    }

    public FrequentVisitReport() {

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

<<<<<<< HEAD
=======
    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName;}

>>>>>>> cec7215 (frequent visit update)
    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    @Override
    public String toString() {
        return "FrequentVisitReport{" +
                ", healthConcern='" + symptoms + '\'' +
                ", visitCount=" + visitCount +
                ", gradeLevel='" + gradeLevel + '\'' +
                '}';
    }
}


