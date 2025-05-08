package com.rocs.infirmary.application.data.dao.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.Date;
import java.util.List;

public interface StudentMedicalRecordDao {
    Student findMedicalInformation(long LRN);

    List<Student> findAllStudentMedicalRecords();


    /**
     * This intended to delete a student's medical record based on their Learner Reference Number (LRN).
     * The LRN is a unique identifier assigned to each student. This value is used to locate and delete the corresponding medical record.
     *
     */

    boolean deleteStudentMedicalRecord(long LRN);
    boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate , String treatment, long LRN  );

}
