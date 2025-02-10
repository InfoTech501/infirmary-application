package com.rocs.student.medical.records.application.app.facade.studentmedicalreport.impl;


import com.rocs.student.medical.records.application.app.facade.studentmedicalreport.StudentMedicalReportFacade;
import com.rocs.student.medical.records.application.data.dao.studentmedicalreport.StudentMedicalReportDao;
import com.rocs.student.medical.records.application.data.dao.studentmedicalreport.impl.StudentMedicalReportDaoImpl;
import com.rocs.student.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

/**
 * Implementation of StudentFacade that interacts with the DAO layer.
 */
public class StudentMedicalReportFacadeImpl implements StudentMedicalReportFacade {

    StudentMedicalReportDao studentDao = new StudentMedicalReportDaoImpl();

    @Override
    public StudentMedicalReport getStudentDetails(int patientId) {
        return studentDao.getPatientById(patientId);
    }
}
