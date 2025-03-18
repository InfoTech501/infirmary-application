package com.rocs.infirmary.application.app.facade.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

/**
 * The StudentMedicalRecordFacade interface defines methods for managing students medical record.
 */
public interface StudentMedicalRecordFacade {

    /**
     * Retrieves a student's important details and student record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */
    Student findMedicalInformationByLRN(long LRN);
    /**
     * Retrieves all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<Student> readAllStudentMedicalRecords();
}
