package com.rocs.infirmary.application.app.facade.lowStockMedicine.impl;

import com.rocs.infirmary.application.app.facade.lowStockMedicine.LowStockMedicineFacade;
import com.rocs.infirmary.application.data.dao.lowStockMedicine.impl.LowStockMedicineDaoImpl;

public class LowStockMedicineFacadeImpl implements LowStockMedicineFacade {
    private LowStockMedicineDaoImpl lowStockMedicineDao;

    public LowStockMedicineFacadeImpl() {
        lowStockMedicineDao = new LowStockMedicineDaoImpl();
    }

    @Override
    public void checkLowStockAndNotify() {
        lowStockMedicineDao.checkLowStockAndNotify();
    }

}

