package com.rocs.medical.records.application.app.facade.facade.commonAilmentsReport;

import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.util.Date;
import java.util.List;

public interface CommonAilmentsReportFacade {

    List<CommonAilmentsReport> generateReport(
            Date startDate,
            Date endDate,
            String gradeLevel,
            String section
    );
}
