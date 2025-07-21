package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;

/**
 * {@code AddInventoryController} is used to handle event processes of the Inventory when adding new Items
 * this implements Initializable interface
 **/
public class AddInventoryController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddInventoryController.class);
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
    private ObservableList<String> itemType;
    private List<Medicine> medicineList = new ArrayList<>();
    private Medicine medicineModel = new Medicine();
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
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
    }
    private void refresh() {
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
    private boolean addMedicine(int quantity) throws ParseException {
        boolean isAdded = false;
        boolean found = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(String.valueOf(expirationDatePicker.getValue()));
        String productName = productNameTextField.getText().trim();
        try {
            if (!medicine.isEmpty()) {
                for (Medicine med : medicine) {
                    if (med.getItemName().equalsIgnoreCase(productName)) {
                        found = true;
                        Optional<ButtonType> result = ControllerHelper.alertAction("Add Confirmation", "The Medicine is Already Exist in the Medicine table do you want to add to inventory?");
                        if(result.isPresent()&& result.get().getButtonData() == ButtonBar.ButtonData.YES){
                            if(inventoryManagementApplication.getMedicineInventoryFacade().addInventory(med.getMedicineId(), itemTypeComboBox.getSelectionModel().getSelectedItem().toString(), quantity, expirationDate)){
                                isAdded = true;
                                showDialog("Notification","Item successfully added check your inventory ");
                            }
                            break;
                        }

                    }
                }
            }
            if (!found) {
                medicineModel.setItemName(productName);
                medicineModel.setDescription(descriptionTextField.getText());
                if(inventoryManagementApplication.getMedicineInventoryFacade().addMedicine(medicineModel)){
                    refresh();
                    Optional<ButtonType> result = ControllerHelper.alertAction("Add Confirmation", "The Medicine is Already Added in the Medicine table do you want to add to inventory?");
                    if(result.isPresent()&& result.get().getButtonData() == ButtonBar.ButtonData.YES){
                        if(addMedicineToInventory(quantity, itemTypeComboBox.getSelectionModel().getSelectedItem().toString(), expirationDate)){
                            isAdded = true;
                            showDialog("Notification","Item successfully added check your inventory ");
                        }
                    }else{
                        isAdded = true;
                        showDialog("Notification","Medicine successfully added");
                    }

                }
            }
        }catch (InputMismatchException ime){
            LOGGER.error("Invalid user input"+ime);
        }
        return isAdded;
    }
    private void showMedicineToEdit(Medicine medicine) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MedicineEditModal.fxml"));
        Parent root = loader.load();
        UpdateMedicineController updateMedicineController = loader.getController();
        updateMedicineController.showMedicineToEdit(medicine);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
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
        } else if(expirationDatePicker.getValue()==null){
            showDialog("warning","Expiration date cannot be empty");
        } else if (itemTypeComboBox.getSelectionModel().getSelectedItem().toString() == null||itemTypeComboBox.getSelectionModel().getSelectedItem().toString().isEmpty()||itemTypeComboBox.getSelectionModel().getSelectedItem().toString().isBlank()) {
            showDialog("warning","Item type cannot be empty");
        } else if (!isValidTextInput(productNameTextField.getText())) {
            showDialog("Invalid Input","Product Name must only contain letters.");
        } else if (!isValidInputNumber(quantityTextField.getText())) {
            showDialog("Invalid Input","Quantity must only contain number");
        } else {
            addMedicine(Integer.parseInt(quantityTextField.getText()));
        }
    }
    private boolean deleteMedicine(){
        return inventoryManagementApplication.getMedicineInventoryFacade().deleteMedicineByItemName(medicineList);
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean isValidTextInput(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }
    private boolean isValidInputNumber(String input) {
        return input.matches("^[0-9]+");
    }

}
