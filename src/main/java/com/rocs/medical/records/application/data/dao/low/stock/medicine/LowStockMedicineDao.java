package com.rocs.medical.records.application.data.dao.low.stock.medicine;

import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;

public interface LowStockMedicineDao {
    List<LowStockItem> getAllLowStockMedicine();
}
