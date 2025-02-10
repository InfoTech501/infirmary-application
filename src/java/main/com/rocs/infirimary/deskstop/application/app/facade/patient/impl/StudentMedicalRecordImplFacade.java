package main.com.rocs.infirimary.deskstop.application.app.facade.patient.impl;


import main.com.rocs.infirimary.deskstop.application.app.facade.StudentMedicalRecordFacade;
import main.com.rocs.infirimary.deskstop.application.data.dao.patient.StudentMedicalRecordDao;
import main.com.rocs.infirimary.deskstop.application.data.dao.patient.impl.StudentMedicalRecordDaoImpl;
import main.com.rocs.infirimary.deskstop.application.model.patient.StudentMedicalRecord;


public class StudentMedicalRecordImplFacade implements StudentMedicalRecordFacade {
    private final StudentMedicalRecordDao PatientDao = new StudentMedicalRecordDaoImpl();


    public StudentMedicalRecord SearchMedInfoUsingLRN(long LRN) {
         StudentMedicalRecord studentMedicalRecord = this.PatientDao.SearchMedInfoUsingLRN(LRN);
         return studentMedicalRecord;
    }


}