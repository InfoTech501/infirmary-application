package com.rocs.infirmary.application.app.facade.student.record.impl;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The StudentMedicalRecordFacadeImpl class is an implementation of the StudentMedicalRecordFacade interface.
 * It provides methods for managing students medical record.
 */
public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {

    private StudentMedicalRecordDao studentMedRecord;
    private static final Logger logger = LoggerFactory.getLogger(StudentMedicalRecordFacadeImpl.class);

    /**
     * {@code StudentMedicalRecordFacadeImpl()} is a constructor that requires parameter
     * @param studentMedRecord DAO implementation of Student Medical Record
     * this provides the business logic of the Student Medical Record
     * {@code this.studentMedRecord = studentMedicalRecordDao} is used to initialize the StudentMedicalRecordDao
     */
    public StudentMedicalRecordFacadeImpl(StudentMedicalRecordDao studentMedRecord) {
        this.studentMedRecord = studentMedRecord;
    }

    public List<MedicalRecord> getMedicalInformationByLRN(String LRN) {
        logger.debug("Entering getMedicalInformationByLRN with LRN: {}", LRN);
        List<MedicalRecord> studentRecord = this.studentMedRecord.findMedicalInformation(LRN);
        logger.debug("Exiting getMedicalInformationByLRN with result: {}", studentRecord);
        return studentRecord;
    }

    /**
     * This is used to delete a student's medical record based on their Learner Reference Number (LRN).
     * boolean returns true if the deletion was successful, otherwise false.
     */
    @Override
    public boolean deleteStudentMedicalRecordByLrn(String LRN) {
        logger.info("Entering deleteStudentMedicalRecordByLrn with LRN: {}", LRN);
        boolean isDeleted = this.studentMedRecord.deleteStudentMedicalRecord(LRN);
        logger.info("Exiting deleteStudentMedicalRecordByLrn with result: {}", isDeleted);
        return isDeleted;
    }

    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, String LRN) {
        logger.debug("Entering updateStudentMedicalRecord with LRN: {}, symptoms: {}, temperature: {}, visitDate: {}, treatment: {}",
                LRN, symptoms, temperatureReadings, visitDate, treatment);
        Boolean updated =  this.studentMedRecord.updateStudentMedicalRecord(symptoms,temperatureReadings,visitDate,treatment, LRN);
        logger.debug("Exiting updateStudentMedicalRecord, update successful: {}", updated);
        return updated;
    }

}
