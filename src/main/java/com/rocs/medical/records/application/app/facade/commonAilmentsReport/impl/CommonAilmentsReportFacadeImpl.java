package com.rocs.medical.records.application.app.facade.commonAilmentsReport.impl;

import com.rocs.medical.records.application.data.dao.commonAilmentsReport.CommonAilmentsReportDAO;
import com.rocs.medical.records.application.data.dao.commonAilmentsReport.impl.CommonAilmentsReportDaoImpl;
import com.rocs.medical.records.application.app.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

/**
 * A class that implements the CommonAilmentsReportFacade
 * managing the CommonAilmentsReport object maintenance such as
 * common ailments, occurrences, affected people, grade level, and strand
 * within the given report period.
 * */
public class CommonAilmentsReportFacadeImpl implements CommonAilmentsReportFacade {

    /** The data access object for CommonAilmentsReport. */
    private final CommonAilmentsReportDAO ailmentsReportDAO = new CommonAilmentsReportDaoImpl();

    @Override
    public List<CommonAilmentsReport> generateReport(Date startDate, Date endDate, String gradeLevel, String section) {
        return this.ailmentsReportDAO.getGeneratedReport(startDate, endDate, gradeLevel, section);
    }
}