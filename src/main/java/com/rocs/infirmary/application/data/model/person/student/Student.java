package com.rocs.infirmary.application.data.model.person.student;

import com.rocs.infirmary.application.data.model.person.Person;

public class Student extends Person {

    private int studentSectionId;
    private Long studentId;
    private Long studentGuardianId;
    private String lrn;
    private Long id;
    private String gradeLevel;
    private String studentAdviser;

    public String getStudentAdviser() {
        return studentAdviser;
    }
    public void setStudentAdviser(String studentAdviser) {
        this.studentAdviser = studentAdviser;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }
    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getLrn() { return lrn; }
    public void setLrn(String lrn) { this.lrn = lrn; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
