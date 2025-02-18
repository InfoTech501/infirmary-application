package com.rocs.medical.records.application.data.dao.createMedicalRecords;

import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;


public interface createMedicalRecordsDao {

    boolean patient(MedicalRecords medicalRecords);

    boolean saveOrUpdateMedicalRecord(MedicalRecords MedicalRecords);
}
