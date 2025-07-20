package com.rocs.infirmary.application.app.facade.student.record.impl;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.nurse.Nurse;
import com.rocs.infirmary.application.data.model.person.student.Patient;
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

    private StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(StudentMedicalRecordFacadeImpl.class);

    /**
     * StudentMedicalRecordFacadeImpl()
     * is a no argument constructor that provides an option to access the Student Medical Records Facade without needing to provide parameters
     */
    public StudentMedicalRecordFacadeImpl() {

    }
    /**
     * {@code StudentMedicalRecordFacadeImpl()} is a constructor that requires parameter
     * @param studentMedRecord DAO implementation of Student Medical Record
     * this provides the business logic of the Medicine Inventory
     * {@code this.studentMedRecord = studentMedicalRecordDao} is used to initialize the StudentMedicalRecordDao
     */
    public StudentMedicalRecordFacadeImpl(StudentMedicalRecordDao studentMedRecord) {
        this.studentMedRecord = studentMedRecord;
    }

    public Student getMedicalInformationByLRN(String LRN) {
        logger.debug("Entering getMedicalInformationByLRN with LRN: {}", LRN);
        Student student = this.studentMedRecord.findMedicalInformation(LRN);
        logger.debug("Exiting getMedicalInformationByLRN with result: {}", student);
        return student;
    }

    @Override
    public List<Patient> getAllStudentMedicalRecords() {
        logger.info("Entering getAllStudentMedicalRecords");
        List<Patient> medicalRecords = this.studentMedRecord.findAllStudentMedicalRecords();
        logger.info("Exiting getAllStudentMedicalRecords with {} records found.", medicalRecords.size());
        return medicalRecords;
    }

    /**
     * This is used to delete a student's medical record based on their Learner Reference Number (LRN).
     * boolean returns true if the deletion was successful, otherwise false.
     */
    @Override
    public boolean deleteStudentMedicalRecordByLrn(Long LRN) {
        logger.info("Entering deleteStudentMedicalRecordByLrn with LRN: {}", LRN);
        boolean isDeleted = this.studentMedRecord.deleteStudentMedicalRecord(LRN);
        logger.info("Exiting deleteStudentMedicalRecordByLrn with result: {}", isDeleted);
        return isDeleted;
    }

    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, long LRN) {
        logger.debug("Entering updateStudentMedicalRecord with LRN: {}, symptoms: {}, temperature: {}, visitDate: {}, treatment: {}",
                LRN, symptoms, temperatureReadings, visitDate, treatment);
        Boolean updated =  this.studentMedRecord.updateStudentMedicalRecord(symptoms,temperatureReadings,visitDate,treatment, LRN);
        logger.debug("Exiting updateStudentMedicalRecord, update successful: {}", updated);
        return updated;
    }

    @Override
    public boolean addStudentMedicalRecord(Patient record) {
        logger.debug("Entering addStudentMedicalRecord with Student: {}", record);
        boolean isInserted = this.studentMedRecord.addStudentMedicalRecord(record);
        logger.debug("Exiting addStudentMedicalRecord – success status: {}", isInserted);
        return isInserted;
    }

    @Override
    public List<Nurse> getAllNurseAccounts() {
        logger.info("Retrieving all nurse accounts...");
        List<Nurse> nurses = studentMedRecord.getAllNurseAccounts();
        logger.info("Retrieved {} nurse records", nurses.size());
        return nurses;
    }

    @Override
    public boolean addMedicineAdministered(Patient record) {
        logger.info("Entering addMedicineAdministered with studentId: {}, medicineId: {}",
                record.getStudentId(), record.getMedicineId());
        boolean isInserted = studentMedRecord.addMedicineAdministered(record);
        logger.info("Exiting addMedicineAdministered with insertion result: {}", isInserted);
        return isInserted;
    }

}
