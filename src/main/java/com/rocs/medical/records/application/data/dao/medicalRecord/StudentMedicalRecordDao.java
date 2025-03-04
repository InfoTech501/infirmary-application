package com.rocs.medical.records.application.data.dao.medicalRecord;


import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

import java.util.List;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord findMedicalInformationByLRN(long LRN);

    /**
     * Fetches all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if none are found.
     */
    List<StudentMedicalRecord> getAllStudentMedicalRecords();

}
