package com.rocs.medical.records.application.app.facade.impl;


import com.rocs.medical.records.application.app.facade.InventoryFacade;
import com.rocs.medical.records.application.data.dao.InventoryDao;
import com.rocs.medical.records.application.data.dao.impl.InventoryDaoImpl;
import com.rocs.medical.records.application.model.Inventory.Inventory;

import java.util.List;

public class InventoryFacadeImpl implements InventoryFacade {

    private InventoryDao inventoryDao = new InventoryDaoImpl();




    @Override
    public List<Inventory> getAllItems() {
        List<Inventory> inventoryList = this.inventoryDao.findAllItems();
        return inventoryList;
    }

}
