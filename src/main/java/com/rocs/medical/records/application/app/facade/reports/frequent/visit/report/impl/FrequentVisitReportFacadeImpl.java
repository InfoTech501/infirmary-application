package com.rocs.medical.records.application.app.facade.reports.frequent.visit.report.impl;

import com.rocs.medical.records.application.app.facade.reports.frequent.visit.report.FrequentVisitReportFacade;
import com.rocs.medical.records.application.data.dao.reports.frequent.visit.report.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.dao.reports.frequent.visit.report.impl.FrequentVisitReportDaoImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.util.Date;
import java.util.List;

public class FrequentVisitReportFacadeImpl implements FrequentVisitReportFacade {

    /** This is the data access object for the FrequentVisitReport. */
    private FrequentVisitReportDao frequentVisitReportDao = new FrequentVisitReportDaoImpl();

    /**
     * * This generates the report of Frequent visit within the given report period.
     * * @param startDate -  The start of the report date period.
     * * @param endDate - The end of the report date period.
     * * @param gradeLevel - The grade level of the students with frequent visit.
     * * @return - Return the list of frequent visit report.
     * */
    @Override
    public List<FrequentVisitReport> generateFrequentVisitReport(Date startDate, Date endDate, String gradeLevel) {
        List<FrequentVisitReport> frequentVisitReportList = this.frequentVisitReportDao.getFrequentVisitReports(gradeLevel, startDate, endDate);
        return frequentVisitReportList;
    }


}
