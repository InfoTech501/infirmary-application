package com.rocs.medical.records.application.data.dao.medical.record;


import com.rocs.medical.records.application.model.medical.record.StudentMedicalRecord;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord getMedicalInformationByLRN(long LRN);

}
