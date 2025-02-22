package com.rocs.medical.records.application.data.dao.commonAilmentsReport;

import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

public interface CommonAilmentsReportDAO {

    /**
     * Returns the generated report of CommonAilmentsReport List within the given report period
     *  @param startDate  The start date of the report period.
     *  @param endDate    The end date of the report period.
     *  @param gradeLevel The grade level to filter the report and can be null.
     *  @param section    The section to filter the report and can be null.
     *  @return list of CommonAilmentsReport object such as common ailments, occurrences, affected people, grade level, and strand.
     * */

    List<CommonAilmentsReport> getGeneratedReport(Date startDate, Date endDate, String gradeLevel, String section);
}
