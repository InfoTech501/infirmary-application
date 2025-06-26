package com.rocs.infirmary.application.app.facade.medicine.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;

import java.util.Date;
import java.util.List;

/**
 * {@code MedicineInventoryFacade} is used to facilitate medicine-related operations within the system.
 * this handles operations such as retrieving, adding, updating, and deleting medicines and inventory records.
 **/
public interface MedicineInventoryFacade {
    /**
     *Displays all available medical supplies and details.
     * Displays a list of Inventory Items(medicine) with details such as medicine name, stocks and Expiration date.
     */
    List<Medicine> getAllMedicine();
    /**
     * This used for the delete functionality of medicine
     * @param itemName the name of the medicine to be removed must correspond to an existing entry
     * @return true if the medicine was successfully deleted false otherwise
     */
    boolean deleteMedicineByItemName(String itemName);
    /**
     * Checks whether a specific medicine is available in the inventory.
     * @param itemName the name of the medicine to check
     * @return true if the medicine exists; false otherwise
     */
    boolean IsAvailable(String itemName);
    /**
     * Adds a new medicine to the system.
     * @param newMedicine the medicine object containing all necessary attributes
     * @return true if the medicine was successfully added; false otherwise
     */
    boolean addMedicine(Medicine newMedicine);
    /**
     * Updates the inventory by adding a specific quantity of an existing medicine.
     * @param medicineId the unique identifier of the medicine
     * @param itemType the type of the Item to be added in the inventory
     * @param quantity the quantity to be added to the inventory
     * @return true if the inventory was successfully updated; false otherwise
     **/
    boolean addInventory(String medicineId , String itemType, int quantity );
    /**
     * This display all the medicine retrieved From the Medicine Table
     * @return a List of Medicine that is available on the database
     **/
    List<Medicine> getAllMedicineFromMedicineTable();
    /**
     * Deletes a specific inventory record using its unique identifier.
     * @param inventoryId the ID of the inventory item to be deleted
     * @return true if the inventory record was successfully deleted false if not
     **/
    boolean deleteInventory(int inventoryId);
    /**
     * this is used to update the medicine attributes in the inventory
     * @param medicineId is a unique identifier that is used to specify the item to be updated
     * @param quantity is used to specify the quantity of the item being added
     * @param description is used to specify what type of item are going to be added on the item table
     * @param expirationDate is used to specify the medicine expiration date
     * @return true if the medicine is successfully updated and false if not
     **/
    boolean updateMedicineInventory(String medicineId, int quantity, String description, Date expirationDate);
}
