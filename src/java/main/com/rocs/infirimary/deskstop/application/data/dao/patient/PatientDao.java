package main.com.rocs.infirimary.deskstop.application.data.dao.patient;

import main.com.rocs.infirimary.deskstop.application.model.patient.Patient;


public interface PatientDao {
    Patient SearchMedInfoUsingLRN(long LRN);

}
