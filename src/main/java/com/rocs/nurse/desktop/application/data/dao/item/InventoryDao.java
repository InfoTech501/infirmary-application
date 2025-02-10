package com.rocs.nurse.desktop.application.data.dao.item;
import com.rocs.nurse.desktop.application.model.Inventory.Inventory;

import java.util.List;

public interface InventoryDao {


    List<Inventory> findAllItems();
}
