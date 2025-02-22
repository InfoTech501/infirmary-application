package com.rocs.medical.records.application.app.facade.lowStockMedicine;

import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;
public interface LowStockMedicineFacade {
    List<LowStockItem> checkLowStockAndNotify();
}
