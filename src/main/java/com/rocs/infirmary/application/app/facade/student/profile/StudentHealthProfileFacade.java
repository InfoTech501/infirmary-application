package com.rocs.infirmary.application.app.facade.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;

import java.util.List;
/**
 * The StudentHealthProfileFacade interface defines methods for managing students health profile.
 */
public interface StudentHealthProfileFacade {
    /**
     * Retrieves all student health profile from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<Student> getAllStudentHealthProfile();
    /**
     * Retrieves a student's important details and student health profile.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */
    List<MedicalRecord> getStudentHealthProfileByLRN(Long LRN);

}
