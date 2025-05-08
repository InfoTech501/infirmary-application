package com.rocs.infirmary.application.data.dao.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
import java.util.List;

public interface StudentMedicalRecordDao {
    Student getMedicalInformationByLRN(long LRN);

    List<Student> getAllStudentMedicalRecords();


    /**
     * This intended to delete a student's medical record based on their Learner Reference Number (LRN).
     * The LRN is a unique identifier assigned to each student. This value is used to locate and delete the corresponding medical record.
     *
     */

    boolean deleteStudentMedicalRecordByLrn (long LRN);
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, long LRN  );

}
