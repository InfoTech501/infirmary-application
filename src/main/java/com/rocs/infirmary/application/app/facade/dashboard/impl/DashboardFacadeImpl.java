package com.rocs.infirmary.application.app.facade.dashboard.impl;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsImpl;
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
    private final DashboardReports dashboard = new DashboardReportsImpl();

    @Override
    public List<LowStockReport> getAllLowStockMedicine() {
        List<LowStockReport> lowStockItems = dashboard.findAllLowStockMedicine();
        return lowStockItems;
    }

    @Override
    public List<CommonAilmentsReport> generateCommonAilmentReport(Date startDate, Date endDate, String gradeLevel, String section) {
        return this.dashboard.findCommonAilmentReport(startDate, endDate, gradeLevel, section);
    }

    @Override
    public List<FrequentVisitReport> generateFrequentVisitReport(Date startDate, Date endDate, String gradeLevel) {
        List<FrequentVisitReport> frequentVisitReportList = this.dashboard.findFrequentVisit(gradeLevel, startDate, endDate);
        return frequentVisitReportList;
    }

    @Override
    public List<MedicationTrendReport> generateMedicationReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> medicationTrendReportList = dashboard.findMedicationTrend(startDate, endDate);
        return medicationTrendReportList;
    }
}
