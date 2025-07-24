package com.rocs.infirmary.application.app.facade.student.profile.Impl;

import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.model.person.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * StudentHealthProfileFacade class is an implementation of the StudentHealthProfile interface.
 * StudentHealthProfileFacade provides implementations for managing student health profiles.
 */
public class StudentHealthProfileFacadeImpl implements StudentHealthProfileFacade {
    /**
     * Data access object for student health profile operations.
     */
    StudentHealthProfileDao studentHealthProfileDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileFacadeImpl.class);

    /**
     * Constructor for StudentHealthProfileFacadeImpl.
     * @param studentHealthProfileDao Is the StudentHealthProfile data access object used for student health profile operations.
     */
    public StudentHealthProfileFacadeImpl(StudentHealthProfileDao studentHealthProfileDao) {
        this.studentHealthProfileDao = studentHealthProfileDao;
    }

    /**
     * Used to retrieve all student health profiles from the database.
     * @return A list of all student health profiles.
     */
    @Override
    public List<Student> getAllStudentHealthProfile() {
        List<Student> studentList = studentHealthProfileDao.findAllStudentHealthProfile();
        LOGGER.warn("getting all student health profiles might return empty");
        return studentList;
    }

    /**
     * Used to retrieve a specific student health profile using their LRN.
     * @param LRN The LRN of the student whose health profile is to be retrieved.
     * @return A list containing the student's health profile matching the LRN.
     */
    @Override
    public List<Student> getStudentHealthProfileByLRN(Long LRN) {
        List<Student> studentListProfile = studentHealthProfileDao.findStudentHealthProfileByLrn(LRN);
        return studentListProfile;
    }
}
