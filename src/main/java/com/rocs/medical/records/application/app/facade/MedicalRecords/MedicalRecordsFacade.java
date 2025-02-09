package com.rocs.medical.records.application.app.facade.MedicalRecords;

import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;


public interface MedicalRecordsFacade {

    boolean patient (MedicalRecords medicalRecords);
    boolean updateMedicalRecord(MedicalRecords medicalRecords); // Update an existing record
    boolean deleteMedicalRecord(int recordId);
}
