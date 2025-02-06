package com.rocs.frequent.visit.function.model.report;

import java.sql.Timestamp;

public class Patients {
    private int id;
    private String student_number;
    private String last_name;
    private String gradeLevel;
    private String symptoms;
    private Timestamp visitDate;
    public Patients(int id, String student_number, String last_name, String gradeLevel, String symptoms, Timestamp visitDate) {
        this.id = id;
        this.student_number = student_number;
        this.last_name = last_name;
        this.gradeLevel = gradeLevel;
        this.symptoms = symptoms;
        this.visitDate = visitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Timestamp getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }
}
