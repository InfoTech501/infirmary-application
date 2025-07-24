package com.rocs.infirmary.application.data.dao.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
import java.util.List;

/**
 * StudentMedicalRecordDao data access object interface for managing student medical records.
 * This interface defines operations for database operations related to student medical information, including retrieval, updating, and deletion operations.
 */
public interface StudentMedicalRecordDao {

    /**
     * Used to retrieve all active medical information for a student by their LRN.
     * @param LRN The LRN of the student whose medical information is to be retrieved.
     * @return A list of student objects containing medical record information.
     */
    List<Student> findMedicalInformation(String LRN);

    /**
     * Used to retrieve medical information for a specific medical record by its unique identifier.
     * This method retrieves detailed medical record information including student details
     * and medical data for the specified medical record ID.
     * @param medicalRecordId The unique identifier of the medical record to retrieve.
     * @return A list of student objects containing the medical record information.
     */
    List<Student> findMedicalInformationById(Long medicalRecordId);

    /**
     * Used to retrieve all student medical records from the database.
     * This method fetches comprehensive medical information for all students who have medical records in the system.
     * @return A list of Student objects containing all medical records.
     */
    List<Student> findAllStudentMedicalRecords();


    /**
     * This intended to delete a student's medical record based on their Learner Reference Number (LRN).
     * The LRN is a unique identifier assigned to each student. This value is used to locate and delete the corresponding medical record.
     *
     */
    boolean deleteStudentMedicalRecord(Long medicalRecordId);

    /**
     * Used in updating aspecific fields of student's medical record including symptoms, temperature readings, visit date, and treatment.
     * @param symptoms A parameter to be updated: symptoms description for the medical record.
     * @param temperatureReadings  A parameter to be updated: temperature readings of the student.
     * @param visitDate  A parameter to be updated: date  of the medical visit.
     * @param treatment  A parameter to be updated: treatment information.
     * @param medicalRecordId The unique identifier of the medical record to be updated.
     * @return true if at least one field was successfully updated or false if no updates performed.
     */
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, Long medicalRecordId  );

}
