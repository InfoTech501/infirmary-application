package com.rocs.medical.records.application.data.dao.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

<<<<<<< HEAD
=======
/**
 * **/

>>>>>>> cec7215 (frequent visit update)
public interface FrequentVisitReportDao {
    List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate);
}
