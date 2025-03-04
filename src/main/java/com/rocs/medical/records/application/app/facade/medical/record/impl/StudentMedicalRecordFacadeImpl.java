package com.rocs.medical.records.application.app.facade.medical.record.impl;


import com.rocs.medical.records.application.app.facade.medical.record.StudentMedicalRecordFacade;
import com.rocs.medical.records.application.data.dao.medical.record.StudentMedicalRecordDao;
import com.rocs.medical.records.application.data.dao.medical.record.impl.StudentMedicalRecordDaoImpl;
import com.rocs.medical.records.application.model.medical.record.StudentMedicalRecord;

public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {
    private final StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();
    public StudentMedicalRecord findMedicalInformationByLRN(long LRN) {
        return this.studentMedRecord.getMedicalInformationByLRN(LRN);
    }
}




