package com.rocs.infirmary.application.data.dao.student.record;


import com.rocs.infirmary.application.data.model.person.student.Student;

public interface StudentMedicalRecordDao {
    Student getMedicalInformationByLRN(long LRN);

}
