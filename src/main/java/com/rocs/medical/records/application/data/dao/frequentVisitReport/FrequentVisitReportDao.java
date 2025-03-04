package com.rocs.medical.records.application.data.dao.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

/**
 * * This returns the generated report of FrequentVisitReport List with the given report period.
 * * @param gradeLevel - The grade level of the students with frequent visit.
 * * @param startDate -  The start of the report date period.
 * * @param endDate - The end of the report date period.
 * * @return list of FrequentVisitReport object like student id, firstName, lastName, grade level, symptoms, visit count, and visit date.
 * */
public interface FrequentVisitReportDao {
    List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate);
}
