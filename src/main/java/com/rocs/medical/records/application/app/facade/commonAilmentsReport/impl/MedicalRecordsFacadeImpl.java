package com.rocs.medical.records.application.app.facade.MedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.MedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.data.dao.MedicalRecordsDao;
import com.rocs.medical.records.application.data.dao.impl.MedicalRecordsDaoImpl;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;


public class MedicalRecordsFacadeImpl implements MedicalRecordsFacade {

        private MedicalRecordsDao medicalRecordsDao = new MedicalRecordsDaoImpl();

        @Override
        public boolean saveOrUpdateMedicalRecord(MedicalRecords medicalRecords) {
            return this.medicalRecordsDao.saveOrUpdateMedicalRecord(medicalRecords);
        }

}

