package com.rocs.infirmary.application.module.inventory.management.application;

import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.app.facade.medicine.inventory.impl.MedicineInventoryFacadeImpl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.data.dao.medicine.inventory.impl.MedicineInventoryDaoImpl;

public class InventoryManagementApplication {

   private MedicineInventoryFacade medicineInventoryFacade;
    /**
     *  Initializes the InventoryManagementApplication by setting up the
     *   medicine inventory data access and facade layers.
     */
    public InventoryManagementApplication() {
        MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();
        this.medicineInventoryFacade = new MedicineInventoryFacadeImpl(medicineInventoryDao);
    }
    /**
     * Gets the medicine inventory facade.
     *
     * @return the medicine inventory facade.
     */
    public MedicineInventoryFacade getMedicineInventoryFacade() {
        return medicineInventoryFacade;
    }
}