package com.rocs.frequent.visit.function.model.report;

import java.sql.Timestamp;

public class Patients {
    private int id;
    private String last_name;
    private String gradeLevel;
    private int visitCount;

    // This is the Constructors
    public Patients(int id, String last_name, String gradeLevel) {
        this.id = id;
        this.last_name = last_name;
        this.gradeLevel = gradeLevel;
        this.visitCount = visitCount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVisitCount() { return visitCount; }

    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }

}

