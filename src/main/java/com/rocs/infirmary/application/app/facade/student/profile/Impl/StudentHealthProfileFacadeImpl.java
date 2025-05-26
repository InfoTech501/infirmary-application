package com.rocs.infirmary.application.app.facade.student.profile.Impl;

import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.model.person.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StudentHealthProfileFacadeImpl implements StudentHealthProfileFacade {
    StudentHealthProfileDao studentHealthProfileDao = new StudentHealthProfileDaoImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileFacadeImpl.class);
    @Override
    public List<Student> getAllStudentHealthProfile() {
        List<Student> studentList = studentHealthProfileDao.findAllStudentHealthProfile();
        LOGGER.warn("getting all student health profiles might return empty");
        return studentList;
    }

    @Override
    public List<Student> getStudentHealthProfileByLRN(Long LRN) {
        List<Student> studentListProfile = studentHealthProfileDao.findStudentHealthProfileByLrn(LRN);
        return studentListProfile;
    }
}
