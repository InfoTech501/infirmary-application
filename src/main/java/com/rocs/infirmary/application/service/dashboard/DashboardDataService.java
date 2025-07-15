package com.rocs.infirmary.application.service.dashboard;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;

import java.util.List;

/**
 * This class DashboardDataService gets the data to display on the Dashboard Page.
 * */
public class DashboardDataService {
    private final DashboardFacade dashboardFacade;
    /**
     * This gets the Dashboard Facade.
     * @return the dashboard facade.
     */
    public DashboardDataService(DashboardFacade dashboardFacade) {
        this.dashboardFacade = dashboardFacade;
    }
    /**
     * This gets the visit count to the generated frequent visit report from dashboardFacade.
     * @return the size of the generated visit report.
     */
    public int getVisitCount (DateRange dateRange, String gradeLevel) {
        List<FrequentVisitReport> reports = dashboardFacade.generateFrequentVisitReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel);
        return reports.size();
    }
    /**
     * This gets the total medication usage to the generated medication report from dashboardFacade.
     * @return the summation of all medication trend report.
     */
    public int getTotalMedicationUsage(DateRange dateRange) {
        List<MedicationTrendReport> reports = dashboardFacade.generateMedicationReport(
                dateRange.getStartDate(), dateRange.getEndDate());
        return reports.stream().mapToInt(MedicationTrendReport::getUsage).sum();
    }
}