package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

public class StudentMedicalRecordApplication {
   private final StudentMedicalRecordFacade studentMedicalRecordFacade;

    public StudentMedicalRecordApplication() {
        StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
        this.studentMedicalRecordFacade = new StudentMedicalRecordFacadeImpl(studentMedicalRecordDao);
    }

    public StudentMedicalRecordFacade getStudentMedicalRecordFacade() {
        return studentMedicalRecordFacade;
    }
}
