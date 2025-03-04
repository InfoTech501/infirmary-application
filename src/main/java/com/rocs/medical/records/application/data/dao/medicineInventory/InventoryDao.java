package com.rocs.medical.records.application.data.dao.medicineInventory;

import com.rocs.medical.records.application.model.medicineInventory.Inventory;

import java.util.List;

public interface InventoryDao {


    List<Inventory> getInventoryItems();
}
