package com.rocs.infirmary.application.app.facade.patient;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;

import java.util.List;
/**
 * The PatientMedicalRecordFacade interface defines methods for managing patient medical record.
 */
public interface PatientMedicalRecordFacade {
    /**
     * Retrieves all student medical records from the database.
     *
     * @return A list of student medical records, or an empty list if no records are found.
     */
    List<MedicalRecord> getAllPatientMedicalRecords();
    /**
     * Adds a new student medical record to the system.
     *
     * @param newPatient the student object containing all necessary attributes
     * @param employee the employee object containing staff member in charge of treatment
     * @return true if the medical record was successfully added; false otherwise
     */
    boolean addMedicalRecord(MedicalRecord newPatient, Employee employee);
    /**
     * adds a medicine administration entry for a student.
     * This intended to insert a record into the medicine_administered table and links it to an existing medical record.
     *
     * @return true if the record was successfully logged; false otherwise
     */
    boolean addMedicineAdministered(MedicalRecord medicalRecord, Employee employee);
}
