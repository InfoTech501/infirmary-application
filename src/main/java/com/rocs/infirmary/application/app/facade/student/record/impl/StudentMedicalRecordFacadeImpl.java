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
    private static final Logger logger = LoggerFactory.getLogger(StudentMedicalRecordFacadeImpl.class);

    public Student getMedicalInformationByLRN(long LRN) {
        logger.debug("Entering getMedicalInformationByLRN with LRN: {}", LRN);
        Student student = this.studentMedRecord.findMedicalInformation(LRN);
        logger.debug("Exiting getMedicalInformationByLRN with result: {}", student);
        return student;
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
    public boolean deleteStudentMedicalRecordByLrn(long LRN) {
        logger.warn("Entering deleteStudentMedicalRecordByLrn with LRN: {}", LRN);
        boolean isDeleted = this.studentMedRecord.deleteStudentMedicalRecord(LRN);
        logger.warn("Exiting deleteStudentMedicalRecordByLrn with result: {}", isDeleted);
        return isDeleted;
    }
    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatement, long LRN) {
        logger.debug("Entering updateStudentMedicalRecord with LRN: {}, symptoms: {}, temperature: {}, visitDate: {}, treatment: {}",
                LRN, symptoms, temperatureReadings, visitDate, treatement);
        Boolean updated =  this.studentMedRecord.updateStudentMedicalRecord(symptoms,temperatureReadings,visitDate,treatement, LRN);
        logger.debug("Exiting updateStudentMedicalRecord, update successful: {}", updated);
        return updated;
    }
}




