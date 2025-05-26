package com.rocs.infirmary.application.data.dao.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

public interface StudentHealthProfileDao {
    List<Student> findAllStudentHealthProfile();
    List<Student> findStudentHealthProfileByLrn(Long LRN);
}
