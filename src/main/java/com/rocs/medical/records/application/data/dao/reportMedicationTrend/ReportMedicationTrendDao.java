package com.rocs.medical.records.application.data.dao.reportMedicationTrend;

import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.util.Date;
import java.util.List;

/**
 * Returns the MedicationTrendReport List with a given report period.
 * * @param startDate  The start date of the report period.
 * * @param endDate    The end date of the report period.
 * */

public interface ReportMedicationTrendDao {
    List<MedicationTrendReport> getGeneratedReport(Date startDate, Date endDate);
}
