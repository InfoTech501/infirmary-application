package com.rocs.medical.records.application.app.facade.medicineInventory.impl;

import com.rocs.medical.records.application.app.facade.medicineInventory.InventoryFacade;
import com.rocs.medical.records.application.data.dao.medicineInventory.InventoryDao;
import com.rocs.medical.records.application.data.dao.medicineInventory.impl.InventoryDaoImpl;
import com.rocs.medical.records.application.model.medicineInventory.Inventory;

import java.util.List;

public class InventoryFacadeImpl implements InventoryFacade {

    private InventoryDao inventoryDao = new InventoryDaoImpl();

    @Override
    public List<Inventory> getInventoryItems() {
        return this.inventoryDao.getInventoryItems();
    }


}


