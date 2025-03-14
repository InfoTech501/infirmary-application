package com.rocs.infirmary.application.data.model.report.ailment;

import com.rocs.infirmary.application.data.model.person.Person;
import com.rocs.infirmary.application.data.model.report.Report;

import java.util.List;

public class CommonAilmentsReport extends Report {
    private String ailment;
    private int occurrences;
    private List<Person> affectedPeople;


    public CommonAilmentsReport(String ailment, int occurrences, List<Person> affectedPeople, String gradeLevel, String strand) {
        this.ailment = ailment;
        this.occurrences = occurrences;
        this.affectedPeople = affectedPeople;
        setGradeLevel(gradeLevel);
        setStrand(strand);

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

    @Override
    public String toString() {
        return "CommonAilmentsReport{" +
                "ailment='" + ailment + '\'' +
                ", occurrences=" + occurrences +
                ", affectedPeople=" + affectedPeople +
                ", gradeLevel='" + getGradeLevel() + '\'' +
                ", strand='" + getStrand() + '\'' +
                '}';
    }
}
