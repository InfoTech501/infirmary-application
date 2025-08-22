package com.rocs.infirmary.application.app.facade.student.record;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;

import java.util.Date;
import java.util.List;

/**
 * The StudentMedicalRecordFacade interface defines methods for managing students medical record.
 */
public interface StudentMedicalRecordFacade {
    /**
     * Retrieves all student's important details and student record.
     *
     * @param LRN       The LRN (Learner Reference Number) of the student.
     */
    List<MedicalRecord> getMedicalInformationByLRN(String LRN);
    /**
     * This intended to delete a student's medical record based on medical records' unique id.
     * The medical record id is a unique identifier assigned to each medical record. This value is used to locate and delete the corresponding medical record.
     * @param medicalRecordId is the uniue identifier assigned to each medical record of a student
     */
    boolean deleteStudentMedicalRecordByLrn(Long medicalRecordId);
    /**
     * This intended to update a student's medical record.
     * @param symptoms is the reported symptoms of the student
     * @param temperatureReadings is the student temperature
     * @param visitDate is the visit date of the student
     * @param treatment is the treatment provided by the nurse
     * @param LRN is a unique identifier assigned to each student
     * @param medicalRecordId is a unique identifier assigned to each records of student medical records
     * @return true if the student medical record is successfully updated and false if not
     */
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, String LRN, Long medicalRecordId  );
}
