package com.rocs.infirmary.application.app.facade.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

public interface StudentMedicalRecordFacade {

    /**
     * Retrieves a student's important details and student record.
     *
     * @param LRN The LRN (Learner Reference Number) of the student.
     */
    Student findMedicalInformationByLRN(long LRN);
}
