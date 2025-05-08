package com.rocs.infirmary.application.app.facade.student.record.impl;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

import java.util.Date;
import java.util.List;

/**
 * The StudentMedicalRecordFacadeImpl class is an implementation of the StudentMedicalRecordFacade interface.
 * It provides methods for managing students medical record.
 */
public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {

    /** The data access object for Student Medical Record. */
    private final StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();

    public Student getMedicalInformationByLRN(long LRN) {
        return this.studentMedRecord.findMedicalInformation(LRN);
    }

    @Override
    public List<Student> getAllStudentMedicalRecords() {
        List<Student> medicalRecords = this.studentMedRecord.findAllStudentMedicalRecords();

        return medicalRecords;
    }

    /**
     * This is used to delete a student's medical record based on their Learner Reference Number (LRN).
     *
     * boolean returns true if the deletion was successful, otherwise false.
     */
    @Override
    public boolean deleteStudentMedicalRecordByLrn(long LRN) {
        return this.studentMedRecord.deleteStudentMedicalRecord(LRN);
    }
    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatement, long LRN) {
        return this.studentMedRecord.updateStudentMedicalRecord(symptoms,temperatureReadings,visitDate,treatement, LRN);
    }
}




