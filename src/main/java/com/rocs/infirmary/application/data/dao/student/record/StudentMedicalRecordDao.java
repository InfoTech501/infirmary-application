package com.rocs.infirmary.application.data.dao.student.record;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;

import java.util.Date;
/**
 * {@code StudentMedicalRecordDao} is used to facilitate student medical record-related operations within the system.
 * this handles the business logic for retrieving, deleting, updating, and adding student's medical records.
 **/
public interface StudentMedicalRecordDao {
    MedicalRecord findMedicalInformation(String LRN);

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
}
