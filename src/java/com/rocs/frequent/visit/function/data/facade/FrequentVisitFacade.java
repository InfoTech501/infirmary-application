package com.rocs.frequent.visit.function.data.facade;

import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.Date;
import java.util.List;

public interface FrequentVisitFacade {
    List<Patients> getFrequentVisitorsReport(String gradeLevel, String startDate, String endDate);
}
