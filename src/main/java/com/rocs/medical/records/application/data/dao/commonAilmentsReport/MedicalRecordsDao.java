package com.rocs.medical.records.application.data.dao;

import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;


public interface MedicalRecordsDao {

    boolean patient(MedicalRecords medicalRecords);

    boolean saveOrUpdateMedicalRecord(MedicalRecords MedicalRecords);
}
