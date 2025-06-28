package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.LowStockNotificationServiceApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
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
    private TableColumn<Medicine, String> expiryDateColumn;
    @FXML
    private TableColumn<Medicine,String> descriptionColumn;
    @FXML
    private TextField searchTextField;


    private ObservableList<Medicine> medicine;
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private List<Medicine> medicineList = new ArrayList<>();

    private LowStockNotificationServiceApplication lowStockNotificationServiceApplication = new LowStockNotificationServiceApplication();
    private List<LowStockReport> lowStockItems = new ArrayList<>();
    private boolean lowLowStock = false;


    @FXML
    private ImageView RedCircle;
    @FXML
    private ImageView NotificationImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        itemSearch();
        initalizeEditClick();
        checkLowStockAndShowAlert();
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
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setStyle("-fx-alignment: CENTER;");
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        expiryDateColumn.setStyle("-fx-alignment: CENTER;");

    }
    private void initalizeEditClick(){
        medDetailsTable.setRowFactory(t->{
            TableRow<Medicine>tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event->{
                if(!tableRow.isEmpty() && event.getClickCount() == 1){
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
    private void refresh() {
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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(location)));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }
    private void showEditInventory(Medicine medicine) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InventoryEditItemModal.fxml"));
        Parent root = loader.load();
        UpdateInventoryController updateInventoryController = loader.getController();
        updateInventoryController.showItemToEdit(medicine);

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
        boolean deleted = false;
        for (Medicine med : medicineList) {
            deleted = inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(med.getInventoryId());
        }
        return deleted;
    }
    /**
     * this method handles the action triggered when the remove button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onRemoveBtnClick(ActionEvent actionEvent) throws IOException {
        System.out.println("clicked");
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



    public void showLowStockModal(Stage ownerStage, List<String> lowStockProducts) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/LowStockNotificationModal.fxml"));
            VBox root = loader.load();

            LowStockNotificationController controller = loader.getController();

            String message = lowStockProducts.size() + " product(s) have low stock. Check those products to re-order\n" +
                    "before the stock reaches zero.\nProduct(s): " + String.join(", ", lowStockProducts);



            controller.setAlertDetails("Low Stock Alert", message);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage modalStage = new Stage();

            modalStage.setScene(scene);
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.NONE);
            modalStage.initStyle(StageStyle.TRANSPARENT);


            double x = ownerStage.getX() + ownerStage.getWidth() - 472 - 60;
            double y = ownerStage.getY() + ownerStage.getHeight() - 580 - 20;

            modalStage.setX(x);
            modalStage.setY(y);

            modalStage.show();

        } catch (IOException e) {
            System.out.println(" Error Occurred" +  e.getMessage());
        }
    }


    private void checkLowStockAndShowAlert() {
        lowStockItems = lowStockNotificationServiceApplication.getDashboardFacade().getAllLowStockMedicine();

        if (!lowStockItems.isEmpty()) {
            List<String> productInfo = lowStockItems.stream()
                    .map(LowStockReport::getDescription)
                    .toList();

                RedCircle.setVisible(true);
                NotificationImage.setOnMouseClicked(event -> {
                    Stage stage = (Stage) medDetailsTable.getScene().getWindow();
                    showLowStockModal(stage, productInfo);
                });

             lowLowStock = true;
        }


    }
}


