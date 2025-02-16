package com.rocs.medical.records.application.app.facade.frequentVisitReport.impl;

import com.rocs.medical.records.application.app.facade.frequentVisitReport.FrequentVisitReportFacade;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.impl.FrequentVisitReportDaoImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

public class FrequentVisitReportFacadeImpl implements FrequentVisitReportFacade {
    private FrequentVisitReportDao frequentVisitDao = new FrequentVisitReportDaoImpl();

    @Override
    public List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel) {
        return frequentVisitDao.getFrequentVisitReports(gradeLevel, startDate, endDate);
    }
}

