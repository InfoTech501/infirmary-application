package com.rocs.medical.records.application.app.facade.low.stock.medicine;

import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;
public interface LowStockMedicineFacade {
    List<LowStockItem> findAllLowStockMedicine();
}
