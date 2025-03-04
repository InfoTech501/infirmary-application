package com.rocs.medical.records.application.app.facade.medical.record;


import com.rocs.medical.records.application.model.medical.record.StudentMedicalRecord;

public interface StudentMedicalRecordFacade {

    /**
     * Retrieves a student's important details and medical record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */
    StudentMedicalRecord findMedicalInformationByLRN(long LRN);
}
