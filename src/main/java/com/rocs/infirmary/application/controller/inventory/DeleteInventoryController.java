package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DeleteInventoryController{
    @FXML
    private Label InventoryDeleteLabel_A;
    @FXML
    private Label InventoryDeleteLabel_B;
    @FXML
    private GridPane medicineListContainer;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private List<Medicine> medicineList = new ArrayList<>();

    public void showMedicineList(List<Medicine> selectedMedicines) {
        medicineListContainer.getChildren().clear();
        medicineListContainer.getRowConstraints().clear();
        medicineListContainer.getColumnConstraints().clear();

        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.SOMETIMES);
        column.setMinWidth(10.0);
        column.setPrefWidth(100.0);
        medicineListContainer.getColumnConstraints().add(column);

        int rowIndex = 0;
        for (Medicine med : selectedMedicines) {
            GridPane innerGrid = new GridPane();
            innerGrid.setPrefWidth(441);
            innerGrid.setPrefHeight(103);
            innerGrid.setHgap(10);
            innerGrid.setVgap(5);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHgrow(Priority.SOMETIMES);
            col1.setMaxWidth(219.67);
            col1.setMinWidth(10.0);
            col1.setPrefWidth(33.67);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHgrow(Priority.SOMETIMES);
            col2.setMaxWidth(446.3333377838135);
            col2.setMinWidth(10.0);
            col2.setPrefWidth(428.33);

            innerGrid.getColumnConstraints().addAll(col1, col2);

            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.SOMETIMES);
            row1.setMaxHeight(27.33);
            row1.setPrefHeight(20.0);

            RowConstraints row2 = new RowConstraints();
            row2.setVgrow(Priority.SOMETIMES);
            row2.setMaxHeight(68.0);
            row2.setPrefHeight(68.0);

            RowConstraints row3 = new RowConstraints();
            row3.setVgrow(Priority.SOMETIMES);
            row3.setMaxHeight(29.67);
            row3.setPrefHeight(14.33);

            innerGrid.getRowConstraints().addAll(row1, row2, row3);

            Label medLabel = new Label(med.getItemName());
            medLabel.setPrefWidth(220.0);
            medLabel.setPrefHeight(73.0);
            medLabel.setStyle("-fx-font-weight: 700; -fx-font-size: 30px;");

            GridPane.setColumnIndex(medLabel, 1);
            GridPane.setRowIndex(medLabel, 1);

            innerGrid.getChildren().add(medLabel);

            medicineListContainer.add(innerGrid, 0, rowIndex);

            RowConstraints outerRow = new RowConstraints();
            outerRow.setPrefHeight(103);
            outerRow.setVgrow(Priority.SOMETIMES);
            medicineListContainer.getRowConstraints().add(outerRow);
            medicineList.add(med);
            rowIndex++;
        }
    }
    private boolean deleteMedicine(){
        boolean deleted = false;
        for (Medicine med : medicineList) {
            deleted = inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(med.getInventoryId());
        }
        return deleted;
    }

    public void onConfirmButtonClick(ActionEvent actionEvent) {
        String medicineName = InventoryDeleteLabel_A.getText();
        if (!isValidString(medicineName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Medicine name must be a string.");
            alert.showAndWait();return;
        }
        boolean isDeleted = deleteMedicine();
        if(isDeleted){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("Deleted successfully!");
            alert.showAndWait();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void onCancelBtnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean isValidString(String input) {
        return input != null && input.matches("[a-zA-Z\\s]+");
    }
}
