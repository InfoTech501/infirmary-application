package com.rocs.medical.records.application.app.facade.reports.medication.trend.report.impl;

import com.rocs.medical.records.application.app.facade.reports.medication.trend.report.ReportMedicationTrendFacade;
import com.rocs.medical.records.application.data.dao.reports.medication.trend.report.impl.ReportMedicationTrendDaoImpl;
import com.rocs.medical.records.application.data.dao.reports.medication.trend.report.ReportMedicationTrendDao;
import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.util.Date;
import java.util.List;

/**
 * A class that implements the ReportMedicationTrendFacade
 * managing the ReportMedicationTrend object maintenance such as
 * medication usage, medicine name, and medication stocks
 * within the given report period.
 * */
public class ReportMedicationTrendFacadeImpl implements ReportMedicationTrendFacade {

    /** The data access object for ReportMedicationTrend. */
    private final ReportMedicationTrendDao medTrendDao = new ReportMedicationTrendDaoImpl();

    @Override
    public List<MedicationTrendReport> generateMedicationReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> medicationTrendReportList = medTrendDao.getMedicationTrendReport(startDate, endDate);
        return medicationTrendReportList;
    }
}
