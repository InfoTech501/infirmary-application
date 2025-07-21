package com.rocs.infirmary.application.app.facade.student.record;

import com.rocs.infirmary.application.data.model.person.nurse.Nurse;
import com.rocs.infirmary.application.data.model.person.student.Patient;
import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
import java.util.List;

/**
 * The StudentMedicalRecordFacade interface defines methods for managing students medical record.
 */
public interface StudentMedicalRecordFacade {
    /**
     * Retrieves a student's important details and student record.
     * @param LRN The LRN (Learner Reference Number) of the student.
     */
    Student getMedicalInformationByLRN(String LRN);
    /**
     * Retrieves all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<Patient> getAllStudentMedicalRecords();
    /**
     * Retrieves all nurse information from the database.
     *
     * @return A list of nurse information, or an empty list if no records are found.
     */
    List<Nurse> getAllNurseAccounts();
    /**
     * This intended to delete a student's medical record based on their Learner Reference Number (LRN).
     * The LRN is a unique identifier assigned to each student. This value is used to locate and delete the corresponding medical record.
     */
    boolean deleteStudentMedicalRecordByLrn(Long LRN);
    /**
     * This intended to update a student's medical record.
     * @param symptoms is the reported symptoms of the student
     * @param temperatureReadings is the student temperature
     * @param visitDate is the visit date of the student
     * @param treatement is the treatment provided by the nurse
     * @param LRN is a unique identifier assigned to each student
     * @return true if the student medical record is successfully updated and false if not
     */
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatement, long LRN  );
    /**
     * Adds a new student medical record to the system.
     *
     * @param newPatient the student object containing all necessary attributes
     * @return true if the medical record was successfully added; false otherwise
     */
    boolean addStudentMedicalRecord(Patient newPatient);
    /**
     * adds a medicine administration entry for a student.
     * This intended to insert a record into the medicine_administered table and links it to an existing medical record.
     *
     * @return true if the record was successfully logged; false otherwise
     */
    boolean addMedicineAdministered(Patient patient);
}
