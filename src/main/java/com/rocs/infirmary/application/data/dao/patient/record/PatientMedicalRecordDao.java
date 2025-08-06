package com.rocs.infirmary.application.data.dao.patient.record;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;

import java.util.List;
/**
 * {@code PatientMedicalRecordDao} is used to facilitate patient-medical-record-related operations within the system.
 * this handles the business logic for retrieving and adding patient medical record.
 **/
public interface PatientMedicalRecordDao {
    /**
     * this is used to find all patient medical records in the medical record
     * return list of StudentMedicalRecord and objects with details such as symptom, temperature readings, pulse rate, blood pressure, respiratory rate, and visit date.
     **/
    List<MedicalRecord> findAllPatientMedicalRecords();
    /**
     * Adds a new patient medical record to the database.
     *
     * @param medicalRecord the model containing all attributes of the medicine to be added
     * @return true if the medical record is successfully added; false if the addition fails
     */
    boolean addMedicalRecord(MedicalRecord medicalRecord, Employee employee);
    /**
     * Adds a new medicine administration entry linked to an existing medical record.
     * This is intended to log information such as medicine ID, nurse in charge, description, quantity, and date administered.
     *
     * @param medicalRecord the student object containing medication and administration details
     * @return true if the medicine administration record is successfully added; false otherwise
     */
    boolean addMedicineAdministered(MedicalRecord medicalRecord, Employee employee);
}
