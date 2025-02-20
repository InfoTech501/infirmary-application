package com.rocs.medical.records.application.data.dao.commonAilmentsReport;

import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

/**
 * Returns the MedicationTrendReport List with a given report period.
 * * @param startDate  The start date of the report period.
 * * @param endDate    The end date of the report period.
 * * @param gradeLevel The grade level to filter the report and can be null.
 * * @param section    The section to filter the report and can be null.
 * */

public interface CommonAilmentsReportDAO {
    List<CommonAilmentsReport> getGeneratedReport(Date startDate, Date endDate, String gradeLevel, String section);
}
