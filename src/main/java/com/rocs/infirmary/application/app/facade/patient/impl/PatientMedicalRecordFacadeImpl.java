package com.rocs.infirmary.application.app.facade.patient.impl;

import com.rocs.infirmary.application.app.facade.patient.PatientMedicalRecordFacade;
import com.rocs.infirmary.application.data.dao.patient.record.PatientMedicalRecordDao;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
/**
 * The PatientMedicalRecordFacadeImpl class is an implementation of the PatientMedicalRecordFacade interface.
 * It provides methods for managing patient medical record.
 */
public class PatientMedicalRecordFacadeImpl implements PatientMedicalRecordFacade {

    private PatientMedicalRecordDao patientMedicalRecordDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientMedicalRecordFacadeImpl.class);

    /**
     * {@code PatientMedicalRecordFacadeImpl()} is a constructor that requires parameter
     *
     * @param patientMedicalRecordDao DAO implementation of Patient Medical Record
     * this provides the business logic of the Patient Medical Record
     * {@code this.patientMedicalRecordDao = patientMedicalRecordDao} is used to initialize the PatientMedicalRecordDao
     */
    public PatientMedicalRecordFacadeImpl(PatientMedicalRecordDao patientMedicalRecordDao) {
        this.patientMedicalRecordDao = patientMedicalRecordDao;
    }

    @Override
    public List<MedicalRecord> getAllPatientMedicalRecords() {
        LOGGER.info("Entering getAllStudentMedicalRecords");
        List<MedicalRecord> medicalRecords = this.patientMedicalRecordDao.findAllPatientMedicalRecords();
        LOGGER.info("Exiting getAllStudentMedicalRecords with {} records found.", medicalRecords.size());
        return medicalRecords;
    }

    @Override
    public boolean addMedicalRecord(MedicalRecord newPatient, Employee employee) {
        LOGGER.info("Entering addMedicalRecord with studentId: {}", newPatient.getStudentId());
        boolean isInserted = this.patientMedicalRecordDao.addMedicalRecord(newPatient, employee);
        LOGGER.info("Exiting addMedicalRecord for studentId: {}", newPatient.getStudentId());
        return isInserted;
    }

    @Override
    public boolean addMedicineAdministered(MedicalRecord medicalRecord, Employee employee) {
        LOGGER.info("Entering addMedicineAdministered with studentId: {}, medicineId: {}",
                medicalRecord.getStudentId(), medicalRecord.getMedicineId());
        boolean isInserted = patientMedicalRecordDao.addMedicineAdministered(medicalRecord, employee);
        LOGGER.info("Exiting addMedicineAdministered for studentId: {}, medicineId: {}",
                medicalRecord.getStudentId(), medicalRecord.getMedicineId());
        return isInserted;
    }
}
