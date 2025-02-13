package main.com.rocs.infirimary.deskstop.application.data.dao.medicalRecord;


import main.com.rocs.infirimary.deskstop.application.model.medicalrecord.StudentMedicalRecord;

public interface StudentMedicalRecordDao {
    StudentMedicalRecord SearchMedInfoUsingLRN(long LRN);

}
