package main.com.rocs.infirimary.deskstop.application.data.dao.patient;


import main.com.rocs.infirimary.deskstop.application.model.patient.StudentMedicalRecord;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord SearchMedInfoUsingLRN(long LRN);

}
