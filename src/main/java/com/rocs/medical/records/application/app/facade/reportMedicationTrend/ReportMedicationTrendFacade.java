package com.rocs.medical.records.application.app.facade.reportMedicationTrend;

import com.rocs.medical.records.application.model.report.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface ReportMedicationTrendFacade {
    List<MedicationTrendReport> generateReport(
            Date startDate,
            Date endDate
    );
}
