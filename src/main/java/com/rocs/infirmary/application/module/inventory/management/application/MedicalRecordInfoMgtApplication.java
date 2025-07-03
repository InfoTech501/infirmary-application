package com.rocs.infirmary.application.module.inventory.management.application;

import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

/**
 * use the 4th year system as reference in docs.
 **/
public class MedicalRecordInfoMgtApplication {

    private StudentMedicalRecordFacade studentMedicalRecordFacade;
//docu dito
    public MedicalRecordInfoMgtApplication() {
        StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
        this.studentMedicalRecordFacade = new StudentMedicalRecordFacadeImpl(studentMedicalRecordDao);
    }
//docu dito
    public StudentMedicalRecordFacade getStudentMedicalRecordFacade() {
        return studentMedicalRecordFacade;
    }

}