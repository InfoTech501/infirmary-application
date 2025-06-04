package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.InventoryManagementApplication;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private TextField ProductNameTextField;
    @FXML
    private TextField QuantityTextField;
    @FXML
    private TextField ExpirationDateTextField;

    private ObservableList<Medicine> medicine;

    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    Medicine medicineModel = new Medicine();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
    }

    private void setup() {
        MedDetailsTable.setEditable(true);

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
        List<Medicine> medicineList; medicineList = inventoryManagementApplication.getMedicineInventoryFacade().findAllMedicine();
        for (Medicine med : medicineList) {
            if (med.isSelectedProperty() == null) {
                med.setIsSelected(false);
                System.out.println(med.isSelected());
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
    private String getMedicineId(TextField textField){
        String itemName = textField.getText();
        String[] words = itemName.trim().split(" ");
        String medicineID = "";

        if (words.length == 1) {
            medicineID += itemName.substring(0, 2).toUpperCase();
        } else {
            for (String word : words) {
                medicineID += Character.toUpperCase(word.charAt(0));
            }
        }
        return medicineID;
    }
    public List<Medicine> getSelectedMedicines() {
        List<Medicine> selectedMedicine = medicine.stream()
                .filter(Medicine::isSelected)
                .toList();
        return selectedMedicine;
    }
    private boolean addMedicine() throws ParseException {
        boolean isAdded = false;
        String medicineId = getMedicineId(ProductNameTextField);
        int quantity = Integer.parseInt(QuantityTextField.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        for(Medicine med:medicine){
            if(Objects.equals(med.getItemName(), ProductNameTextField.getText())){
                System.out.println("existing");
                inventoryManagementApplication.getMedicineInventoryFacade().addInventory(med.getMedicineId(),med.getItemType(),quantity);
                isAdded = true;
                break;
            }else{
                System.out.println("new");
                Date expirationDate;
                String inputDate = ExpirationDateTextField.getText();
                expirationDate = dateFormat.parse(inputDate);

                medicineModel.setMedicineId(medicineId);
                medicineModel.setItemName(ProductNameTextField.getText());
                medicineModel.setExpirationDate(new Timestamp(expirationDate.getTime()));
                inventoryManagementApplication.getMedicineInventoryFacade().addMedicine(medicineModel);
                inventoryManagementApplication.getMedicineInventoryFacade().addInventory(medicineId,"medicine",quantity);
                isAdded = true;
                break;
            }
        }
        refresh();
        return isAdded;
    }

    public void onConfirmBtnClick(ActionEvent actionEvent) throws ParseException {
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
            if(addMedicine()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added");
                alert.showAndWait();
            }
        }
    }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InventoryDeleteItemModal.fxml"));
            Parent root = loader.load();
            DeleteInventoryController deleteInventoryController = loader.getController();
            deleteInventoryController.showMedicineList(selectedMedicine);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }
    }

    public void onCancelBtnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean isValidTextInput(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

}
