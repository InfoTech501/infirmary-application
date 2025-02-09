package com.rocs.infirmary.application.data.dao.reportMedicationTrend;

import com.rocs.infirmary.application.model.report.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface ReportMedicationTrendDao {
    List<MedicationTrendReport> getGenerateReport(Date startDate, Date endDate);
}
