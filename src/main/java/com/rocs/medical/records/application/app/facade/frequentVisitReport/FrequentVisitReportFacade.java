package com.rocs.medical.records.application.app.facade.frequentVisitReport;

import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

public interface FrequentVisitReportFacade {
    List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel);
}
