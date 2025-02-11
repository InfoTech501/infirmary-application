package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.lowStockMedicine.LowStockMedicineFacade;
import com.rocs.infirmary.application.app.facade.lowStockMedicine.impl.LowStockMedicineFacadeImpl;

public class InfirmarySystemApplication {
    public static void main(String[] args) {
        LowStockMedicineFacade inventoryFacade = new LowStockMedicineFacadeImpl();
        inventoryFacade.checkLowStockAndNotify();
    }
}

