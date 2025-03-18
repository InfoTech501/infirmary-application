package com.rocs.infirmary.application.data.dao.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

public interface StudentMedicalRecordDao {
    Student getMedicalInformationByLRN(long LRN);
    List<Student> getAllStudentMedicalRecords();
}
