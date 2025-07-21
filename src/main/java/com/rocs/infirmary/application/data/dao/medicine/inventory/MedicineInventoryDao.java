package com.rocs.infirmary.application.data.dao.medicine.inventory;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.Date;
import java.util.List;
/**
 * {@code MedicineInventoryDao} is used to facilitate medicine-related operations within the system.
 * this handles the business logic for retrieving, adding, updating, and deleting medicines and inventory records.
 **/
public interface MedicineInventoryDao {
    /**
     * this is used to find all medicine in the inventory
     * return list of Medicine and inventory objects with details such as medicine name, description, quantity, and expiration date.
     **/
    List<Medicine> findAllMedicine();
    /**
     * This is intended to delete medicine based on its Name(ItemName).
     * @param medicines is a list of medicine to be removed
     * @return true if the medicine is successfully deleted, false when medicine is not successfully deleted
     */
    boolean deleteMedicine(List<Medicine> medicines);
    /**
     * This is used to check if the medicine is already existing in the database
     * @param itemName is used to specify the name of the medicine that needs to be checked
     * @return true if the medicine is already existing, false otherwise
     **/
    boolean isAvailable(String itemName);
    /**
     * Adds a new medicine to the database.
     * @param medicine the model containing all attributes of the medicine to be added
     * @return true if the medicine is successfully added; false if the addition fails
     **/
    boolean addMedicine(Medicine medicine);
    /**
     * This is used to update the specified medicine record in the inventory.
     * @param inventoryId is a unique identifier that is used to specify the item in inventory to be updated
     * @param medicineId is a unique identifier that is  used to specify the medicine that needs to be updated
     * @param quantity is used to update the quantity of medicine
     * @param itemType is used to update the inventory item type
     * @param expirationDate is used to update the medicine's expiration date
     * @return true when the medicine are successfully updated, false when the medicine are not updated successfully due to error
     * */
    boolean updateInventory(Long inventoryId, Long medicineId, int quantity, String itemType, Date expirationDate);
    /**
     * This is used to update the specified medicine record.
     * @param medicineId is a unique identifier that is  used to specify the medicine that needs to be updated
     * @param description is used to update the medicine description
     * @return true when the medicine are successfully updated, false when the medicine are not updated successfully due to error
     * */
    boolean updateMedicine(Long medicineId, String medicineName, String description);
    /**
     * this is used to add new medicine in the inventory
     * @param medicineId is a unique identifier that is used to specify the item to be added
     * @param itemType is used to specify what type of item are going to be added on the item table
     * @param quantity is used to specify the quantity of the item being added
     **/
    boolean addInventory(Long medicineId , String itemType, int quantity, Date expirationDate );
    /**
     * This is intended to delete medicine based on its Name(ItemName).
     * @param medicines is the list of selected medicine to be deleted.
     * @return true if the item is successfully deleted, false when item is not successfully deleted
     */
    boolean deleteInventory(List<Medicine> medicines);
    /**
     * this is used to find all medicine in the medicine table
     * return list of Medicine attributes with details such as medicineId,medicine name, and description
     **/
    List<Medicine> findAll();
}
