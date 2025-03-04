package com.rocs.medical.records.application.data.dao.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

/**
 * * This is an interface that manages the generated report of FrequentVisitReport object that maintains
 * * studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
 * */
public interface FrequentVisitReportDao {
    /**
     * * This returns the generated report of FrequentVisitReport List with the given report period.
     * * @param startDate -  The start of the report date period.
     * * @param endDate - The end of the report date period.
     * * @param gradeLevel - The grade level of the students with frequent visit.
     * * @return list of FrequentVisitReport object like studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
     * */
    List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate);
}
