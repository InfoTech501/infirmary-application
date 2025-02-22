package com.rocs.medical.records.application.data.dao.reportMedicationTrend;

import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface ReportMedicationTrendDao {

    /**
     * Returns the generated report of MedicationTrendReports object within the given report period
     *  @param startDate  The start date of the report period.
     *  @param endDate    The end date of the report period.
     *  @return list of MedicationTrendReports object such as medication usage, medicine name and medication stocks.
     * */

    List<MedicationTrendReport> getGeneratedReport(Date startDate, Date endDate);
}
