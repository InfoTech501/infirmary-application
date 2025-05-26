package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddInventoryController implements Initializable {
    @FXML
    private TableView<Medicine> MedDetailsTable;
    @FXML
    private TableColumn<Medicine, Boolean> SelectColumn;
    @FXML
    private TableColumn<Medicine, String> ProductNameColumn;
    @FXML
    private TableColumn<Medicine, Integer> QuantityColumn;
    @FXML
    private TableColumn<Medicine, String> ExpiryDateColumn;
    @FXML
    private TextField SearchTextField;

    private ObservableList<Medicine> medicine;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
    }

    private void setup() {
        SelectColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        SelectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(SelectColumn));
        SelectColumn.setEditable(true);
        SelectColumn.setStyle("-fx-alignment: CENTER;");

        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        ProductNameColumn.setStyle("-fx-alignment: CENTER;");
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        QuantityColumn.setStyle("-fx-alignment: CENTER;");
        ExpiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        ExpiryDateColumn.setStyle("-fx-alignment: CENTER;");

    }

    private void refresh() {
        List<Medicine> medicineList = inventoryManagementApplication.getMedicineInventoryFacade().findAllMedicine();
        medicine = FXCollections.observableArrayList(medicineList);
        MedDetailsTable.setItems(medicine);
    }
}
