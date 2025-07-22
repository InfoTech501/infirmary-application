package com.rocs.infirmary.application.app.facade.student.record.impl;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The StudentMedicalRecordFacadeImpl class is an implementation of the StudentMedicalRecordFacade interface.
 * It provides methods for managing students medical record.
 */
public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {

    /** The data access object for Student Medical Record. */
    private final StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();
    StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(StudentMedicalRecordFacadeImpl.class);

    public StudentMedicalRecordFacadeImpl(StudentMedicalRecordDao studentMedRecord) {
        this.studentMedicalRecordDao = studentMedRecord;
    }

    @Override
    public List<Student> getMedicalInformationByLRN(String LRN) {
        logger.debug("Entering getMedicalInformationByLRN with LRN: {}", LRN);
        List<Student> medicalInfo = this.studentMedRecord.findMedicalInformation(LRN);
        logger.debug("Exiting getMedicalInformationByLRN with result: {}", medicalInfo);
        logger.debug("getMedicalInformationByLRN items found: {}", medicalInfo.size());
        return medicalInfo;
    }


    @Override
    public List<Student> getMedicalRecordById(Long medicalRecordId) {
        logger.debug("Entering getMedicalRecordById with: {}", medicalRecordId);
        List<Student> medicalInfo = this.studentMedRecord.findMedicalInformationById(medicalRecordId);
        logger.debug("Exiting getMedicalRecordById with result: {}", medicalInfo);
        logger.debug("getMedicalRecordById items found: {}", medicalInfo.size());
        return medicalInfo;
    }

    @Override
    public List<Student> getAllStudentMedicalRecords() {
        logger.info("Entering getAllStudentMedicalRecords");
        List<Student> medicalRecords = this.studentMedRecord.findAllStudentMedicalRecords();
        logger.info("Exiting getAllStudentMedicalRecords with {} records found.", medicalRecords.size());
        return medicalRecords;
    }

    /**
     * This is used to delete a student's medical record based on their Learner Reference Number (LRN).
     *
     * boolean returns true if the deletion was successful, otherwise false.
     */
    @Override
    public boolean deleteStudentMedicalRecordById(Long medicalRecordId) {
        logger.warn("Using deleteStudentMedicalRecordByLrn with LRN: {}", medicalRecordId);
        boolean isDeleted = this.studentMedRecord.deleteStudentMedicalRecord(medicalRecordId);
        logger.warn("Exiting deleteStudentMedicalRecordByLrn with result: {}", isDeleted);
        return isDeleted;
    }
    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, Long medicalRecordId) {
        logger.debug("Using updateStudentMedicalRecord with LRN: {}, symptoms: {}, temperature: {}, visitDate: {}, treatment: {}",
                medicalRecordId, symptoms, temperatureReadings, visitDate, treatment);
        Boolean updated =  this.studentMedRecord.updateStudentMedicalRecord(symptoms,temperatureReadings,visitDate,treatment, medicalRecordId);
        logger.debug("Exiting updateStudentMedicalRecord, update successful: {}", updated);
        return updated;
    }
}




