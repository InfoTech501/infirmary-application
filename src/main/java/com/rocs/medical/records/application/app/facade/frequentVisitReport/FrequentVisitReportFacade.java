package com.rocs.medical.records.application.app.facade.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

/**
 * This is an interface that manages the report of the frequent visit of the student in the clinic within the given period of date and grade level
 * * @param startDate
 * *@param endDate
 * *@return list of FrequentVisitReport like student id, student first and last name,grade level, symptoms,visit count, visit date.
 * */

public interface FrequentVisitReportFacade {
    List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel);

    void handleFrequentVisit(List<FrequentVisitReport> reports, Date startDate, Date endDate, String gradeLevel);
}