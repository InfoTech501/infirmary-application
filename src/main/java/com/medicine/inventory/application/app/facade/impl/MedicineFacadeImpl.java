package com.medicine.inventory.application.app.facade.impl;

import com.medicine.inventory.application.app.facade.MedicineFacade;
import com.medicine.inventory.application.data.dao.Impl.MedicineDaoImpl;
import com.medicine.inventory.application.data.dao.MedicicineDao;

public class MedicineFacadeImpl implements MedicineFacade {

    MedicicineDao Med = new MedicineDaoImpl();


    @Override
    public boolean deleteById(int id) {
        return this.Med.deleteById(id);
    }

}
