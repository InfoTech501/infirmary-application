package com.rocs.infirmary.application.app.facade.dashboard;

import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface DashboardFacade {
    List<LowStockReport> findAllLowStockMedicine();
    List<CommonAilmentsReport> generateCommonAilmentReport(Date startDate,Date endDate, String gradeLevel, String section);
    /**
     * This retrieves the report of the frequent visit of the student in the clinic within the given start date, end date, and grade level of the student.
     * * @param startDate - The start date of the report period.
     * * @param endDate - The end date of the report period.
     * * @param gradeLevel - The grade level of the student.
     * * @return list of FrequentVisitReport object like studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
     * */
    List<FrequentVisitReport> generateFrequentVisitReport(Date startDate, Date endDate, String gradeLevel);
    List<MedicationTrendReport> generateMedicationReport(Date startDate, Date endDate);

}
