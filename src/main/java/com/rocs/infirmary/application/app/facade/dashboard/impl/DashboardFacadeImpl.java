package com.rocs.infirmary.application.app.facade.dashboard.impl;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardDao;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardDaoImpl;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;

import java.util.Date;
import java.util.List;

/**
 * The DashboardFacadeImpl class is an implementation of the DashboardFacade interface.
 * It provides methods for managing reports and notification.
 */
public class DashboardFacadeImpl implements DashboardFacade {

    /** The data access object for Dashboard. */
    private final DashboardDao dashboard = new DashboardDaoImpl();

    @Override
    public List<LowStockReport> findAllLowStockMedicine() {
        List<LowStockReport> lowStockItems = dashboard.getAllLowStockMedicine();
        return lowStockItems;
    }

    @Override
    public List<CommonAilmentsReport> generateCommonAilmentReport(Date startDate, Date endDate, String gradeLevel, String section) {
        return this.dashboard.getCommonAilmentReport(startDate, endDate, gradeLevel, section);
    }

    @Override
    public List<FrequentVisitReport> generateFrequentVisitReport(Date startDate, Date endDate, String gradeLevel) {
        List<FrequentVisitReport> frequentVisitReportList = this.dashboard.getFrequentVisitReports(gradeLevel, startDate, endDate);
        return frequentVisitReportList;
    }

    @Override
    public List<MedicationTrendReport> generateMedicationReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> medicationTrendReportList = dashboard.getMedicationTrendReport(startDate, endDate);
        return medicationTrendReportList;
    }
}
