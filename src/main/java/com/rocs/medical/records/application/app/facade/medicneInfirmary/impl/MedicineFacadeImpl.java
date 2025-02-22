package com.rocs.medical.records.application.app.facade.medicneInfirmary.impl;


import com.rocs.medical.records.application.app.facade.medicneInfirmary.MedicineFacade;
import com.rocs.medical.records.application.data.dao.medicineInfirmary.Impl.MedicineDaoImpl;
import com.rocs.medical.records.application.data.dao.medicineInfirmary.MedicineDao;

public class MedicineFacadeImpl implements MedicineFacade {

    private final MedicineDao medicineDao = new MedicineDaoImpl();


    @Override
    public boolean deleteMedicineById(String medicineId) {
        return this.medicineDao.deleteMedicineById(medicineId);
    }
}

