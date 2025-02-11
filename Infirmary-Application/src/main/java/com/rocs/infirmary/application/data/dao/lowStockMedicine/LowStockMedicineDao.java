package com.rocs.infirmary.application.data.dao.lowStockMedicine;

import com.rocs.infirmary.application.model.lowStockMedicine.LowStockMedicine;

import java.util.List;
public interface LowStockMedicineDao {
    List<LowStockMedicine> getLowStockItems();
    void checkLowStockAndNotify();
}
