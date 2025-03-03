package com.rocs.medical.records.application.app.facade.commonAilmentsReport;

import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

/**
 * An interface that manages the CommonAilmentsReport object maintenance such as
 * common ailments, occurrences, affected people, grade level, and strand
 * within the given report period.
 * */
public interface CommonAilmentsReportFacade {

    /**
     * Generates a report listing common ailments within a given period
     * @param startDate  The start date of the report period.
     * @param endDate    The end date of the report period.
     * @param gradeLevel The grade level to filter the report and can be null.
     * @param section    The section to filter the report and can be null.
     * @return A list of CommonAilmentsReport objects.
     */
    List<CommonAilmentsReport> generateReport(
            Date startDate,
            Date endDate,
            String gradeLevel,
            String section
    );
}
