package main.com.rocs.infirimary.deskstop.application.app.facade.patient.impl;


import main.com.rocs.infirimary.deskstop.application.app.facade.PatientFacade;
import main.com.rocs.infirimary.deskstop.application.data.dao.patient.impl.PatientImplDao;
import main.com.rocs.infirimary.deskstop.application.data.dao.patient.PatientDao;
import main.com.rocs.infirimary.deskstop.application.model.patient.Patient;


public class PatientFacadeImpl implements PatientFacade {
    private final PatientDao PatientDao = new PatientImplDao();


    public Patient SearchMedInfoUsingLRN(long LRN) {
        Patient patient = this.PatientDao.SearchMedInfoUsingLRN(LRN);
        return patient;
    }


}