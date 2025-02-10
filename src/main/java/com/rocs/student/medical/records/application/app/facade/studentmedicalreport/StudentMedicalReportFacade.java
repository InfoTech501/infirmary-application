package com.rocs.student.medical.records.application.app.facade.studentmedicalreport;

import com.rocs.student.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

public interface StudentMedicalReportFacade {
    /**
     * Fetches student medical records using student ID.
     *
     * @param patientId The unique ID of the student.
     * @return A Student object containing medical records.
     */
    StudentMedicalReport getStudentDetails(int patientId);
}
