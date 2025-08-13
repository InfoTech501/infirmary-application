package com.rocs.infirmary.application.module.student.profile;

import com.rocs.infirmary.application.app.facade.student.profile.Impl.StudentHealthProfileFacadeImpl;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;

/**
 * Application layer component for managing student health profiles.
 * Main entry point for student health profile operations.
 * follows the dependency injection pattern by wiring together the DAO and facade layers.
 */
public class StudentHealthProfileApplication {
    private final StudentHealthProfileFacade studentHealthProfileFacade;

    /**
     * Constructs a new StudentHealthProfileApplication with all dependencies configured.
     * - Creates a StudentHealthProfileDao implementation
     * - Injects the DAO into the StudentHealthProfileFacade implementation
     */
    public StudentHealthProfileApplication() {
        StudentHealthProfileDao studentHealthProfileDao = new StudentHealthProfileDaoImpl();
        this.studentHealthProfileFacade = new StudentHealthProfileFacadeImpl(studentHealthProfileDao);
    }

    /**
     * Returns the configured student health profile facade.
     * The facade provides a simplified interface for all student health profile operations.
     *
     * @return the StudentHealthProfileFacade instance.
     */
    public StudentHealthProfileFacade getStudentHealthProfileFacade() {
        return studentHealthProfileFacade;
    }
}
