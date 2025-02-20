package com.rocs.medical.records.application.model.reports;

import com.rocs.medical.records.application.model.person.Person;

import java.util.Date;
import java.util.List;

public class CommonAilmentsReport {
    private String ailment;
    private int occurrences;
    private List<Person> affectedPeople;
    private String gradeLevel;
    private String strand;

    public CommonAilmentsReport(String ailment, int occurrences, List<Person> affectedPeople, String gradeLevel, String strand) {
        this.ailment = ailment;
        this.occurrences = occurrences;
        this.affectedPeople = affectedPeople;
        this.gradeLevel = gradeLevel;
        this.strand = strand;
    }

    public CommonAilmentsReport() {

    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public List<Person> getAffectedPeople() {
        return affectedPeople;
    }

    public void setAffectedPeople(List<Person> affectedPeople) {
        this.affectedPeople = affectedPeople;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    @Override
    public String toString() {
        return "CommonAilmentsReport{" +
                "ailment='" + ailment + '\'' +
                ", occurrences=" + occurrences +
                ", affectedPeople=" + affectedPeople +
                ", gradeLevel='" + gradeLevel + '\'' +
                ", strand='" + strand + '\'' +
                '}';
    }
}
