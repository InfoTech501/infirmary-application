package com.rocs.Medicine.Inventory.Application.app.facade.impl;


import com.rocs.Medicine.Inventory.Application.app.facade.InventoryFacade;
import com.rocs.Medicine.Inventory.Application.data.dao.InventoryDao;
import com.rocs.Medicine.Inventory.Application.data.dao.impl.InventoryDaoImpl;
import com.rocs.Medicine.Inventory.Application.model.Inventory.Inventory;

import java.util.List;

public class InventoryFacadeImpl implements InventoryFacade {

    private InventoryDao inventoryDao = new InventoryDaoImpl();




    @Override
    public List<Inventory> getAllItems() {
        List<Inventory> inventoryList = this.inventoryDao.findAllItems();
        return inventoryList;
    }

}
