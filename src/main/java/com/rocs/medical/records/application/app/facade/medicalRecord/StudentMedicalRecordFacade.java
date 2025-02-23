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

    List<StudentMedicalRecord> findAllStudentMedicalRecords();
}
