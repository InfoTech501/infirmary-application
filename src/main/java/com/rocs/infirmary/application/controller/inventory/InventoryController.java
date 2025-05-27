package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.InventoryManagementApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

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
        itemSearch();
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
        for (Medicine med : medicineList) {
            if (med.isSelectedProperty() == null) {
                med.setIsSelected(false);
            }
        }
        medicine = FXCollections.observableArrayList(medicineList);
        MedDetailsTable.setItems(medicine);
    }
    private void showModal(ActionEvent actionEvent,String location) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(location)));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }

    public void onShowAddModalBtnClick(ActionEvent actionEvent) throws IOException {
        showModal(actionEvent,"/views/InventoryAddItemModal.fxml");
    }
    private void itemSearch(){
        FilteredList<Medicine> filteredList = new FilteredList<>(medicine, b -> true);

        SearchTextField.textProperty().addListener((observable,oldValue , newValue)->
                        filteredList.setPredicate(medicine -> {
                            if(newValue.isEmpty()||newValue.isBlank()||newValue == null){
                                return true;
                            }
                            String searchKeyword = newValue.toLowerCase();
                            if(medicine.getItemName().toLowerCase().contains(searchKeyword)){
                                return true;
                            }
                            return false;
                        })
                );
        SortedList<Medicine> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(MedDetailsTable.comparatorProperty());
        MedDetailsTable.setItems(sortedList);
    }

    public void onFilterButtonAClick(ActionEvent actionEvent) {
        ProductNameColumn.setSortable(true);
        ProductNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        MedDetailsTable.getSortOrder().setAll(ProductNameColumn);
        MedDetailsTable.sort();

    }

    public void onFilterButtonZClick(ActionEvent actionEvent) {
        ProductNameColumn.setSortable(true);
        ProductNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        MedDetailsTable.getSortOrder().setAll(ProductNameColumn);
        MedDetailsTable.sort();
    }

    public void onClearFilterClick(ActionEvent actionEvent) {
        ProductNameColumn.setSortable(true);
        ProductNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        MedDetailsTable.getSortOrder().setAll(ProductNameColumn);
        MedDetailsTable.sort();
        SearchTextField.clear();
        refresh();
    }

    public void onQuantityFilterClick(ActionEvent actionEvent) {
        QuantityColumn.setSortable(true);
        QuantityColumn.setSortType(TableColumn.SortType.ASCENDING);
        MedDetailsTable.getSortOrder().setAll(QuantityColumn);
        MedDetailsTable.sort();
    }
}


