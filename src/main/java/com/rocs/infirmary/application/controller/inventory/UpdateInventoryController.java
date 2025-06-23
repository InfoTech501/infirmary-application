package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.InventoryManagementApplication;
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

public class UpdateInventoryController {
    @FXML
    private Label ItemToEditLabel;
    @FXML
    private TextField ProductNameTextField;
    @FXML
    private TextField QuantityTextField;
    @FXML
    private TextField ExpirationDateTextField;
    private String medicineId;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private final Logger LOGGER = LoggerFactory.getLogger("UpdateInventoryController");

    public void showItemToEdit(Medicine medicine){
        LOGGER.info("Edit Inventory Controller");
        ItemToEditLabel.setText(medicine.getItemName());
        ProductNameTextField.setText(medicine.getItemName()+" Cannot be edit as of now due to conflicts");
        ProductNameTextField.setEditable(false);
        QuantityTextField.setText(String.valueOf(medicine.getQuantity()));
        ExpirationDateTextField.setText(String.valueOf(medicine.getExpirationDate()));
        medicineId = medicine.getMedicineId();
    }

    private boolean updateMedicine()throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        boolean isUpdated = false;
        if(!ProductNameTextField.getText().isEmpty()||!QuantityTextField.getText().isEmpty()||!ExpirationDateTextField.getText().isEmpty()){
            String inputDate = ExpirationDateTextField.getText();
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(inputDate);
            java.sql.Date parseDate = new java.sql.Date(date.getTime());

            isUpdated = inventoryManagementApplication.getMedicineInventoryFacade().updateMedicineInventory(medicineId,Integer.parseInt(QuantityTextField.getText()),null,parseDate );
        }
        return isUpdated;
    }
    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void onConfirmButtonClick(ActionEvent actionEvent) throws ParseException {
        if(ProductNameTextField.getText().isEmpty()||ProductNameTextField.getText().isBlank()||ProductNameTextField.getText()==null){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Product Name cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }else if(QuantityTextField.getText().isEmpty()||QuantityTextField.getText().isBlank()||QuantityTextField.getText()==null){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Quantity cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }else if(ExpirationDateTextField.getText().isEmpty()||ExpirationDateTextField.getText().isBlank()||ExpirationDateTextField.getText()==null){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Expiration date cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
        else if (!isValidTextInput(ProductNameTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input In Product Name");
            alert.setContentText("Product Name must only contain letters.");
            alert.showAndWait();
        }
        else {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setTitle("Update Confirmation");
            ButtonType okayButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDialog.setContentText("Are you sure about this update?");
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
