package com.rocs.medical.records.application.app.facade.reports.medication.trend.report;

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
    List<MedicationTrendReport> generateMedicationReport(
            Date startDate,
            Date endDate
    );

}
