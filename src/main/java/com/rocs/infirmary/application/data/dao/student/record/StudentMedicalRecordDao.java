package com.rocs.infirmary.application.data.dao.student.record;

import com.rocs.infirmary.application.data.model.person.nurse.Nurse;
import com.rocs.infirmary.application.data.model.person.student.Patient;
import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
import java.util.List;
/**
 * {@code StudentMedicalRecordDao} is used to facilitate student medical record-related operations within the system.
 * this handles the business logic for retrieving, deleting, updating, and adding student's medical records.
 **/
public interface StudentMedicalRecordDao {
    Student findMedicalInformation(String LRN);
    /**
     * this is used to find all student medical records in the medical record
     * return list of StudentMedicalRecord and objects with details such as symptom, temperature readings, pulse rate, blood pressure, (edit).
     **/
    List<Patient> findAllStudentMedicalRecords();
    /**
     * Retrieves all nurse information from the database.
     * @return A list of nurse information, or an empty list if no records are found.
     */
    List<Nurse> getAllNurseAccounts();
    /**
     * This is intended to delete medical record based on LRN.
     * @param LRN is a student's unique identifier assigned to each student
     * @return true if the medical record is successfully deleted, false when medical record is not successfully deleted
     * */
    boolean deleteStudentMedicalRecord(String LRN);
    /**
     * This is used to update the specified medicine record in the inventory.
     * @param symptoms is a student's symptoms
     * @param temperatureReadings is used to update the quantity of medicine
     * @param visitDate is used to update the medicine description
     * @param treatment is used to update the medicine's expiration date
     * @param LRN is a student's unique identifier assigned to each student
     * @return true when the medical record are successfully updated, false when the medical record are not updated successfully due to error
     * */
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, String LRN  );
    /**
     * Adds a new student medical record to the database.
     *
     * @param patient the model containing all attributes of the medicine to be added (edit this - change record into student)
     * @return true if the medical record is successfully added; false if the addition fails
     */
    boolean addStudentMedicalRecord(Patient patient);
    /**
     * Adds a new medicine administration entry linked to an existing medical record.
     * This is intended to log information such as medicine ID, nurse in charge, description, quantity, and date administered.
     *
     * @param patient the student object containing medication and administration details
     * @return true if the medicine administration record is successfully added; false otherwise
     */
    boolean addMedicineAdministered(Patient patient);
}
