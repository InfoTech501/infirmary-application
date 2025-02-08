package com.rocs.medical.records.application.data.dao.studentmedicalreport;

import com.rocs.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

public interface StudentMedicalReportDao {

    StudentMedicalReport getPatientById(int patientId);
}
