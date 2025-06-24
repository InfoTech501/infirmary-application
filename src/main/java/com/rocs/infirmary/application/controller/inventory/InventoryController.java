package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.LowStockNotificationServiceApplication;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.InventoryManagementApplication;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
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
    private List<Medicine> medicineList = new ArrayList<>();

    private LowStockNotificationServiceApplication lowStockNotificationServiceApplication = new LowStockNotificationServiceApplication();
     private List<LowStockReport> lowStockItems = new ArrayList<>();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        itemSearch();
        checkLowStockAndShowAlert();
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
    private List<Medicine> getSelectedMedicines() {
        List<Medicine> selectedMedicine = medicine.stream()
                .filter(Medicine::isSelected)
                .toList();
        for(Medicine med: selectedMedicine){
            medicineList.add(med);
        }
        return selectedMedicine;
    }
    public void onClearFilterClick(ActionEvent actionEvent) {
        ProductNameColumn.setSortable(true);
        ProductNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        MedDetailsTable.getSortOrder().setAll(ProductNameColumn);
        MedDetailsTable.sort();
        SearchTextField.clear();
        refresh();
        itemSearch();
    }

    public void onQuantityFilterClick(ActionEvent actionEvent) {
        QuantityColumn.setSortable(true);
        QuantityColumn.setSortType(TableColumn.SortType.ASCENDING);
        MedDetailsTable.getSortOrder().setAll(QuantityColumn);
        MedDetailsTable.sort();
    }
    private boolean deleteMedicine(){
        boolean deleted = false;
        for (Medicine med : medicineList) {
            deleted = inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(med.getInventoryId());
        }
        return deleted;
    }
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

            String message = String.format(
                    "%d product(s) have low stock. Check those products to re-order\nbefore the stock reaches zero.\nProduct(s): %s",
                    lowStockProducts.size(),
                    String.join(", ", lowStockProducts)
            );

            controller.setAlertDetails("Low Stock Alert", message);

            root.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.25)));

            Scene scene = new Scene(root);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.NONE);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setAlwaysOnTop(true);

            double x = ownerStage.getX() + ownerStage.getWidth() - 472 - 20;
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

            Platform.runLater(() -> {
                Stage stage = (Stage) MedDetailsTable.getScene().getWindow();
                showLowStockModal(stage, productInfo);
            });
        }
    }
}


