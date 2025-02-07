package com.rocs.frequent.visit.function.controller;

import com.rocs.frequent.visit.function.data.facade.FrequentVisitFacade;
import com.rocs.frequent.visit.function.data.facade.impl.FrequentVisitFacadeImpl;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.List;

public class FrequentVisitController {
    private final FrequentVisitFacade frequentVisitFacade;

    public FrequentVisitController() {
        this.frequentVisitFacade = new FrequentVisitFacadeImpl();
    }

    public List<Patients> getFrequentVisitorsReport(String gradeLevel, String startDate, String endDate) {
        return frequentVisitFacade.getFrequentVisitorsReport(gradeLevel, startDate, endDate);
    }
}