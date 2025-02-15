package com.rocs.medical.records.application.data.dao.reportMedicationTrend;

import com.rocs.medical.records.application.model.report.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface ReportMedicationTrendDao {
    List<MedicationTrendReport> getGeneratedReport(Date startDate, Date endDate);
}
