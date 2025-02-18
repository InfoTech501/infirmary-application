package com.rocs.medical.records.application.app.facade.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

public interface FrequentVisitReportFacade {
<<<<<<< HEAD
    List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel);
=======

    List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel);

    void handleFrequentVisit(List<FrequentVisitReport> reports, Date frequentVisitStartDate, Date frequentVisitEndDate, String frequentVisitGradeLevel);
>>>>>>> cec7215 (frequent visit update)
}
