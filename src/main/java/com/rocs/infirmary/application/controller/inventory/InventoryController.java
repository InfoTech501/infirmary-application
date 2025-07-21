package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * {@code InventoryController} is used to handle event processes of the Inventory,
 * this implements Initializable interface
 **/
public class InventoryController implements Initializable {

    @FXML
    private TableView<Medicine> medDetailsTable;
    @FXML
    private TableColumn<Medicine, Boolean> selectColumn;
    @FXML
    private TableColumn<Medicine, String> productNameColumn;
    @FXML
    private TableColumn<Medicine, Integer> quantityColumn;
    @FXML
    private TableColumn<Medicine, Timestamp> expiryDateColumn;
    @FXML
    private TableColumn<Medicine,String> itemTypeColumn;
    @FXML
    private TextField searchTextField;
    private ObservableList<Medicine> medicine;
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private List<Medicine> medicineList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        itemSearch();
        initalizeEditClick();
    }

    private void setup() {
        medDetailsTable.setEditable(true);
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);
        selectColumn.setStyle("-fx-alignment: CENTER;");

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        productNameColumn.setStyle("-fx-alignment: CENTER;");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setStyle("-fx-alignment: CENTER;");
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        itemTypeColumn.setStyle("-fx-alignment: CENTER;");
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        setMedicineExpiration();
        expiryDateColumn.setStyle("-fx-alignment: CENTER;");

    }
    private void setMedicineExpiration(){
        expiryDateColumn.setCellFactory(expiryDateColumn -> new TableCell<Medicine, Timestamp>() {
            @Override
            protected void updateItem(Timestamp expirationDate, boolean empty) {
                if (empty || expirationDate == null) {
                    setText(null);
                } else {
                    LocalDate localDate = expirationDate.toLocalDateTime().toLocalDate();
                    setText(localDate.format(outputFormat));
                }
            }
        });
    }
    private void initalizeEditClick(){
        medDetailsTable.setRowFactory(t->{
            TableRow<Medicine>tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event->{
                if(!tableRow.isEmpty() && event.getClickCount() == 2){
                    Medicine selectedMedicine = tableRow.getItem();
                    try {
                        showEditInventory(selectedMedicine);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return tableRow;
        });
    }
    /**
     * this method handles the refresh functionality for inventory table
     ***/
    public void refresh() {
        List<Medicine> medicineList = inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicine();
        for (Medicine med : medicineList) {
            if (med.isSelectedProperty() == null) {
                med.setIsSelected(false);
            }
        }
        medicine = FXCollections.observableArrayList(medicineList);
        medDetailsTable.setItems(medicine);
    }
    private void showModal(ActionEvent actionEvent,String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();
        AddInventoryController controller = loader.getController();
        controller.setParentController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }
    private void showEditInventory(Medicine medicine) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InventoryEditItemModal.fxml"));
        Parent root = loader.load();
        UpdateInventoryController updateInventoryController = loader.getController();
        updateInventoryController.showItemToEdit(medicine);
        updateInventoryController.setParentController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    /**
     * this method handles the action triggered when the add new medicine button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onShowAddModalBtnClick(ActionEvent actionEvent) throws IOException {
        showModal(actionEvent,"/views/InventoryAddItemModal.fxml");
    }
    private void itemSearch(){
        FilteredList<Medicine> filteredList = new FilteredList<>(medicine, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue , newValue)->
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
        sortedList.comparatorProperty().bind(medDetailsTable.comparatorProperty());
        medDetailsTable.setItems(sortedList);
    }
    /**
     * this method handles the action triggered when the increment filter button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onFilterButtonAClick(ActionEvent actionEvent) {
        productNameColumn.setSortable(true);
        productNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        medDetailsTable.getSortOrder().setAll(productNameColumn);
        medDetailsTable.sort();

    }
    /**
     * this method handles the action triggered when the decrement filter button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onFilterButtonZClick(ActionEvent actionEvent) {
        productNameColumn.setSortable(true);
        productNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        medDetailsTable.getSortOrder().setAll(productNameColumn);
        medDetailsTable.sort();
    }
    private List<Medicine> getSelectedMedicines() {
        List<Medicine> selectedMedicine = medicine.stream()
                .filter(Medicine::isSelected)
                .toList();
        for(Medicine med: selectedMedicine){
            medicineList.add(med);
        }
        return selectedMedicine;
    }
    /**
     * this method handles the action triggered when the clear filter button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onClearFilterClick(ActionEvent actionEvent) {
        productNameColumn.setSortable(true);
        productNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        medDetailsTable.getSortOrder().setAll(productNameColumn);
        medDetailsTable.sort();
        searchTextField.clear();
        refresh();
        itemSearch();
    }
    /**
     * this method handles the action triggered when the filter by quantity button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onQuantityFilterClick(ActionEvent actionEvent) {
        quantityColumn.setSortable(true);
        quantityColumn.setSortType(TableColumn.SortType.ASCENDING);
        medDetailsTable.getSortOrder().setAll(quantityColumn);
        medDetailsTable.sort();
    }
    private boolean deleteMedicine(){
        return inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(medicineList);
    }
    /**
     * this method handles the action triggered when the remove button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onRemoveBtnClick(ActionEvent actionEvent) throws IOException {
        if(getSelectedMedicines().isEmpty()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("No Items selected");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
        if(getSelectedMedicines().size() >= 2){
            List<Medicine> selectedMedicine = getSelectedMedicines();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InventoryDeleteItemModal.fxml"));
            Parent root = loader.load();
            DeleteInventoryController deleteInventoryController = loader.getController();
            deleteInventoryController.showMedicineList(selectedMedicine);
            deleteInventoryController.setParentController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }
        if(getSelectedMedicines().size() == 1 ) {
            deleteMedicine();
            Dialog dialog = new Dialog();
            dialog.setTitle("Notification");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Deleted Successfully!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            if(type.getButtonData().isDefaultButton()){
                refresh();
                itemSearch();
            }
        }
    }

}


