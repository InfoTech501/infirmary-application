package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.student.profile.Impl.StudentHealthProfileFacadeImpl;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;

public class StudentHealthProfileInfoApplication {

    private StudentHealthProfileFacade studentHealthProfileFacade;
    private StudentHealthProfileDao studentHealthProfileDao = new StudentHealthProfileDaoImpl();

    public StudentHealthProfileInfoApplication() {
        this.studentHealthProfileFacade = new StudentHealthProfileFacadeImpl(studentHealthProfileDao);
    }

    public StudentHealthProfileFacade getStudentHealthProfileFacade() {
        return  studentHealthProfileFacade;
    }



}
