package com.rocs.infirmary.application.app.facade.patient.impl;

import com.rocs.infirmary.application.app.facade.patient.PatientMedicalRecordFacade;
import com.rocs.infirmary.application.data.dao.patient.record.PatientMedicalRecordDao;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PatientMedicalRecordFacadeImpl implements PatientMedicalRecordFacade {

    private PatientMedicalRecordDao patientMedicalRecordDao;
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalRecordFacadeImpl.class);

    public PatientMedicalRecordFacadeImpl(PatientMedicalRecordDao patientMedicalRecordDao) {
        this.patientMedicalRecordDao = patientMedicalRecordDao;
    }

    @Override
    public List<MedicalRecord> getAllPatientMedicalRecords() {
        logger.info("Entering getAllStudentMedicalRecords");
        List<MedicalRecord> medicalRecords = this.patientMedicalRecordDao.findAllPatientMedicalRecords();
        logger.info("Exiting getAllStudentMedicalRecords with {} records found.", medicalRecords.size());
        return medicalRecords;
    }


    @Override
    public boolean addMedicalRecord(MedicalRecord newPatient) {
        logger.debug("Entering addStudentMedicalRecord with Student: {}", newPatient);
        boolean isInserted = this.patientMedicalRecordDao.addMedicalRecord(newPatient);
        logger.debug("Exiting addStudentMedicalRecord – success status: {}", isInserted);
        return isInserted;
    }

    @Override
    public boolean addMedicineAdministered(MedicalRecord medicalRecord) {
        logger.info("Entering addMedicineAdministered with studentId: {}, medicineId: {}",
                medicalRecord.getStudentId(), medicalRecord.getMedicineId());
        boolean isInserted = patientMedicalRecordDao.addMedicineAdministered(medicalRecord);
        logger.info("Exiting addMedicineAdministered with insertion result: {}", isInserted);
        return isInserted;
    }
}
