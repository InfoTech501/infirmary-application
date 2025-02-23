package com.rocs.medical.records.application.data.dao.medicalRecord;


import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

import java.util.List;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord findMedicalInformationByLRN(long LRN);

    List<StudentMedicalRecord> getAllStudentMedicalRecords();

}
