package com.rocs.medical.records.application.app.facade.reportMedicationTrend.impl;

import com.rocs.medical.records.application.app.facade.reportMedicationTrend.ReportMedicationTrendFacade;
import com.rocs.medical.records.application.data.dao.reportMedicationTrend.impl.ReportMedicationTrendDaoImpl;
import com.rocs.medical.records.application.data.dao.reportMedicationTrend.ReportMedicationTrendDao;
import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportMedicationTrendFacadeImpl implements ReportMedicationTrendFacade {

    /** The data access object for ReportMedicationTrend. */
    private final ReportMedicationTrendDao medTrendDao = new ReportMedicationTrendDaoImpl();

    /**
     * A class that manages the report of medication trend within the given report period
     * * @param startDate  The start date of the report period.
     * * @param endDate    The end date of the report period.
     * * @return list of MedicationTrendReports.
     * */

    @Override
    public List<MedicationTrendReport> generateReport(Date startDate, Date endDate) {
        return this.medTrendDao.getGeneratedReport(startDate, endDate);
    }

    /**
     * A class that manages the displaying of medication trend within the given report period.
     * * @param reports    Checking if there is a report within the given report period.
     * * @param startDate  The start date of the report period.
     * * @param endDate    The end date of the report period.
     * * @return list of MedicationTrendReports.
     * */

    @Override
    public void displayMedTrendReport(List<MedicationTrendReport> reports, Date startDate, Date endDate) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("\nMedication Trend report");
        System.out.println("Period date: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("\nTotal no. of medicine usage within the period date: " + reports.size());

        for (MedicationTrendReport report : reports) {
            printMedicationTrend(report);
        }

    }

    private static void printMedicationTrend(MedicationTrendReport report) {
        System.out.print("\nMedication Usage: " + report.getUsage());
        System.out.print(" | Medicine: " + report.getMedicineName());
        System.out.print(" | Medication Stocks: " + report.getStocks());
    }
}
