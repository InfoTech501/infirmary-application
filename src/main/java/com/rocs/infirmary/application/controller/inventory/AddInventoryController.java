package com.rocs.infirmary.application.controller.inventory;

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
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
/**
 * {@code AddInventoryController} is used to handle event processes of the Inventory when adding new Items
 * this implements Initializable interface
 **/
public class AddInventoryController implements Initializable {
    @FXML
    private TableView<Medicine> medDetailsTable;
    @FXML
    private TableColumn<Medicine, Boolean> selectColumn;
    @FXML
    private TableColumn<Medicine, String> productNameColumn;
    @FXML
    private TableColumn<Medicine, Timestamp> expiryDateColumn;
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
    private ChoiceBox itemTypeChoicebox;

    private ObservableList<Medicine> medicine;
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    Medicine medicineModel = new Medicine();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
    }

    private void setup() {
        medDetailsTable.setEditable(true);

        selectColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setEditable(true);
        selectColumn.setStyle("-fx-alignment: CENTER;");

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        productNameColumn.setStyle("-fx-alignment: CENTER;");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setStyle("-fx-alignment: CENTER;");
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        setMedicineExpiration();
        expiryDateColumn.setStyle("-fx-alignment: CENTER;");

    }
    private void setMedicineExpiration(){
        expiryDateColumn.setCellFactory(expiryDateColumn -> new TableCell<Medicine, Timestamp>() {
            @Override
            protected void updateItem(Timestamp expirationDate, boolean empty) {
                super.updateItem(expirationDate, empty);
                if (empty || expirationDate == null) {
                    setText(null);
                } else {
                    LocalDate localDate = expirationDate.toLocalDateTime().toLocalDate();
                    setText(localDate.format(outputFormat));
                }
            }
        });
    }
    private void refresh() {
        List<Medicine> medicineList = inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicineFromMedicineTable();
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
        medDetailsTable.setItems(medicine);
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
    private boolean addMedicine() throws ParseException {
        boolean isAdded = false;
        boolean found = false;
        int quantity = Integer.parseInt(quantityTextField.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(String.valueOf(expirationDatePicker.getValue()));
        String productName = productNameTextField.getText().trim();
        if (!medicine.isEmpty()) {
            for (Medicine med : medicine) {
                if (med.getItemName().equalsIgnoreCase(productName)) {
                    found = true;
                    isAdded = inventoryManagementApplication.getMedicineInventoryFacade().addInventory(med.getMedicineId(), med.getItemType(), quantity, expirationDate);
                    break;
                }
            }
        }
        if (!found) {
            medicineModel.setItemName(productName);
            medicineModel.setDescription(descriptionTextField.getText());
            inventoryManagementApplication.getMedicineInventoryFacade().addMedicine(medicineModel);
            isAdded = addMedicineToInventory(quantity,"medicine",expirationDate);
        }
        return isAdded;
    }

    /**
     * this method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onConfirmBtnClick(ActionEvent actionEvent) throws ParseException {
        if(productNameTextField.getText()==null||productNameTextField.getText().isEmpty()|| productNameTextField.getText().isBlank()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Product Name cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }else if(quantityTextField.getText()==null||quantityTextField.getText().isEmpty()|| quantityTextField.getText().isBlank()){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Quantity cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        } else if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()||descriptionTextField.getText().isBlank()) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Description cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        } else if(expirationDatePicker.getValue()==null){
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("Expiration date cannot be empty");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
        else if (!isValidTextInput(productNameTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input In Product Name");
            alert.setContentText("Product Name must only contain letters.");
            alert.showAndWait();
        }
        else {
            if(addMedicine()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added");
                alert.showAndWait();
            }
        }
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
        }else {
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

}
