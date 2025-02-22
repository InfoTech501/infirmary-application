package com.rocs.medical.records.application.app.facade.lowStockMedicine.impl;

import com.rocs.medical.records.application.app.facade.lowStockMedicine.LowStockMedicineFacade;
import com.rocs.medical.records.application.data.dao.lowStockMedicine.impl.LowStockMedicineDaoImpl;
import com.rocs.medical.records.application.model.inventory.LowStockItem;

import java.util.List;

public class LowStockMedicineFacadeImpl implements LowStockMedicineFacade {
    private LowStockMedicineDaoImpl lowStockMedicineDao;

    public LowStockMedicineFacadeImpl() {
        this.lowStockMedicineDao = new LowStockMedicineDaoImpl();
    }

    @Override
    public List<LowStockItem> checkLowStockAndNotify() {
        List<LowStockItem> lowStockItems = lowStockMedicineDao.getLowStockItems();
        for (LowStockItem item : lowStockItems) {
            sendNotification(item.getDescription(), item.getQuantityAvailable());
        }
        return lowStockItems;
    }

    private void sendNotification(String description, int quantityAvailable) {
        System.out.println("Medicine Name: " + description);
        System.out.println("Current Stock Level: " + quantityAvailable);
        System.out.println("Notification: The stock level of " + description + " is low. Current stock level: " + quantityAvailable + ". Please reorder supplies.");
    }
    
}


