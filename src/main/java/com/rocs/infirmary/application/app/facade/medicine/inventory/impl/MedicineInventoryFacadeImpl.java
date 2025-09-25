package com.rocs.infirmary.application.app.facade.medicine.inventory.impl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.app.facade.medicine.inventory.MedicineInventoryFacade;
import com.rocs.infirmary.application.data.dao.medicine.inventory.impl.MedicineInventoryDaoImpl;
import com.rocs.infirmary.application.data.model.inventory.Inventory;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MedicineInventoryFacadeImpl class is an implementation of the Medicine Inventory Facade Interface.
 * It provides methods for managing the create, update and delete functionality.
 */
public class MedicineInventoryFacadeImpl implements MedicineInventoryFacade {

    private MedicineInventoryDao medicineInventoryDao = new MedicineInventoryDaoImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineInventoryFacadeImpl.class);

    /**
     * MedicineInventoryFacadeImpl()
     * is a no argument constructor that provides an option to access the Medicine Inventory Facade without needing to provide parameters
     */
    public MedicineInventoryFacadeImpl() {

    }
    /**
     * {@code MedicineInventoryFacadeImpl()} is a constructor that requires parameter
     * @param medicineInventoryDao DAO implementation of Medicine Inventory
     * this provides the business logic of the Medicine Inventory
     * {@code this.medicineInventoryDao = medicineInventoryDao} is used to initialize the MedicineInventoryDao
     */
    public MedicineInventoryFacadeImpl(MedicineInventoryDao medicineInventoryDao) {
        this.medicineInventoryDao = medicineInventoryDao;
    }

    @Override
    public List<Medicine> getAllMedicine() {
        LOGGER.info("Entering findAllMedicine");
        List<Medicine> medicines = this.medicineInventoryDao.findAllMedicine();
        LOGGER.info("Exiting findAllMedicine with {} medicines found.", medicines.size());
        return medicines;
    }
    @Override
    public boolean deleteMedicineByItemName(List<Medicine> medicines) {
        LOGGER.info("Entering deleteMedicineByItemName with itemName: {}", medicines);
        boolean isDeleted = medicineInventoryDao.deleteMedicine(medicines);
        LOGGER.info("Exiting deleteMedicineByItemName with result: {}", isDeleted);
        return isDeleted;
    }

    @Override
    public boolean IsAvailable(String itemName) {
        LOGGER.info("Entering IsAvailable with itemName: {}", itemName);
        boolean available = medicineInventoryDao.isAvailable(itemName);
        LOGGER.info("Exiting IsAvailable with result: {}", available);
        return available;
    }

    @Override
    public List<Medicine> findAllMedicine() {
        LOGGER.info("Entering findAllMedicine()");
        List<Medicine> medicineInventoryList = medicineInventoryDao.findAllMedicine();
        LOGGER.info("Exiting findAllMedicine()");
        return medicineInventoryList;
    }

    @Override
    public boolean addMedicine(Medicine medicine) {
        LOGGER.debug("Entering addMedicine with Name: {} and Description: {}", medicine.getItemName(), medicine.getDescription());
        boolean isAdded = medicineInventoryDao.addMedicine(medicine);
        LOGGER.debug("Exiting addMedicine for '{}'", medicine.getItemName());
        return isAdded;
    }



    @Override
    public boolean addInventory(Long medicineId, String itemType, int quantity, Date expirationDate) {
        return this.medicineInventoryDao.addInventory(medicineId, itemType, quantity,expirationDate);
    }
    @Override
    public List<Medicine> getAllMedicineFromMedicineTable() {
        LOGGER.info("Fetching all medicine from medicine table");
        return this.medicineInventoryDao.findAll();
    }

    @Override
    public boolean deleteInventory(List<Medicine> medicines) {
        LOGGER.info("Deleting inventory for medicines: {}", medicines);
        return this.medicineInventoryDao.deleteInventory(medicines);
    }

    @Override
    public boolean updateMedicineInventory(Long inventoryId, Long medicineId, int quantity, String itemType, Date expirationDate) {
        LOGGER.info("Updating inventory — Inventory ID: {}, Medicine ID: {}, Quantity: {}, Type: {}, Expiration: {}", inventoryId, medicineId, quantity, itemType, expirationDate);
        return this.medicineInventoryDao.updateInventory(inventoryId,medicineId,quantity, itemType,expirationDate);
    }

    @Override
    public boolean updateMedicine(Long medicineId, String medicineName, String description) {
        LOGGER.info("Updating medicine — ID: {}, Name: {}", medicineId, medicineName);
        return this.medicineInventoryDao.updateMedicine(medicineId,medicineName,description);
    }
}

