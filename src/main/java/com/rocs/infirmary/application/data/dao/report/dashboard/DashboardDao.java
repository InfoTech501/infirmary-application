package com.rocs.infirmary.application.data.dao.report.dashboard;

import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;

import java.util.Date;
import java.util.List;

public interface DashboardDao {

    List<LowStockReport> getAllLowStockMedicine();

    /**
     * Retrieves common ailments report by start date, end date, grade level, and section of student.
     *
     * @param startDate  The start date of the report period.
     * @param endDate    The end date of the report period.
     * @param gradeLevel The grade level to filter the report and can be null.
     * @param section    The section to filter the report and can be null.
     * @return list of CommonAilmentsReport object such as common ailments, occurrences, affected people, grade level, and strand.
     */
    List<CommonAilmentsReport> getCommonAilmentReport(Date startDate, Date endDate, String gradeLevel, String section);

    /**
     * * This retrieves the Frequent visit report using the grade level of the student, start date, and end date.
     *
     * @param gradeLevel - The grade level of the students.
     * @param startDate -  The start of the report date period.
     * @param endDate - The end of the report date period.
     * @return list of FrequentVisitReport object like studentId, firstName, lastName, gradeLevel, symptoms, visitCount, and visitDate.
     * */
    List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate);

    /**
     * Retrieves medication trend report by start date, end date.
     *
     * @param startDate  The start date of the report period.
     * @param endDate    The end date of the report period.
     * @return list of MedicationTrendReports object such as medication usage, medicine name and medication stocks.
     */
    List<MedicationTrendReport> getMedicationTrendReport(Date startDate, Date endDate);

}
