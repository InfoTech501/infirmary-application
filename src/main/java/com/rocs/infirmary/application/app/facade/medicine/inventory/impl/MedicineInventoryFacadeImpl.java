package com.rocs.infirmary.application.app.facade.medicine.inventory.impl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.data.dao.medicine.inventory.impl.MedicineInventoryDaoImpl;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(MedicineInventoryFacadeImpl.class);

    @Override
    public List<Medicine> findAllMedicine() {
        logger.info("Entering findAllMedicine");
        List<Medicine> medicines = this.medicineInventoryDao.findAllMedicine();
        logger.info("Exiting findAllMedicine with {} medicines found.", medicines.size());
        return medicines;
    }
    @Override
    public boolean deleteMedicineByItemName(String itemName) {
        logger.warn("Entering deleteMedicineByItemName with itemName: {}", itemName);
        boolean isDeleted = medicineInventoryDao.deleteMedicine(itemName);
        logger.warn("Exiting deleteMedicineByItemName with result: {}", isDeleted);
        return isDeleted;
    }

    @Override
    public boolean IsAvailable(String itemName) {
        logger.debug("Entering IsAvailable with itemName: {}", itemName);
        boolean available = medicineInventoryDao.isAvailable(itemName);
        logger.debug("Exiting IsAvailable with result: {}", available);
        return available;
    }

    @Override
    public boolean addMedicine(Medicine medicine) {
        return this.medicineInventoryDao.addMedicine(medicine);

    }

    @Override
    public boolean addInventory(String medicineId, String itemType, int quantity) {
        return this.medicineInventoryDao.addInventory(medicineId, itemType, quantity);
    }
    @Override
    public List<Medicine>  findAllMedicineFromMedicineTable() {
        return this.medicineInventoryDao.findAllMedicine();
    }

    @Override
    public boolean deleteInventory(int inventoryID) {
        return this.medicineInventoryDao.deleteInventory(inventoryID);
    }


}
