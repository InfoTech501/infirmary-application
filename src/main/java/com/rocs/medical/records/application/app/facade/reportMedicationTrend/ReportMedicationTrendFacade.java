package com.rocs.medical.records.application.app.facade.reportMedicationTrend;

import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.util.Date;
import java.util.List;

/**
 * An interface that manages the ReportMedicationTrend object maintenance such as
 * medication usage, medicine name, and medication stocks
 * within the given report period.
 * */
public interface ReportMedicationTrendFacade {

    /**
     * Get the report of medication trend within the given report period
     *  @param startDate  The start date of the report period.
     *  @param endDate    The end date of the report period.
     *  @return list of MedicationTrendReports object such as medication usage, medicine name and medication stocks.
     * */
    List<MedicationTrendReport> generateReport(
            Date startDate,
            Date endDate
    );

    /**
     * Display the report of medication trend within the given report period.
     *  @param reports    Checking if there is a report within the given report period.
     *  @param startDate  The start date of the report period.
     *  @param endDate    The end date of the report period.
     *  @return list of MedicationTrendReports object such as medication usage, medicine name and medication stocks.
     * */
    void displayMedTrendReport (List<MedicationTrendReport> reports, Date startDate, Date endDate);
}
