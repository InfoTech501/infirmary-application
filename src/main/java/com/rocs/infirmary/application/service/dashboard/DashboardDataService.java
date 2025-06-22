package com.rocs.infirmary.application.service.dashboard;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;

import java.util.List;

public class DashboardDataService {
    private final DashboardFacade dashboardFacade;

    public DashboardDataService(DashboardFacade dashboardFacade) {
        this.dashboardFacade = dashboardFacade;
    }

    public int getVisitCount (DateRange dateRange, String gradeLevel) {
        List<FrequentVisitReport> reports = dashboardFacade.generateFrequentVisitReport(
                dateRange.getStartDate(), dateRange.getEndDate(), gradeLevel);
        return reports.size();
    }

    public int getTotalMedicationUsage(DateRange dateRange) {
        List<MedicationTrendReport> reports = dashboardFacade.generateMedicationReport(
                dateRange.getStartDate(), dateRange.getEndDate());
        return reports.stream().mapToInt(MedicationTrendReport::getUsage).sum();
    }
}
