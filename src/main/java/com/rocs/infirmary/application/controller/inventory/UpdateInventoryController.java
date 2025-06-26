package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
/**
 * {@code UpdateInventoryController} is used to handle event processes of the Inventory when updating Item attributes
 **/
public class UpdateInventoryController {
    @FXML
    private Label itemToEditLabel;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField expirationDateTextField;
    @FXML
    private TextField descriptionTextField;

    private String medicineId;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateInventoryController.class);
    private static final String prompt = "  Cannot be edit as of now due to conflicts";
    /**
     * this displays the attributes of the Item to be updated
     * @param medicine is a model that contains all attribute of the medicine
     **/
    public void showItemToEdit(Medicine medicine){
        LOGGER.info("Edit Inventory Controller started");
        itemToEditLabel.setText(medicine.getItemName());
        productNameTextField.setText(medicine.getItemName()+prompt);
        productNameTextField.setEditable(false);
        quantityTextField.setText(String.valueOf(medicine.getQuantity()));
        expirationDateTextField.setText(String.valueOf(medicine.getExpirationDate()));
        descriptionTextField.setText(medicine.getDescription());
        medicineId = medicine.getMedicineId();
    }

    private boolean updateMedicine()throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        boolean isUpdated = false;
        if(!productNameTextField.getText().isEmpty()||!quantityTextField.getText().isEmpty()||!expirationDateTextField.getText().isEmpty()){
            String inputDate = expirationDateTextField.getText();
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(inputDate);
            Date parseDate = new Date(date.getTime());

            isUpdated = inventoryManagementApplication.getMedicineInventoryFacade().updateMedicineInventory(medicineId,Integer.parseInt(quantityTextField.getText()),descriptionTextField.getText(), parseDate);
        }
        return isUpdated;
    }
    /**
     * this method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     * this method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onConfirmButtonClick(ActionEvent actionEvent) throws ParseException {
        LOGGER.warn("This action cannot be undone");
        if(productNameTextField.getText()==null || productNameTextField.getText().isEmpty()|| productNameTextField.getText().isBlank()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Product Name cannot be empty");
            LOGGER.warn("Product name field is empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }else if(quantityTextField.getText()==null||quantityTextField.getText().isEmpty()|| quantityTextField.getText().isBlank()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Quantity cannot be empty");
            LOGGER.warn("Quantity field is empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }else if(expirationDateTextField.getText()==null||expirationDateTextField.getText().isEmpty()|| expirationDateTextField.getText().isBlank()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Expiration date cannot be empty");
            LOGGER.warn("Expiration field is empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
        else if (!isValidTextInput(productNameTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input In Product Name");
            alert.setContentText("Product Name must only contain letters.");
            LOGGER.warn("Product name contains number or symbols");
            alert.showAndWait();
        }
        else {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setTitle("Update Confirmation");
            ButtonType okayButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDialog.setContentText("This action cannot be undone. Are you sure about this update?");
            confirmDialog.getDialogPane().getButtonTypes().addAll(okayButton,cancelButton);
            Optional<ButtonType> result = confirmDialog.showAndWait();

            if(result.isPresent() && result.get() == okayButton){
                if(updateMedicine()){
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Notification");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    dialog.setContentText("Updated Successfully!");
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                    if(type.getButtonData().isDefaultButton()){
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                        LOGGER.info("Exiting Update Inventory Modal");
                    }
                }
            }else{
                confirmDialog.close();
            }
        }
    }
    private boolean isValidTextInput(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }
}
