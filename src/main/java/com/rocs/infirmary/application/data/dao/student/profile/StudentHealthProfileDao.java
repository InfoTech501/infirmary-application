package com.rocs.infirmary.application.data.dao.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

/**
 * StudentHealthProfileDao is a data access object interface for managing student health profiles.
 * This interface defines operations for database operations related to student health profile information, providing methods to retrieve
 * health data for students.
 */
public interface StudentHealthProfileDao {
    /**
     * Used to retrieves all student health profiles from the database.
     * @return A list of Student objects containing all health profiles.
     */
    List<Student> findAllStudentHealthProfile();

    /**
     * Used to retrieve specific student health profile using their LRN.
     * @param LRN The LRN of the student whose health profile is to be retrieved.
     * @return A list of Student objects containing the student's health profile matching the LRN.
     */
    List<Student> findStudentHealthProfileByLrn(Long LRN);
}
