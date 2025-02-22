package com.rocs.medical.records.application.app.facade.createMedicalRecords;

import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

public interface createMedicalRecordsFacade {
    boolean addMedicalRecord(MedicalRecords record);

    MedicalRecords addStudentMedicalRecord();

    void findMedicalInformationByLRN(long LRN);

}