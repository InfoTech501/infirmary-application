package com.rocs.medical.records.application.app.facade.medicalRecord;


import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

import java.util.List;

public interface StudentMedicalRecordFacade {

    /**
     * Retrieves a student's important details and medical record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */

    void findMedicalInformationByLRN(long LRN);

    /**
     * Retrieves all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<StudentMedicalRecord> readAllStudentMedicalRecords();
}
