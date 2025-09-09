package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
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
import java.util.Optional;

import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;
/**
 * {@code UpdateMedicineController} is used to handle event processes of the Medicine table when updating Medicine attributes
 **/
public class UpdateMedicineController {
    @FXML
    private StackPane medicineEditModal;
    @FXML
    private Label itemToEditLabel;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextArea descriptionTextField;
    private Long medicineId;
    private AddInventoryController parentController;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateInventoryController.class);
    /**
     * this displays the attributes of the Item to be updated
     * @param medicine is a model that contains all attribute of the medicine
     **/
    public void showMedicineToEdit(Medicine medicine){
        LOGGER.info("Edit Medicine Controller started");
        itemToEditLabel.setText(medicine.getItemName());
        productNameTextField.setText(medicine.getItemName());

        descriptionTextField.setText(medicine.getDescription());
        medicineId = medicine.getMedicineId();
    }

    private boolean updateMedicine()throws ParseException {
        boolean isUpdated = false;
        if (productNameTextField.getText() != null && descriptionTextField.getText() != null && !productNameTextField.getText().isEmpty() && !descriptionTextField.getText().isEmpty()) {
            isUpdated = inventoryManagementApplication.getMedicineInventoryFacade().updateMedicine(medicineId,productNameTextField.getText(), descriptionTextField.getText());
        }
        return isUpdated;
    }
    /**
     * this method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onCancelButtonClick(ActionEvent actionEvent) {
        if(parentController != null){
            parentController.refresh();
        }
        medicineEditModal.setVisible(false);
        medicineEditModal.setDisable(true);
        medicineEditModal.getChildren().clear();
        LOGGER.info("Exiting Update Medicine Modal");
    }
    /**
     * this method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onConfirmButtonClick(ActionEvent actionEvent) throws ParseException {
        LOGGER.warn("This action cannot be undone");
       try {
           if(productNameTextField.getText()==null || productNameTextField.getText().isEmpty()|| productNameTextField.getText().isBlank()){
               LOGGER.warn("Product name field is empty");
               showDialog("Warning","Product Name cannot be empty");
           } else if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty() || descriptionTextField.getText().isBlank()) {
               showDialog("Warning","Description cannot be empty");
           } else if (!isValidTextInput(productNameTextField.getText())) {
               showDialog("Invalid Input","Product Name must only contain letters.");
           }
           else {
               Optional<ButtonType> result = ControllerHelper.alertAction("Update Confirmation", "This action cannot be undone. Are you sure about this update?");
               if (result.isPresent()&& result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                   if (updateMedicine()) {
                       ControllerHelper.showDialog("Notification", "Updated Successfully!");
                        if(parentController != null){
                            parentController.refresh();
                        }
                       Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                       LOGGER.info("Exiting Update Medicine Modal");
                       stage.close();
                   }
               }
           }
       }catch (NullPointerException e){
           LOGGER.error("NullPointerException Occurred "+e);
       }
    }
    private boolean isValidTextInput(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }
    /**
     * this method setup's the parent controller
     * @param parentController the parent AddInventoryController instance to be associated with this controller
     * */
    public void setParentController(AddInventoryController parentController) {
        this.parentController = parentController;
    }
}
