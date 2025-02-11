package com.rocs.medical.records.application.data.dao.commonAilmentsReport;

import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

public interface CommonAilmentsReportDAO {
    List<CommonAilmentsReport> getGeneratedReport(Date startDate, Date endDate, String gradeLevel, String section);
}
