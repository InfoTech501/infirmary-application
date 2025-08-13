package com.rocs.infirmary.application.app.facade.student.profile.Impl;

import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * The StudentHealthProfileFacadeImpl class is an implementation of the StudentHealthProfileFacade interface.
 * It provides methods for managing student health profile.
 */
public class StudentHealthProfileFacadeImpl implements StudentHealthProfileFacade {

    private final StudentHealthProfileDao studentHealthProfileDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileFacadeImpl.class);

    /**
     * {@code StudentHealthProfileFacadeImpl()} is a constructor that requires parameter
     * @param studentHealthProfileDao DAO implementation of Student Health Profile!
     * this provides the business logic of the Student Health Profile
     * {@code this.studentHealthProfileDao = studentHealthProfileDao} is used to initialize the StudentHealthProfileDao
     */
    public StudentHealthProfileFacadeImpl(StudentHealthProfileDao studentHealthProfileDao) {
        this.studentHealthProfileDao = studentHealthProfileDao;
    }

    /**
     * This is used to get the list of all the student's health profile
     * @return list of student health profiles
     */
    @Override
    public List<Student> getAllStudentHealthProfile() {
        List<Student> studentList = studentHealthProfileDao.findAllStudentHealthProfile();
        LOGGER.warn("getting all student health profiles might return empty");
        return studentList;
    }

    /**
     * This is used to get the list of all the student's medical health based on their Learner Reference Number (LRN).
     * @return list of student health profile
     */
    @Override
    public List<MedicalRecord> getStudentHealthProfileByLRN(String LRN) {
        LOGGER.info("getting all student health profiles might return empty");
        List<MedicalRecord> medicalRecords = studentHealthProfileDao.findStudentHealthProfileByLrn(LRN);
        LOGGER.info("findStudentHealthProfileByLrn for LRN {}: {}", LRN, medicalRecords);
        return medicalRecords;
    }
}
