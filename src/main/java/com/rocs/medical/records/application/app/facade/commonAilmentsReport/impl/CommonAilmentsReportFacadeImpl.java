package com.rocs.medical.records.application.app.facade.commonAilmentsReport.impl;

import com.rocs.medical.records.application.data.dao.commonAilmentsReport.CommonAilmentsReportDAO;
import com.rocs.medical.records.application.data.dao.commonAilmentsReport.impl.CommonAilmentsReportDaoImpl;
import com.rocs.medical.records.application.app.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

public class CommonAilmentsReportFacadeImpl implements CommonAilmentsReportFacade {
    private final CommonAilmentsReportDAO ailmentsReportDAO = new CommonAilmentsReportDaoImpl();

    @Override
    public List<CommonAilmentsReport> generateReport(Date startDate, Date endDate, String gradeLevel, String section) {
        return this.ailmentsReportDAO.getGeneratedReport(startDate, endDate, gradeLevel, section);
    }
}