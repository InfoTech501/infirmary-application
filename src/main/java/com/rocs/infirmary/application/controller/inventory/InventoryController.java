package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.controller.records.AddDailyTreatmentRecordController;
import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.controller.lowstock.helper.LowStockAlertHelper;
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
import javafx.scene.layout.StackPane;
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
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * {@code InventoryController} is used to handle event processes of the Inventory,
 * this implements Initializable interface
 **/
public class InventoryController implements Initializable {
    @FXML
    private StackPane inventoryPage;
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
    private TableColumn<Medicine, String> itemTypeColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    private CheckBox selectAllCheckbox;
    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<String> itemTypeComboBox;
    @FXML
    private ComboBox<String> expiryDateComboBox;
    private static final int ROWS_PER_PAGE = 11;

    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();
    private ObservableList<Medicine> medicineInventoryList = FXCollections.observableArrayList();
    private FilteredList<Medicine> filteredList;
    private List<Medicine> medicineList = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);
    private boolean isRowAction = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        itemSearch();
        initalizeEditClick();
        setupSelectAll();
        setupPagination();
        itemExpiryDropdowns();
        setupFiltersItemType();
        setupFiltersExpiryDate();
    }

    private void itemExpiryDropdowns() {

        setupItemTypeDropdown();
        setupExpiryDateDropdown();
    }

    private void reloadDropdownFilters() {
        setupItemTypeDropdown();
        setupExpiryDateDropdown();
    }

    private void setupItemTypeDropdown() {

        Set<String> itemTypes = medicineInventoryList.stream()
                .map(Medicine::getItemType)
                .filter(type -> type != null && !type.trim().isEmpty())
                .collect(Collectors.toSet());

        ObservableList<String> itemTypeItems = FXCollections.observableArrayList();
        itemTypeItems.add("Item Type");
        itemTypeItems.addAll(itemTypes.stream().sorted().toList());
        itemTypeComboBox.setItems(itemTypeItems);
        itemTypeComboBox.getSelectionModel().select("Item Type");

    }

    private void setupExpiryDateDropdown() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        Set<String> expiryDates = medicineInventoryList.stream()
                .map(Medicine::getExpirationDate)
                .filter(date -> date != null)
                .map(ts -> ts.toLocalDateTime().toLocalDate().format(formatter))
                .collect(Collectors.toSet());

        ObservableList<String> expiryDateItems = FXCollections.observableArrayList();
        expiryDateItems.add("Expiry Date");
        expiryDateItems.addAll(expiryDates.stream().sorted().toList());
        expiryDateComboBox.setItems(expiryDateItems);
        expiryDateComboBox.getSelectionModel().select("Expiry Date");
    }

    private void setupFiltersItemType() {

        itemTypeComboBox.setOnAction(e -> {
            String selected = itemTypeComboBox.getValue();

            filteredList.setPredicate(med -> {

                if (selected.equals("Item Type"))
                    return true;

                return med.getItemType() != null &&
                        med.getItemType().equalsIgnoreCase(selected);
            });

            medDetailsTable.refresh();
            updatePagination();
            pagination.setCurrentPageIndex(0);
        });
    }

        private void setupFiltersExpiryDate() {
            expiryDateComboBox.setOnAction(e -> {
                String selected = expiryDateComboBox.getValue();

                filteredList.setPredicate(med -> {

                    if (selected.equals("Expiry Date"))
                        return true;

                    if (med.getExpirationDate() == null) return false;

                    LocalDate ld = med.getExpirationDate().toLocalDateTime().toLocalDate();
                    String formatted = ld.format(DateTimeFormatter.ofPattern("yyyy"));

                    return formatted.equalsIgnoreCase(selected);
                });

                medDetailsTable.refresh();
                updatePagination();
                pagination.setCurrentPageIndex(0);
            });
        }

    private void setupRowSelectListener() {
        if (medicineInventoryList == null || medicineInventoryList.isEmpty()) {
            return;

        }

            for (Medicine med : medicineInventoryList) {

                if (med.isSelectedProperty() == null) {
                    med.setIsSelected(false);
                }

                med.isSelectedProperty().removeListener(this::onRowSelectionChanged);
                med.isSelectedProperty().addListener(this::onRowSelectionChanged);
        }
    }

    private void onRowSelectionChanged(
            javafx.beans.value.ObservableValue<? extends Boolean> observable,
            Boolean oldValue,
            Boolean newValue
    ) {
        isRowAction = true;

        if (!newValue) {
            selectAllCheckbox.setSelected(false);

        } else if (medicineInventoryList.stream().allMatch(Medicine::isSelected)) {
            selectAllCheckbox.setSelected(true);
        }

        isRowAction = false;
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

    private void setMedicineExpiration() {
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

    private void initalizeEditClick() {
        medDetailsTable.setRowFactory(t -> {
            TableRow<Medicine> tableRow = new TableRow<>();
            tableRow.setOnMouseClicked(event -> {
                if (!tableRow.isEmpty() && event.getClickCount() == 2) {
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
        LowStockAlertHelper.checkLowStockAndShowAlert();
        selectAllCheckbox.setSelected(false);
        List<Medicine> medicineList = inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicine();
        for (Medicine med : medicineList) {
            if (med.isSelectedProperty() == null) {
                med.setIsSelected(false);
            }
        }
        medicineInventoryList = FXCollections.observableArrayList(medicineList);
        setupRowSelectListener();
        reloadDropdownFilters();
        itemSearch();
        updatePagination();
        medDetailsTable.refresh();
    }

    private void showModal(ActionEvent actionEvent, String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        loader.setControllerFactory(param -> new AddInventoryController());
        Parent root = loader.load();

        AddInventoryController controller = loader.getController();
        controller.setParentController(this);
        inventoryPage.getChildren().add(root);
    }

    private void showEditInventory(Medicine medicine) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InventoryEditItemModal.fxml"));
        loader.setControllerFactory(param -> new UpdateInventoryController());
        Parent root = loader.load();

        UpdateInventoryController controller = loader.getController();
        controller.setParentController(this);
        controller.showItemToEdit(medicine);
        inventoryPage.getChildren().add(root);
    }

    /**
     * this method handles the action triggered when the add new medicine button is clicked.
     *
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onShowAddModalBtnClick(ActionEvent actionEvent) throws IOException {
        showModal(actionEvent, "/views/InventoryAddItemModal.fxml");
    }

    private void itemSearch() {
        filteredList = new FilteredList<>(medicineInventoryList, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(medicine -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String searchKeyword = newValue.toLowerCase();

                boolean matchName = medicine.getItemName().toLowerCase().contains(searchKeyword);

                boolean matchType = medicine.getItemType() != null &&
                        medicine.getItemType().toLowerCase().contains(searchKeyword);

                boolean matchDate = false;
                if (medicine.getExpirationDate() != null) {
                    LocalDate ld = medicine.getExpirationDate().toLocalDateTime().toLocalDate();
                    String formatted = ld.format(outputFormat).toLowerCase();
                    matchDate = formatted.contains(searchKeyword);
                }

                return matchName || matchType || matchDate;
            });

            updatePagination();
            pagination.setCurrentPageIndex(0);
        });

        updatePagination();
    }

    /**
     * this method handles the action triggered when the increment filter button is clicked.
     *
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onItemTypeFilterClick(ActionEvent actionEvent) {

        FilteredList<Medicine> filteredList = new FilteredList<>(medicineInventoryList, med -> {
            if (med.getItemType() == null) return false;
            return !med.getItemType().isEmpty();
        });

        SortedList<Medicine> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(medDetailsTable.comparatorProperty());
        medDetailsTable.setItems(sortedList);

        itemTypeColumn.setSortable(true);
        itemTypeColumn.setSortType(TableColumn.SortType.ASCENDING);
        medDetailsTable.getSortOrder().setAll(itemTypeColumn);
        medDetailsTable.sort();

    }

    /**
     * this method handles the action triggered when the decrement filter button is clicked.
     *
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onExpiryDateFilterClick(ActionEvent actionEvent) {
        expiryDateColumn.setSortable(true);
        expiryDateColumn.setSortType(TableColumn.SortType.DESCENDING);
        medDetailsTable.getSortOrder().setAll(expiryDateColumn);
        medDetailsTable.sort();
    }

    private List<Medicine> getSelectedMedicines() {
        medicineList.clear();
        List<Medicine> selectedMedicine = medicineInventoryList.stream()
                .filter(Medicine::isSelected)
                .toList();
        for (Medicine med : selectedMedicine) {
            medicineList.add(med);
        }
        return selectedMedicine;
    }

    /**
     * this method handles the action triggered when the clear filter button is clicked.
     *
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
     *
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onProductNameFilterClick(ActionEvent actionEvent) {
        productNameColumn.setSortable(true);
        productNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        medDetailsTable.getSortOrder().setAll(productNameColumn);
        medDetailsTable.sort();
    }

    private boolean deleteMedicine() {
        return inventoryManagementApplication.getMedicineInventoryFacade().deleteInventory(medicineList);
    }

    /**
     * this method handles the action triggered when the remove button is clicked.
     *
     * @param actionEvent the event triggered by the confirm button click
     */
    public void onRemoveBtnClick(ActionEvent actionEvent) throws IOException {
        if (getSelectedMedicines().isEmpty()) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Warning");
            ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.setContentText("No Items selected");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
        if (getSelectedMedicines().size() >= 2) {
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
        if (getSelectedMedicines().size() == 1) {
            ControllerHelper.alertAction("Confirm Delete", "Are you sure you want to delete this medicine?")
                    .ifPresent(response -> {
                        if (response.getButtonData() == ButtonBar.ButtonData.YES && deleteMedicine()) {
                            ControllerHelper.showDialog("Notification", "Deleted Successfully!");
                            refresh();
                            itemSearch();
                        }
                    });
        }
    }

    private void setupSelectAll() {
        selectAllCheckbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (medicineInventoryList == null || medicineInventoryList.isEmpty()) return;

            if (isRowAction) return;

            if (medicineInventoryList == null || medicineInventoryList.isEmpty()) return;

                for (Medicine med : medicineInventoryList) {
                    med.setIsSelected(isNowSelected);
                }

                
                medDetailsTable.refresh();
        });
    }

    private void setupPagination() {
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            changeTableViewData(newIndex.intValue());
        });
    }

    private void updatePagination() {
        if (filteredList == null) {
            LOGGER.error("Warning: filteredList is null. Pagination cannot be updated yet.");
            return;
        }

        try {
            int totalItems = filteredList.size();
            int pageCount = (int) Math.ceil((double) totalItems / ROWS_PER_PAGE);
            pagination.setPageCount(Math.max(pageCount, 1));
            changeTableViewData(0);

        } catch (Exception e) {
            LOGGER.error("Error updating pagination: " + e.getMessage());
        }
    }

    private void changeTableViewData(int pageIndex) {
        if (filteredList == null) {
            LOGGER.error("Warning: filteredList is null. Cannot change table view data.");
            return;
        }

        try {
            SortedList<Medicine> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(medDetailsTable.comparatorProperty());
            int fromIndex = pageIndex * ROWS_PER_PAGE;
            int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, sortedList.size());

            ObservableList<Medicine> currentPageData = FXCollections.observableArrayList(
                    sortedList.subList(fromIndex, toIndex)
            );

            medDetailsTable.setItems(currentPageData);
        } catch (NullPointerException e) {
            LOGGER.error(" Error: Table update failed because some data is missing.", e);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error(" Error: Table update failed because the data index is out of range.", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(" Error: Table update failed due to invalid data or argument.", e);
        }
    }
}