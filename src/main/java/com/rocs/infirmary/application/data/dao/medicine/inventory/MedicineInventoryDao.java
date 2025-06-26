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
     * @param itemName is a unique identifier assigned to a medicine. This value is used to locate and delete the corresponding medicine.
     * @return true if the medicine is successfully deleted, false when medicine is not successfully deleted
     */
    boolean deleteMedicine(String itemName);
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
     * @param medicineId isis a unique identifier that is  used to specify the medicine that needs to be updated
     * @param quantity is used to update the quantity of medicine
     * @param description is used to update the medicine description
     * @param expirationDate is used to update the medicine's expiration date
     * @return true when the medicine are successfully updated, false when the medicine are not updated successfully due to error
     * */
    boolean updateMedicine(String medicineId, int quantity, String description, Date expirationDate);
    /**
     * this is used to add new medicine in the inventory
     * @param medicineId is a unique identifier that is used to specify the item to be added
     * @param itemType is used to specify what type of item are going to be added on the item table
     * @param quantity is used to specify the quantity of the item being added
     **/
    boolean addInventory(String medicineId , String itemType, int quantity );
    /**
     * This is intended to delete medicine based on its Name(ItemName).
     * @param inventoryID is a unique identifier assigned to an item in the inventory. This value is used to locate and delete the corresponding item.
     * @return true if the item is successfully deleted, false when item is not successfully deleted
     */
    boolean deleteInventory(int inventoryID);
}
