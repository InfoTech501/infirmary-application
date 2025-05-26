package com.rocs.infirmary.application.app.facade.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;

import java.util.List;

public interface StudentHealthProfileFacade {
    List<Student> getAllStudentHealthProfile();
    List<Student> getStudentHealthProfileByLRN(Long LRN);

}
