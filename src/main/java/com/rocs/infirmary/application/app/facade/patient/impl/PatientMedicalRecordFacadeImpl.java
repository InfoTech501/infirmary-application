package com.rocs.infirmary.application.app.facade.patient.impl;

import com.rocs.infirmary.application.app.facade.patient.PatientMedicalRecordFacade;
import com.rocs.infirmary.application.data.dao.patient.record.PatientMedicalRecordDao;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * The PatientMedicalRecordFacadeImpl class is an implementation of the PatientMedicalRecordFacade interface.
 * It provides methods for managing patient medical record.
 */
public class PatientMedicalRecordFacadeImpl implements PatientMedicalRecordFacade {

    private PatientMedicalRecordDao patientMedicalRecordDao;
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalRecordFacadeImpl.class);
    /**
     * {@code PatientMedicalRecordFacadeImpl()} is a constructor that requires parameter
     * @param patientMedicalRecordDao DAO implementation of Patient Medical Record
     * this provides the business logic of the Patient Medical Record
     * {@code this.patientMedicalRecordDao = patientMedicalRecordDao} is used to initialize the PatientMedicalRecordDao
     */
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
    public boolean addMedicalRecord(MedicalRecord newPatient, Employee employee) {
        logger.debug("Entering addStudentMedicalRecord with Student: {}", newPatient);
        boolean isInserted = this.patientMedicalRecordDao.addMedicalRecord(newPatient, employee);
        logger.debug("Exiting addStudentMedicalRecord – success status: {}", isInserted);
        return isInserted;
    }

    @Override
    public boolean addMedicineAdministered(MedicalRecord medicalRecord, Employee employee) {
        logger.info("Entering addMedicineAdministered with studentId: {}, medicineId: {}",
                medicalRecord.getStudentId(), medicalRecord.getMedicineId());
        boolean isInserted = patientMedicalRecordDao.addMedicineAdministered(medicalRecord, employee);
        logger.info("Exiting addMedicineAdministered with insertion result: {}", isInserted);
        return isInserted;
    }
}
