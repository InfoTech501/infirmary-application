package com.rocs.medical.records.application.data.dao.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

public interface FrequentVisitReportDao {

    /**
     * * This returns the generated report of FrequentVisitReport List with the given report period.
     * * @param startDate -  The start of the report date period.
     * * @param endDate - The end of the report date period.
     * * @param gradeLevel - The grade level of the students with frequent visit.
     * * @return list of FrequentVisitReport object like student id, firstName, lastName, grade level, symptoms, visit count, and visit date.
     * */

  List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate);
}
