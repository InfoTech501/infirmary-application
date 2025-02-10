package com.medicine.inventory.application.app.facade.impl;

import com.medicine.inventory.application.app.facade.MedicineFacade;
import com.medicine.inventory.application.data.dao.Impl.MedicineDoaImpl;
import com.medicine.inventory.application.data.dao.MedicicineDaoo;

public class MedicineFacadeImpl implements MedicineFacade {

    MedicicineDaoo Med = new MedicineDoaImpl();
    @Override
    public boolean deleteById(int id) {
        return this.Med.deleteById(id);
    }

}
