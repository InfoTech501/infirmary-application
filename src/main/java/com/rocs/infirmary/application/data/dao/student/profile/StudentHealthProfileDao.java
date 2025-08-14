package com.rocs.infirmary.application.data.dao.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;

import java.util.List;
/**
 * {@code StudentHealthProfileDao} is used to facilitate student health profile-related operations within the system.
 * this handles the business logic for retrieving student health profile information.
 **/
public interface StudentHealthProfileDao {
    /**
     * Retrieves all student health profiles from the database.
     * return list of StudentMedicalRecord and objects with details such as full name, grade level, age, gender, section,
     * symptom, temperature readings, pulse rate, blood pressure, respiratory rate, and visit date.
     */
    List<Student> findAllStudentHealthProfile();
    /**
     * Retrieves all student health profile based on LRN.
     * @param LRN is a student's unique identifier assigned to each student
     * return list of StudentMedicalRecord and objects with details such as full name, grade level, age, gender, section,
     * symptom, temperature readings, pulse rate, blood pressure, respiratory rate, and visit date.
     */
    List<MedicalRecord> findStudentHealthProfileByLrn(String LRN);
}
