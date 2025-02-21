package com.rocs.medical.records.application.data.dao.medicalRecord;


import com.rocs.medical.records.application.model.medicalrecord.StudentMedicalRecord;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord findMedicalInformationByLRN(long LRN);

}
