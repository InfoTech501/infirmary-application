package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.app.facade.medicine.inventory.impl.MedicineInventoryFacadeImpl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.data.dao.medicine.inventory.impl.MedicineInventoryDaoImpl;

public class InventoryManagementApplication {

   private MedicineInventoryFacade medicineInventoryFacade;

    public InventoryManagementApplication() {
        MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();
        this.medicineInventoryFacade = new MedicineInventoryFacadeImpl(medicineInventoryDao);
    }

    public MedicineInventoryFacade getMedicineInventoryFacade() {
        return medicineInventoryFacade;
    }
}
