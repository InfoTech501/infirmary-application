package com.rocs.medical.records.application.model.reports;

import com.rocs.medical.records.application.model.person.Person;

import java.util.Date;
import java.util.List;

public class FrequentVisitReport {
    private int studentId;
    private String gradeLevel;
    private String symptoms;
    private int visitCount;
    private Date visitDate;

    public FrequentVisitReport(int studentId, String gradeLevel, String symptoms, int visitCount, Date visitDate) {
        this.studentId = studentId;
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


