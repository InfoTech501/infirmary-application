package com.rocs.medical.records.application.app.facade.MedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.MedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.data.dao.medicalrecordsDao;
import com.rocs.medical.records.application.data.dao.impl.medicalrecordsDaoImpl;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;


public class MedicalRecordsFacadeImpl implements MedicalRecordsFacade {

    private medicalRecordsDao medicalRecordsDao = new medicalrecordsDaoImpl();

    @Override
    public boolean patient(MedicalRecords medicalRecords) {
        return this.medicalRecordsDao.patient(medicalRecords);
    }
}
