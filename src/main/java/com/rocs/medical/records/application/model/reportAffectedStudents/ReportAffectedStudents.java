package com.rocs.medical.records.application.model.reportAffectedStudents;

public class ReportAffectedStudents {
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String gradeLevel;
    private String section;
    private int age;

    public ReportAffectedStudents(String studentNumber, String firstName, String lastName, String gradeLevel, String section, int age) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gradeLevel = gradeLevel;
        this.section = section;
        this.age = age;
    }

    public ReportAffectedStudents() {

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ReportAffectedStudents{" +
                "studentNumber='" + studentNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gradeLevel='" + gradeLevel + '\'' +
                ", section='" + section + '\'' +
                ", age=" + age +
                '}';
    }
}
