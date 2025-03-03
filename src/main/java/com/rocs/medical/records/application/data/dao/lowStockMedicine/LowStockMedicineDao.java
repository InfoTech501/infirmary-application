package com.rocs.medical.records.application.data.dao.lowStockMedicine;

import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;

public interface LowStockMedicineDao {
    List<LowStockItem> getLowStockItems();
}
