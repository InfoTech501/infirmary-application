package com.rocs.infirmary.application.module.medical.record.management.application;

import com.rocs.infirmary.application.app.facade.employee.EmployeeInformationFacade;
import com.rocs.infirmary.application.app.facade.employee.impl.EmployeeInformationFacadeImpl;
import com.rocs.infirmary.application.app.facade.patient.PatientMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.patient.impl.PatientMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.app.facade.student.record.impl.StudentMedicalRecordFacadeImpl;
import com.rocs.infirmary.application.data.dao.employee.EmployeeDao;
import com.rocs.infirmary.application.data.dao.employee.impl.EmployeeDaoImpl;
import com.rocs.infirmary.application.data.dao.patient.record.PatientMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.patient.record.impl.PatientMedicalRecordDaoImpl;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;

/**
 * Provides access to student medical record operations.
 */
public class MedicalRecordInfoMgtApplication {

    private StudentMedicalRecordFacade studentMedicalRecordFacade;
    private EmployeeInformationFacade employeeInformationFacade;
    private PatientMedicalRecordFacade patientMedicalRecordFacade;
    /**
     * Initializes the application with its required dependencies.
     */
    public MedicalRecordInfoMgtApplication() {
        StudentMedicalRecordDao studentMedicalRecordDao = new StudentMedicalRecordDaoImpl();
        this.studentMedicalRecordFacade = new StudentMedicalRecordFacadeImpl(studentMedicalRecordDao);

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        this.employeeInformationFacade = new EmployeeInformationFacadeImpl(employeeDao);

        PatientMedicalRecordDao patientMedicalRecordDao = new PatientMedicalRecordDaoImpl();
        this.patientMedicalRecordFacade = new PatientMedicalRecordFacadeImpl(patientMedicalRecordDao);
    }
    /**
     * This gets the for managing student medical records.
     * @return the StudentMedicalRecordFacade instance.
     */
    public StudentMedicalRecordFacade getStudentMedicalRecordFacade() {
        return studentMedicalRecordFacade;
    }
    /**
     * This gets the for managing employee information.
     * @return the StudentMedicalRecordFacade instance.
     */
    public EmployeeInformationFacade getEmployeeInformationFacade() {
        return employeeInformationFacade;
    }
    /**
     * This gets the for managing patient medical records.
     * @return the StudentMedicalRecordFacade instance.
     */
    public PatientMedicalRecordFacade getPatientMedicalRecordFacade() {
        return patientMedicalRecordFacade;
    }

}