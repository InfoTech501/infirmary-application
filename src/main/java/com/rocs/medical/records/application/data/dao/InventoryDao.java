package com.rocs.medical.records.application.data.dao;
import com.rocs.medical.records.application.model.Inventory.Inventory;

import java.util.List;

public interface InventoryDao {


    List<Inventory> findAllItems();
}
