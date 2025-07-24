package com.rocs.infirmary.application.app.facade.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

/**
 * The StudentHealthProfileFacade interface defines methods for managing student health profiles.
 */
public interface StudentHealthProfileFacade {
    /**
     * Used to retrieve all student health profiles from the database.
     * @return A list of all student health profiles.
     */
    List<Student> getAllStudentHealthProfile();

    /**
     * Used to retrieve a specific student health profile using their LRN.
     * @param LRN The LRN of the student whose health profile is to be retrieved.
     * @return A list containing the student's health profile matching the LRN.
     */
    List<Student> getStudentHealthProfileByLRN(Long LRN);

}
