package main.com.rocs.infirimary.deskstop.application.app.facade.medicalRecord.impl;


import main.com.rocs.infirimary.deskstop.application.app.facade.StudentMedicalRecordFacade;
import main.com.rocs.infirimary.deskstop.application.data.dao.medicalRecord.StudentMedicalRecordDao;
import main.com.rocs.infirimary.deskstop.application.data.dao.medicalRecord.impl.StudentMedicalRecordDaoImpl;
import main.com.rocs.infirimary.deskstop.application.model.medicalrecord.StudentMedicalRecord;


public class StudentMedicalRecordImplFacade implements StudentMedicalRecordFacade {
    private final StudentMedicalRecordDao StudentMedRecord = new StudentMedicalRecordDaoImpl();


    public StudentMedicalRecord SearchMedInfoUsingLRN(long LRN) {
         StudentMedicalRecord studentMedicalRecord = this.StudentMedRecord.SearchMedInfoUsingLRN(LRN);
         return studentMedicalRecord;
    }


}