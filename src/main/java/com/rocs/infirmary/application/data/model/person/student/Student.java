package com.rocs.infirmary.application.data.model.person.student;

import com.rocs.infirmary.application.data.model.person.Person;

import java.util.Date;

/**
 * Student model class representing student information.
 */
public class Student extends Person {

    private int studentSectionId;
    private Long studentId;
    private Long studentGuardianId;
    private String lrn;
    private Long id;
    private String gradeLevel;

    /**
     * This gets the grade level.
     * @return the Grade Level.
     */
    public String getGradeLevel() {
        return gradeLevel;
    }
    /**
     * This sets the grade level.
     * @param gradeLevel is the Grade Level to be set.
     */
    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * This gets the student id.
     * @return the Student id.
     */
    public Long getStudentId() {
        return studentId;
    }
    /**
     * This sets the student id.
     * @param studentId is the Student id to be set.
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * This gets the lrn.
     * @return the Lrn.
     */
    public String getLrn() {
        return lrn;
    }
    /**
     * This sets the lrn.
     * @param lrn is the Lrn to be set.
     */
    public void setLrn(String lrn) {
        this.lrn = lrn;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
