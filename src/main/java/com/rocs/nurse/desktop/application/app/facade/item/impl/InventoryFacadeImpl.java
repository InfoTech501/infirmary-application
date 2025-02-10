package com.rocs.nurse.desktop.application.app.facade.item.impl;


import com.rocs.nurse.desktop.application.app.facade.item.InventoryFacade;
import com.rocs.nurse.desktop.application.data.dao.item.InventoryDao;
import com.rocs.nurse.desktop.application.data.dao.item.impl.InventoryDaoImpl;
import com.rocs.nurse.desktop.application.model.Inventory.Inventory;

import java.util.List;

public class InventoryFacadeImpl implements InventoryFacade {

    private InventoryDao inventoryDao = new InventoryDaoImpl();




    @Override
    public List<Inventory> getAllItems() {
        List<Inventory> inventoryList = this.inventoryDao.findAllItems();
        return inventoryList;
    }

}
