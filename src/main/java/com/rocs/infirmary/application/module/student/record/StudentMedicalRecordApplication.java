package com.rocs.infirmary.application.module.student.record;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

/**
 * Application layer component for managing student medical record.
 * Main entry point for student medical record operations.
 * follows the dependency injection pattern by wiring together the DAO and facade layers.
 */
public class StudentMedicalRecordApplication {
   private final StudentMedicalRecordFacade studentMedicalRecordFacade;

    /**
     * Constructs a new StudentMedicalRecordApplication with all dependencies configured.
     * - Creates a StudentMedicalRecordDao implementation
     * - Injects the DAO into the StudentMedicalRecordFacade implementation
     */
    public StudentMedicalRecordApplication() {
        StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
        this.studentMedicalRecordFacade = new StudentMedicalRecordFacadeImpl(studentMedicalRecordDao);
    }

    /**
     * Returns the configured student medical record facade.
     * The facade provides a simplified interface for all student medical record operations.
     *
     * @return the StudentMedicalRecordFacade instance.
     */
    public StudentMedicalRecordFacade getStudentMedicalRecordFacade() {
        return studentMedicalRecordFacade;
    }
}
