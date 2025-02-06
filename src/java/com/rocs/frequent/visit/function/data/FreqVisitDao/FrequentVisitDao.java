package com.rocs.frequent.visit.function.data.FreqVisitDao;

import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.List;

public interface FrequentVisitDao {
    List<Patients> getFrequentVisitors(String gradeLevel, String startDate, String endDate);
}


