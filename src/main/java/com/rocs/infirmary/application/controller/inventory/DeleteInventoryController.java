package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static java.awt.SystemColor.window;

public class DeleteInventoryController{
    @FXML
    private Label InventoryDeleteLabel_A;
    @FXML
    private Label InventoryDeleteLabel_B;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private Medicine medicine;
    private int inventoryId;
    public void showMedicine(String medicineName,int invetoryId,String medicineId){
        InventoryDeleteLabel_A.setText(medicineName);
        InventoryDeleteLabel_B.setText("Medicine ID: "+medicineId);
        this.inventoryId = invetoryId;
    }
    private void deleteMedicine(){
        boolean isDeleted = inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(inventoryId);
        if(isDeleted){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("Deleted successful!");
            alert.showAndWait();
        }
    }

    public void onConfirmButtonClick(ActionEvent actionEvent) {
        deleteMedicine();
    }

    public void onCancelBtnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
