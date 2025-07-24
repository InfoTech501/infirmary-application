package com.rocs.infirmary.application.app.facade.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
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
    List<Student> getMedicalInformationByLRN(String LRN);

    /**
     * Retrieves all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<Student> getAllStudentMedicalRecords();

    /**
     * This intended to delete a student medical record using medical record id.
     * The medical record id is a unique identifier assigned to each medical records. This value is used to locate and delete the corresponding medical record.
     */
    boolean deleteStudentMedicalRecordById(Long medicalRecordId);

    /**
     * Used to update an existing student medical record with new information.
     * Updates specified medical record with the provided symptoms, temperature readings, visit date, and treatment.
     * @param symptoms A parameter to be updated: symptoms description for the medical record.
     * @param temperatureReadings A parameter to be updated: temperature readings for the patient.
     * @param visitDate A parameter to be updated: visit date of the medical visit.
     * @param treatment A parameter to be updated: treatment description.
     * @param medicalRecordId The unique identifier of the medical record to be updated.
     */
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, Long medicalRecordId  );

    /**
     * Used to retrieve specific student medical record using its unique identifier.
     * @param medicalRecordId The unique identifier of the medical record to retrieve.
     * @return A list containing the student with the specified medical record.
     */
    List<Student> getMedicalRecordById(Long medicalRecordId);
}
