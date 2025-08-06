package com.rocs.infirmary.application.app.facade.student.profile.Impl;

import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.Impl.StudentHealthProfileDaoImpl;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * The StudentHealthProfileFacadeImpl class is an implementation of the StudentHealthProfileFacade interface.
 * It provides methods for managing student health profile.
 */
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
    public List<MedicalRecord> getStudentHealthProfileByLRN(Long LRN) {
        List<MedicalRecord> studentListProfile = studentHealthProfileDao.findStudentHealthProfileByLrn(LRN);
        return studentListProfile;
    }
}
