package com.rocs.medical.records.application.app.facade.reportMedicationTrend.impl;

import com.rocs.medical.records.application.app.facade.reportMedicationTrend.ReportMedicationTrendFacade;
import com.rocs.medical.records.application.data.dao.reportMedicationTrend.impl.ReportMedicationTrendDaoImpl;
import com.rocs.medical.records.application.data.dao.reportMedicationTrend.ReportMedicationTrendDao;
import com.rocs.medical.records.application.model.report.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public class ReportMedicationTrendFacadeImpl implements ReportMedicationTrendFacade {
    private final ReportMedicationTrendDao medTrendDao = new ReportMedicationTrendDaoImpl();

    @Override
    public List<MedicationTrendReport> generateReport(Date startDate, Date endDate) {
        return this.medTrendDao.getGeneratedReport(startDate, endDate);
    }
}
