package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.controller.lowstock.helper.LowStockAlertHelper;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;

/**
 * {@code UpdateInventoryController} is used to handle event processes of the Inventory when updating Item attributes
 **/
public class UpdateInventoryController {
    @FXML
    private StackPane inventoryEditItemModal;
    @FXML
    private Label itemToEditLabel;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private ComboBox itemTypeComboBox;

    private String defaultItemType;
    private Long medicineId;
    private Long inventoryId;
    private Date expirationDate;

    private InventoryController parentController;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateInventoryController.class);
    private static final String PROMPT = "  (Product Name Cannot be edited here)";
    /**
     * this displays the attributes of the Item to be updated
     * @param medicine is a model that contains all attribute of the medicine
     **/
    public void showItemToEdit(Medicine medicine){
        String[] itemTypeList = {"No selection","Medicine", "Non expiry", "other"};
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        ObservableList<String> itemType;
        LOGGER.info("Edit Inventory Controller started");

        defaultItemType = medicine.getItemType();
        medicineId = medicine.getMedicineId();
        inventoryId = medicine.getInventoryId();

        itemToEditLabel.setText(medicine.getItemName());
        productNameTextField.setText(medicine.getItemName()+ PROMPT);
        productNameTextField.setEditable(false);
        quantityTextField.setText(String.valueOf(medicine.getQuantity()));

        if (medicine.getExpirationDate() != null && !medicine.getItemType().equals("Non expiry")) {
            LocalDate localDate = medicine.getExpirationDate().toLocalDateTime().toLocalDate();
            expirationDatePicker.setPromptText(localDate.format(outputFormat));
            expirationDate = java.sql.Date.valueOf(localDate);
        } else {
            expirationDatePicker.setPromptText("No Expiration Date Available");
            expirationDatePicker.setDisable(true);
        }
        itemType = FXCollections.observableArrayList(itemTypeList);
        itemTypeComboBox.setItems(itemType);
        itemTypeComboBox.setPromptText(defaultItemType);

    }
    /**
     * this method handles the update functionality.
     * @param quantity the new quantity to update for the medicine
     * @return {@code true} if the medicine was successfully updated; {@code false} if not updated
     * @throws ParseException if there is an error parsing the expiration date or other date fields
     */
    public boolean updateMedicine(int quantity)throws ParseException{
        boolean isUpdated = false;
        String itemType = defaultItemType;
        try {
            if (productNameTextField != null && quantityTextField != null && expirationDatePicker != null && itemTypeComboBox != null && productNameTextField.getText() != null && !productNameTextField.getText().isBlank()&& !quantityTextField.getText().isBlank()) {

                Date newSelectedExpirationDate = expirationDate;
                if (expirationDatePicker.getValue() != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    simpleDateFormat.setLenient(false);
                    String inputDate = expirationDatePicker.getValue().toString();
                    Date parsedDate = simpleDateFormat.parse(inputDate);
                    newSelectedExpirationDate = new Date(parsedDate.getTime());
                }
                if (itemTypeComboBox.getSelectionModel().getSelectedItem() != null) {
                    itemType = itemTypeComboBox.getSelectionModel().getSelectedItem().toString();
                }
                try {
                    isUpdated = inventoryManagementApplication.getMedicineInventoryFacade().updateMedicineInventory(inventoryId, medicineId, quantity, itemType, newSelectedExpirationDate);
                } catch (NumberFormatException e) {
                    showDialog("Warning","Invalid quantity");
                    LOGGER.error("Invalid quantity");
                }
            }
        }catch (NullPointerException e){
            LOGGER.error("NullPointerException Occurred "+e);
        }
        return isUpdated;
    }
    /**
     * this method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onCancelButtonClick(ActionEvent actionEvent) {
        if (parentController != null) {
            parentController.refresh();
        }
        LOGGER.info("Exiting Update Inventory Modal");
        inventoryEditItemModal.setVisible(false);
        inventoryEditItemModal.setDisable(true);
        inventoryEditItemModal.getChildren().clear();
    }
    /**
     * this method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onConfirmButtonClick(ActionEvent actionEvent) throws ParseException {
        LOGGER.warn("This action cannot be undone");
        String parsedQuantity  = quantityTextField.getText().trim();
        try {
            if(productNameTextField.getText()==null || productNameTextField.getText().isEmpty()|| productNameTextField.getText().isBlank()){
                LOGGER.warn("Product name field is empty");
                showDialog("Warning","Product Name cannot be empty");
            }else if(quantityTextField.getText()==null||quantityTextField.getText().isEmpty()|| quantityTextField.getText().isBlank()){
                LOGGER.warn("Quantity field is empty");
                showDialog("Warning","Quantity cannot be empty");
            } else if (!parsedQuantity.matches("^\\d{1,10}$")) {
                showDialog("Warning", "Quantity must be a whole number between 0 and " + Integer.MAX_VALUE);
                LOGGER.warn("Invalid quantity input: " + parsedQuantity);
            }else if(expirationDatePicker.getPromptText() == null && expirationDatePicker.getValue() == null && expirationDatePicker == null && expirationDate == null){
                LOGGER.warn("Expiration field is empty");
                showDialog("Warning","Expiration date cannot be empty");
            } else if (itemTypeComboBox.getSelectionModel().getSelectedItem() == null && defaultItemType == null || defaultItemType.isEmpty()) {
                showDialog("Warning","please select item type");
            } else {
                Optional<ButtonType> result = ControllerHelper.alertAction("Update Confirmation", "This action cannot be undone. Are you sure about this update?");
                if (result.isPresent()&& result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                    if (updateMedicine(Integer.parseInt(quantityTextField.getText()))) {
                        ControllerHelper.showDialog("Notification", "Updated Successfully!");
                        if (parentController != null) {
                            parentController.refresh();
                        }
                        LOGGER.info("Exiting Update Inventory Modal");
                        inventoryEditItemModal.setVisible(false);
                        inventoryEditItemModal.setDisable(true);
                        inventoryEditItemModal.getChildren().clear();
                    }
                }
            }
        }catch (NullPointerException e){
            LOGGER.error("NullPointerException Occurred "+e);
        }
    }
    /**
     * this method setup's the parent controller
     * @param parentController the parent InventoryController instance to be associated with this controller
     * */
    public void setParentController(InventoryController parentController) {
        this.parentController = parentController;
    }
}
