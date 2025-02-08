package com.rocs.medical.records.application.app.facade.studentmedicalreport.impl;

import com.rocs.medical.records.application.app.facade.studentmedicalreport.StudentMedicalReportFacade;
import com.rocs.medical.records.application.data.dao.studentmedicalreport.StudentMedicalReportDao;
import com.rocs.medical.records.application.data.dao.studentmedicalreport.impl.StudentMedicalReportDaoImpl;
import com.rocs.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

public class StudentMedicalReportFacadeImpl implements StudentMedicalReportFacade {

        StudentMedicalReportDao studentDao = new StudentMedicalReportDaoImpl();

    @Override
    public StudentMedicalReport getStudentDetails(int patientId) {
        return studentDao.getPatientById(patientId);
    }
}
