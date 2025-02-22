package com.rocs.medical.records.application.app.facade.medicalRecord.createMedicalRecords;

import com.rocs.medical.records.application.model.medicalrecord.createmedicalrecords.MedicalRecords;

public interface CreateMedicalRecordsFacade {
    boolean addMedicalRecord(MedicalRecords record);

    MedicalRecords addStudentMedicalRecord();

}