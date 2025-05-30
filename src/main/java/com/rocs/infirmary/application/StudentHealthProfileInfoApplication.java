package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.student.profile.Impl.StudentHealthProfileFacadeImpl;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

public class StudentHealthProfileInfoApplication {

    private StudentHealthProfileFacade studentHealthProfileFacade;
    private StudentHealthProfileDao studentHealthProfileDao = new StudentHealthProfileDaoImpl();

    private StudentMedicalRecordFacade studentMedicalRecordFacade;
    private StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();

    public StudentHealthProfileInfoApplication() {
        this.studentHealthProfileFacade = new StudentHealthProfileFacadeImpl(studentHealthProfileDao);
    }

    public StudentHealthProfileFacade getStudentHealthProfileFacade() {
        return  studentHealthProfileFacade;
    }

    public StudentMedicalRecordFacade getStudentMedicalRecordFacade(){return  studentMedicalRecordFacade; }



}
