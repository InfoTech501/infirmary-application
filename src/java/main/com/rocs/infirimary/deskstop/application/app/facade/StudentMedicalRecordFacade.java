package main.com.rocs.infirimary.deskstop.application.app.facade;



import main.com.rocs.infirimary.deskstop.application.model.patient.StudentMedicalRecord;


public interface StudentMedicalRecordFacade {
    StudentMedicalRecord SearchMedInfoUsingLRN(long LRN);
}
