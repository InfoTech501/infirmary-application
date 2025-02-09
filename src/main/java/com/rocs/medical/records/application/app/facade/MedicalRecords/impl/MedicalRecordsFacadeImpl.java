package com.rocs.medical.records.application.app.facade.MedicalRecords.impl;

import com.rocs.medical.records.application.app.facade.MedicalRecords.MedicalRecordsFacade;
import com.rocs.medical.records.application.data.dao.MedicalRecordsDao;
import com.rocs.medical.records.application.data.dao.impl.medicalrecordsDaoImpl;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;


public class MedicalRecordsFacadeImpl implements MedicalRecordsFacade {

        private MedicalRecordsDao medicalRecordsDao = new medicalrecordsDaoImpl();

        @Override
        public boolean patient(MedicalRecords medicalRecords) {
            return this.medicalRecordsDao.patient(medicalRecords); // Corrected variable name
        }

        @Override
        public boolean updateMedicalRecord(MedicalRecords medicalRecords) {
        return this.medicalRecordsDao.patient(medicalRecords);
        }

        @Override
        public boolean deleteMedicalRecord(int recordId) {
        return false;
        }
}

