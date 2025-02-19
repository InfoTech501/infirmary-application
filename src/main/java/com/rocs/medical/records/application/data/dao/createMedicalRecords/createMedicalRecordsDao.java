package com.rocs.medical.records.application.data.dao.createMedicalRecords;

import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

public interface createMedicalRecordsDao {

    boolean createMedicalRecord(MedicalRecords medicalRecords);

    MedicalRecords findMedicalInformationByLRN(long LRN);

}