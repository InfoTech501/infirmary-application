package com.rocs.medical.records.application.app.facade.studentmedicalreport;

import com.rocs.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

public interface StudentMedicalReportFacade {
    StudentMedicalReport getStudentDetails(int patientId);
}
