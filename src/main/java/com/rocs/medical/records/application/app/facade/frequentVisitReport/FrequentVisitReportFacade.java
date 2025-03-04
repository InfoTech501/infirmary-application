package com.rocs.medical.records.application.app.facade.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

/**
 * * This is a FrequentVisit interface that manages the FrequentVisitReport object such as
 * * studentId, firstname, lastName, gradeLevel, symptoms, visitCount, and visitDate
 * * within the given report period.
 * */
public interface FrequentVisitReportFacade {
    /**
     * This generates report of the frequent visit of the student in the clinic within the given period of date and grade level
     * * @param startDate - The start date of the report period.
     * * @param endDate - The end date of the report period.
     * * @param gradeLevel - The grade level of the student.
     * * @return list of FrequentVisitReport object like studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
     * */
    List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel);
    /**
     * * This handleFrequentVisit display the report of frequent visit within the given report period.
     * * @param reports - Checks if there's a report within the given report period.
     * * @param startDate - The start date of the report period.
     * * @param endDate - The end date of the report period.
     * * @param gradeLevel - the grade level of the student.
     * * @return list of FrequentVisitReport object like studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
     * */
    void handleFrequentVisit(List<FrequentVisitReport> reports, Date startDate, Date endDate, String gradeLevel);
}