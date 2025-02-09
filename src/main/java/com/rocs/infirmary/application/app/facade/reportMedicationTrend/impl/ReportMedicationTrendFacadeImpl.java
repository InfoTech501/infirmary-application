package com.rocs.infirmary.application.app.facade.reportMedicationTrend.impl;

import com.rocs.infirmary.application.app.facade.reportMedicationTrend.ReportMedicationTrendFacade;
import com.rocs.infirmary.application.data.dao.reportMedicationTrend.impl.ReportMedicationTrendDaoImpl;
import com.rocs.infirmary.application.data.dao.reportMedicationTrend.ReportMedicationTrendDao;
import com.rocs.infirmary.application.model.report.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public class ReportMedicationTrendFacadeImpl implements ReportMedicationTrendFacade {
    ReportMedicationTrendDao medTrendDao = new ReportMedicationTrendDaoImpl();

    @Override
    public List<MedicationTrendReport> generateReport(Date startDate, Date endDate) {
        return this.medTrendDao.getGenerateReport(startDate, endDate);
    }
}
