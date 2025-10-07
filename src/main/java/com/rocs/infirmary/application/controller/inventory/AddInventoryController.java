package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.rocs.infirmary.application.controller.helper.ControllerHelper.alertAction;
import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;

/**
 * {@code AddInventoryController} is used to handle event processes of the Inventory when adding new Items
 * this implements Initializable interface
 **/
public class AddInventoryController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddInventoryController.class);
    @FXML
    private StackPane inventoryAddItemModal;
    @FXML
    private TableView<Medicine> medDetailsTable;
    @FXML
    private TableColumn<Medicine, Boolean> selectColumn;
    @FXML
    private TableColumn<Medicine, String> productNameColumn;
    @FXML
    private TableColumn<Medicine, String> descriptionColumn;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private ComboBox itemTypeComboBox;

    private ObservableList<Medicine> medicine;
    private List<Medicine>inventoryItem;
    private ObservableList<String> itemType;
    private List<Medicine> medicineList = new ArrayList<>();
    private Medicine medicineModel = new Medicine();
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private InventoryController parentController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        medicineAutofill();
        initalizeEditClick();
    }

    private void setup() {
        medDetailsTable.setEditable(true);

        selectColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);
        selectColumn.setStyle("-fx-alignment: CENTER;");

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        itemTypeComboBox.setItems(itemType);

        setupSelectColumn();
    }

    private void setupSelectColumn() {
        selectColumn.setCellFactory(tc -> {
            CheckBoxTableCell<Medicine, Boolean> cell = new CheckBoxTableCell<Medicine, Boolean>() {
                @Override
                public void updateItem(Boolean selected, boolean empty) {
                    parentUpdate(selected, empty);

                    if (!empty) {
                        Medicine med = getTableView().getItems().get(getIndex());
                        med.isSelectedProperty().addListener((obs, wasSelected, isSelected) -> {
                            if (isSelected) {
                                productNameTextField.setText(med.getItemName());
                                descriptionTextField.setText(med.getDescription());
                                expirationDatePicker.setValue(null);
                                quantityTextField.clear();

                            } else {

                                productNameTextField.clear();
                                descriptionTextField.clear();
                                expirationDatePicker.setValue(null);
                                quantityTextField.clear();
                            }
                        });
                    }
                }

                private void parentUpdate(Boolean selected, boolean empty) {
                    super.updateItem(selected, empty);
                }
            };
            return cell;
        });
    }

    /**
     * this method handles the refresh functionality for medicine table
     ***/
    public void refresh() {
        List<Medicine> medicineList = inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicineFromMedicineTable();
        String[] itemTypeList = {"No selection","Medicine", "Non expiry", "other"};
        for (Medicine med : medicineList) {
            if (med.isSelectedProperty() == null) {
                med.setIsSelected(false);
                System.out.println(med.isSelected());
            }
            if(!med.isSelected()){
                medicineModel.setHasSelect(false);
            }else{
                medicineModel.setHasSelect(true);
                System.out.println(med.isSelected());
            }
        }
        medicine = FXCollections.observableArrayList(medicineList);
        itemType = FXCollections.observableArrayList(itemTypeList);
        inventoryItem = FXCollections.observableArrayList(inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicine());
        medDetailsTable.setItems(medicine);
        itemTypeComboBox.setItems(itemType);
    }
    private void initalizeEditClick(){
        medDetailsTable.setRowFactory(t->{
            TableRow<Medicine>tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event->{
                if(!tableRow.isEmpty() && event.getClickCount() == 2){
                    Medicine selectedMedicine = tableRow.getItem();
                    try {
                        showMedicineToEdit(selectedMedicine);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return tableRow;
        });
    }
    /**
     * This method retrieves a list of medicines that are marked as selected.
     * This method filters the medicine list and returns only those medicine that appeared selected,
     * this happens when {@code isSelected} returns {@code true}
     * @return a list of selected {@code Medicine} objects
     */
    public List<Medicine> getSelectedMedicines() {
        List<Medicine> selectedMedicine = medicine.stream()
                .filter(Medicine::isSelected)
                .toList();
        for(Medicine med: selectedMedicine){
            medicineList.add(med);
        }
        return selectedMedicine;
    }
    private boolean addMedicineToInventory(int quantity, String itemType,Date expirationDate){
        refresh();
        if(!medicine.isEmpty()){
            for(Medicine med:medicine){
                if(med.getItemName().equalsIgnoreCase(productNameTextField.getText())){
                    return inventoryManagementApplication.getMedicineInventoryFacade().addInventory(med.getMedicineId(), itemType, quantity, expirationDate);
                }
            }
        }
        return false;
    }
    private void medicineAutofill(){
        FilteredList<Medicine> filteredList = new FilteredList<>(medicine, b -> true);

        productNameTextField.textProperty().addListener((observable, oldValue , newValue)->
                filteredList.setPredicate(medicine -> {
                    if(newValue.isEmpty()||newValue.isBlank()||newValue == null){
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                     if(medicine.getItemName().toLowerCase().contains(searchKeyword)){
                        descriptionTextField.setText(medicine.getDescription());
                        return true;
                    }
                    return false;
                })
        );
    }
    private boolean addMedicine(int quantity) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = null;

        if (!itemTypeComboBox.getSelectionModel().getSelectedItem().equals("Non expiry")
                && expirationDatePicker.getValue() == null) {
            showDialog("Warning", "Expiration date is required unless item is Non expiry");
            return false;
        }

        if (!itemTypeComboBox.getSelectionModel().getSelectedItem().equals("Non expiry")) {
            expirationDate = dateFormat.parse(String.valueOf(expirationDatePicker.getValue()));
        }

        String productName = productNameTextField.getText();

        if (productName == null || productName.trim().isEmpty()) {
            showDialog("Warning", "No medicine selected to add.");
            return false;
        }

        try {
           Medicine existingMedicine = medicine.stream().filter(med -> med.getItemName().equalsIgnoreCase(productName)).findFirst().orElse(null);
            if (existingMedicine != null) {
                Date finalExpirationDate = expirationDate;
                Medicine existingInventoryItem = inventoryItem.stream()
                        .filter(item -> {
                            try{
                                if (item.getExpirationDate() != null && finalExpirationDate != null) {
                                    return item.getItemName().equalsIgnoreCase(productName) &&
                                            dateFormat.parse(item.getExpirationDate().toString()).equals(finalExpirationDate);
                                }
                                if (item.getExpirationDate() == null && finalExpirationDate == null) {
                                    return item.getItemName().equalsIgnoreCase(productName) &&
                                            item.getItemType().equalsIgnoreCase(existingMedicine.getItemType());
                                }
                                return false;
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .findFirst()
                        .orElse(null);

                if (existingInventoryItem != null) {
                    Optional<ButtonType> confirmUpdate = alertAction("Update Confirmation", "The medicine with the same name and expiration date exists in inventory. Do you want to update the quantity instead?");
                    if (confirmUpdate.isPresent() && confirmUpdate.get().getButtonData() == ButtonBar.ButtonData.YES) {
                        int updatedQuantity = existingInventoryItem.getQuantity() + quantity;
                        boolean updated = inventoryManagementApplication
                                .getMedicineInventoryFacade()
                                .updateMedicineInventory(
                                        existingInventoryItem.getInventoryId(),
                                        existingInventoryItem.getMedicineId(),
                                        updatedQuantity,
                                        itemTypeComboBox.getSelectionModel().getSelectedItem().toString(),
                                        expirationDate
                                );
                        if (updated) {
                            showDialog("Notification", "Quantity successfully updated in inventory.");
                            refresh();
                            return true;
                        }
                    }
                    return false;
                } else {
                    boolean inserted = inventoryManagementApplication
                            .getMedicineInventoryFacade()
                            .addInventory(
                                    existingMedicine.getMedicineId(),
                                    itemTypeComboBox.getSelectionModel().getSelectedItem().toString(),
                                    quantity,
                                    expirationDate
                            );
                    refresh();
                    if (inserted) {
                        showDialog("Notification", "Item successfully added. Check your inventory.");
                        refresh();
                        return true;
                    }
                }

            }
            medicineModel.setItemName(productName);
            medicineModel.setDescription(descriptionTextField.getText());
            boolean medAdded = inventoryManagementApplication.getMedicineInventoryFacade().addMedicine(medicineModel);
            if (medAdded) {
                refresh();
                Optional<ButtonType> result = alertAction(
                        "Add Confirmation",
                        "Medicine added to the table. Do you want to add it to inventory?"
                );
                if (result.isPresent()&&result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                    refresh();
                    if (addMedicineToInventory(quantity, itemTypeComboBox.getSelectionModel().getSelectedItem().toString(), expirationDate)) {
                        showDialog("Notification", "Item successfully added. Check your inventory.");
                        return true;
                    }
                }
                return true;
            }

        } catch (InputMismatchException ime) {
            LOGGER.error("Invalid user input: " + ime);
        }
        return false;
    }
    private void showMedicineToEdit(Medicine medicine) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MedicineEditModal.fxml"));
        loader.setControllerFactory(param -> new UpdateMedicineController());
        Parent root = loader.load();

        UpdateMedicineController controller = loader.getController();
        controller.showMedicineToEdit(medicine);
        controller.setParentController(this);
        inventoryAddItemModal.getChildren().add(root);
    }
    /**
     * this method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onConfirmBtnClick(ActionEvent actionEvent) throws ParseException {
        String parsedQuantity  = quantityTextField.getText().trim();
        if(productNameTextField.getText()==null||productNameTextField.getText().isEmpty()|| productNameTextField.getText().isBlank()){
            showDialog("warning","Product Name cannot be empty");
        }else if(quantityTextField.getText()==null||quantityTextField.getText().isEmpty()|| quantityTextField.getText().isBlank()){
            showDialog("warning","Quantity cannot be empty");
        }else if (!parsedQuantity.matches("^\\d{1,10}$")) {
            showDialog("Warning", "Quantity must be a whole number between 0 and " + Integer.MAX_VALUE);
            LOGGER.warn("Invalid quantity input: " + parsedQuantity);
        }else if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()||descriptionTextField.getText().isBlank()) {
            showDialog("warning","Description cannot be empty");
        }else if (!isValidTextInput(descriptionTextField.getText())) {
            showDialog("Invalid Input", "Description must only contain letters and spaces.");
        }else if (!"Non expiry".equals(itemTypeComboBox.getSelectionModel().getSelectedItem().toString())
            && expirationDatePicker.getValue() == null) {
        showDialog("warning","Expiration date cannot be empty");
        } else if (itemTypeComboBox.getSelectionModel().getSelectedItem().toString() == null||itemTypeComboBox.getSelectionModel().getSelectedItem().toString().isEmpty()||itemTypeComboBox.getSelectionModel().getSelectedItem().toString().isBlank()) {
            showDialog("warning","Item type cannot be empty");
        } else if (!isValidTextInput(productNameTextField.getText())) {
            showDialog("Invalid Input","Product Name must only contain letters.");
        } else if (!isValidInputNumber(quantityTextField.getText())) {
            showDialog("Invalid Input","Quantity must only contain number");
        } else {

            Optional<ButtonType> confirmAction = alertAction(
                    "Add Confirmation",
                    "Are you sure you want to add this medicine to inventory?\n\n"
                            + "Product: " + productNameTextField.getText() + "\n"
                            + "Quantity: " + quantityTextField.getText() + "\n"
                            + "Item Type: " + itemTypeComboBox.getSelectionModel().getSelectedItem() + "\n"
                            + "Expiration Date: " + expirationDatePicker.getValue()
            );
            if (confirmAction.isPresent() && confirmAction.get().getButtonData() == ButtonBar.ButtonData.YES) {
                if (addMedicine(Integer.parseInt(quantityTextField.getText()))) {
                    if (parentController != null) {
                        parentController.refresh();
                    }
                }
            } else {
                LOGGER.info("Canceled the add operation.");
            }
        }
    }
    private List<Medicine> findMatchingMedicineFromInventory(List<Medicine> selected, List<Medicine> inventoryMedicine) {
        List<Long> selectedIds = selected.stream()
                .map(Medicine::getMedicineId)
                .toList();

        return inventoryMedicine.stream()
                .filter(med -> selectedIds.contains(med.getMedicineId()))
                .collect(Collectors.toList());
    }
    private boolean deleteMedicines() {
        refresh();
        List<Medicine> medicinesToDelete = findMatchingMedicineFromInventory(medicineList, inventoryItem);

        boolean medicineDeleted = inventoryManagementApplication
                .getMedicineInventoryFacade()
                .deleteMedicineByItemName(medicineList);

        boolean inventoryDeleted = inventoryManagementApplication
                .getMedicineInventoryFacade()
                .deleteInventory(medicinesToDelete);

        parentController.refresh();
        return medicineDeleted && inventoryDeleted;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MedicineDeleteItemModal.fxml"));
            Parent root = loader.load();
            DeleteMedicineController deleteMedicineController = loader.getController();
            deleteMedicineController.showMedicineList(selectedMedicine);
            deleteMedicineController.setParentController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }
        if(getSelectedMedicines().size() == 1 ) {
            deleteMedicines();
            Dialog dialog = new Dialog();
            dialog.setTitle("Notification");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Deleted Successfully!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            if(type.getButtonData().isDefaultButton()){
                refresh();
            }
        }
    }

    @FXML
    private void onSelectData(){
        medicineModel = medDetailsTable.getSelectionModel().getSelectedItem();
        int selectedRow = medDetailsTable.getSelectionModel().getSelectedIndex();
        if(selectedRow < 0){
            return;
        }
        productNameTextField.setText(medicineModel.getItemName());
        productNameTextField.setStyle("-fx-alignment: LEFT;");
        descriptionTextField.setText(medicineModel.getDescription());
        descriptionTextField.setStyle("-fx-alignment:LEFT;");

    }
    /**
     * this method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onCancelBtnClick(ActionEvent actionEvent) throws IOException {
        if (parentController != null) {
            parentController.refresh();
        }
        inventoryAddItemModal.setVisible(false);
        inventoryAddItemModal.setDisable(true);
        inventoryAddItemModal.getChildren().clear();
    }
    private boolean isValidTextInput(String input) {
        return input.matches("[a-zA-Z\\s,\\.]+");
    }

    private boolean isValidInputNumber(String input) {
        return input.matches("^[0-9]+");
    }
    /**
     * this method setup's the parent controller
     * @param parentController the parent InventoryController instance to be associated with this controller
     * */
    public void setParentController(InventoryController parentController) {
        this.parentController = parentController;
    }
    /**
     * this method retrieve's the parent controller instance to be associated with other controllers.
     * @return the parent InventoryController instance
     */
    public InventoryController getParentController() {
        return parentController;
    }
}
