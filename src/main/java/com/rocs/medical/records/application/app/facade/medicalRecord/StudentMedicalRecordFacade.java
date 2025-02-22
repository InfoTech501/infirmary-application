package com.rocs.medical.records.application.app.facade.medicalRecord;


public interface StudentMedicalRecordFacade {

    /**
     * Retrieves a student's important details and medical record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */

    void findMedicalInformationByLRN(long LRN);
}
