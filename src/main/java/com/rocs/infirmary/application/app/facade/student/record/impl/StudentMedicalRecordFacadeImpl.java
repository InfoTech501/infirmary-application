package com.rocs.infirmary.application.app.facade.student.record.impl;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

import java.util.List;

/**
 * The StudentMedicalRecordFacadeImpl class is an implementation of the StudentMedicalRecordFacade interface.
 * It provides methods for managing students medical record.
 */
public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {

    /** The data access object for Student Medical Record. */
    private final StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();

    public Student findMedicalInformationByLRN(long LRN) {
        return this.studentMedRecord.getMedicalInformationByLRN(LRN);
    }
    @Override
    public List<Student> readAllStudentMedicalRecords() {
        List<Student> medicalRecords = this.studentMedRecord.getAllStudentMedicalRecords();

        return medicalRecords;
    }
}




