package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.student.profile.Impl.StudentHealthProfileFacadeImpl;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;

public class StudentHealthProfileApplication {
    private final StudentHealthProfileFacade studentHealthProfileFacade;

    public StudentHealthProfileApplication() {
        StudentHealthProfileDao studentHealthProfileDao = new StudentHealthProfileDaoImpl();
        this.studentHealthProfileFacade = new StudentHealthProfileFacadeImpl(studentHealthProfileDao);
    }

    public StudentHealthProfileFacade getStudentHealthProfileFacade() {
        return studentHealthProfileFacade;
    }
}
