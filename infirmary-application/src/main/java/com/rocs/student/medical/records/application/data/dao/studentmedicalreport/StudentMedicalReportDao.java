package com.rocs.student.medical.records.application.data.dao.studentmedicalreport;

import com.rocs.student.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

public interface StudentMedicalReportDao {
    /**
     * Retrieves student details by student ID.
     *
     * @param patientId The unique ID of the student.
     * @return A Student object containing the student's medical records.
     */
    StudentMedicalReport getPatientById(int patientId);
}
