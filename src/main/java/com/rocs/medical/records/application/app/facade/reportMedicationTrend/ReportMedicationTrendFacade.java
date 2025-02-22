package com.rocs.medical.records.application.app.facade.reportMedicationTrend;

import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.util.Date;
import java.util.List;

/**
 * An interface that manages the report of medication trend within the given report period
 * * @param startDate  The start date of the report period.
 * * @param endDate    The end date of the report period.
 * * @return list of MedicationTrendReports such as usage, medicine name and stocks.
 * */

public interface ReportMedicationTrendFacade {
    List<MedicationTrendReport> generateReport(
            Date startDate,
            Date endDate
    );

    void displayMedTrendReport (List<MedicationTrendReport> reports, Date startDate, Date endDate);
}

