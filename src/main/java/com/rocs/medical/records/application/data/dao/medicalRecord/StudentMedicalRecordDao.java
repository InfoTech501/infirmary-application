package com.rocs.medical.records.application.data.dao.medicalRecord;


import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

public interface StudentMedicalRecordDao {

    /**
     * Retrieves a student's important details and medical record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */


    StudentMedicalRecord findMedicalInformationByLRN(long LRN);

}
