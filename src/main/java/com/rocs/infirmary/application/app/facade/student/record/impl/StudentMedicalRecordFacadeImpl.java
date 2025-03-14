package com.rocs.infirmary.application.app.facade.student.record.impl;


import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

public class StudentMedicalRecordFacadeImpl implements StudentMedicalRecordFacade {
    private final StudentMedicalRecordDao studentMedRecord = new StudentMedicalRecordDaoImpl();
    public Student findMedicalInformationByLRN(long LRN) {
        return this.studentMedRecord.getMedicalInformationByLRN(LRN);
    }
}




