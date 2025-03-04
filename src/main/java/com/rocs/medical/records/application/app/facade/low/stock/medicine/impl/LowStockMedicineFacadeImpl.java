package com.rocs.medical.records.application.app.facade.low.stock.medicine.impl;

import com.rocs.medical.records.application.app.facade.low.stock.medicine.LowStockMedicineFacade;
import com.rocs.medical.records.application.data.dao.low.stock.medicine.impl.LowStockMedicineDaoImpl;
import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;

public class LowStockMedicineFacadeImpl implements LowStockMedicineFacade {
    private LowStockMedicineDaoImpl lowStockMedicineDao = new LowStockMedicineDaoImpl();
    @Override
    public List<LowStockItem> findAllLowStockMedicine() {
        List<LowStockItem> lowStockItems = lowStockMedicineDao.getAllLowStockMedicine();
        return lowStockItems;
    }

}


