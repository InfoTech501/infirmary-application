package com.rocs.Medicine.Inventory.Application.data.dao;
import com.rocs.Medicine.Inventory.Application.model.Inventory.Inventory;

import java.util.List;

public interface InventoryDao {


    List<Inventory> findAllItems();
}
