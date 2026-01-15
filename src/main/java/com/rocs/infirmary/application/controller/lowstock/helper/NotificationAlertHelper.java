package com.rocs.infirmary.application.controller.lowstock.helper;

import com.rocs.infirmary.application.controller.lowstock.NotificationController;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockItems;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.lowstock.notification.service.application.LowStockNotificationServiceApplication;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles low stock alerts by checking inventory and showing notifications.
 */
public class NotificationAlertHelper {

    private static LowStockNotificationServiceApplication lowStockService;
    private static InventoryManagementApplication inventoryManagementApplication;
    private static ImageView redCircle;
    private static ToggleButton toggleButton;
    private static Node node;
    /**
     * NotificationAlertHelper()
     * is a no-argument constructor that allows creating a NotificationAlertHelper
     */
    public NotificationAlertHelper(){}
    /**
     * Binds the red  icon and the toggle button to this helper.
     * These UI elements are needed to show alerts
     *
     * @param redCircle an ImageView that signals a low stock alert
     * @param toggleButton a ToggleButton that the user clicks to view the alert
     */
    public void bindUI(ImageView redCircle, ToggleButton toggleButton) {
        this.redCircle = redCircle;
        this.toggleButton = toggleButton;
    }
    /**
     * Sets the service that checks which products are low in stock.
     * @param lowStockService the service used to get low stock items
     */
    public void bindService(LowStockNotificationServiceApplication lowStockService,InventoryManagementApplication inventoryManagementApplication){
        this.lowStockService = lowStockService;
        this.inventoryManagementApplication = inventoryManagementApplication;
    }
    /**
     * Sets the current UI node. This is needed to get the current  window
     * so the modal alert shows in the right place.
     *
     * @param node any Node from the current JavaFX scene
     */
    public void setMainNode(Node node) {
        this.node = node;
    }
    /**
     * Checks for low stock items. If found, shows redCircle and
     * sets toggleButton to open the alert modal with product info.
     */
    public static void checkLowStockAndShowAlert() {

        List<LowStockItems> lowStockItems = lowStockService.getDashboardFacade().getAllLowStockMedicine();
        List<Medicine> expiringMedicine = inventoryManagementApplication.getMedicineInventoryFacade().getAllMedicine();
        Date today = new Date();

        long sevenDaysLaterMillis = today.getTime() + 7L * 24 * 60 * 60 * 1000;

        List<Medicine> expiringSoon = expiringMedicine.stream()
                .filter(med -> {
                    Date expiryDate = med.getExpirationDate(); // assuming this is java.util.Date
                    return !expiryDate.before(today) && expiryDate.getTime() <= sevenDaysLaterMillis;
                })
                .collect(Collectors.toList());

        if (lowStockItems.isEmpty() && expiringSoon.isEmpty()) {
            redCircle.setVisible(false);
            toggleButton.setOnMouseClicked(null);
            return;
        }
        redCircle.setVisible(true);
        toggleButton.setOnMouseClicked(event -> {
            toggleButton.setDisable(true);
            Stage currentPage = (Stage) node.getScene().getWindow();
            NotificationController.addLowStockItemOnAlert(currentPage, lowStockItems);
            NotificationController.addExpiringMedicineOnAlert(currentPage,expiringSoon);
            toggleButton.setDisable(false);
        });
    }
}
