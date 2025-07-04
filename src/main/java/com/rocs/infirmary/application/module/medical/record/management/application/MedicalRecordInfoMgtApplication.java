package com.rocs.infirmary.application.module.medical.record.management.application;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

/**
 * Provides access to student medical record operations.
 */
public class MedicalRecordInfoMgtApplication {

    private StudentMedicalRecordFacade studentMedicalRecordFacade;
    /**
     * Initializes the application with its required dependencies.
     */
    public MedicalRecordInfoMgtApplication() {
        StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
        this.studentMedicalRecordFacade = new StudentMedicalRecordFacadeImpl(studentMedicalRecordDao);
    }
    /**
     * This gets the for managing student medical records.
     * @return the StudentMedicalRecordFacade instance.
     */
    public StudentMedicalRecordFacade getStudentMedicalRecordFacade() {
        return studentMedicalRecordFacade;
    }
}