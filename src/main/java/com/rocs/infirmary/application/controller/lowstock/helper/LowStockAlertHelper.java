package com.rocs.infirmary.application.controller.lowstock.helper;

import com.rocs.infirmary.application.controller.lowstock.LowStockNotificationController;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.lowstock.notification.service.LowStockNotificationServiceApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Handles low stock alerts by checking inventory and showing notifications.
 */
public class LowStockAlertHelper {

    private  LowStockNotificationServiceApplication lowStockService;
    private  ImageView redCircle;
    private  ToggleButton toggleButton;
    private Node node;

    private static LowStockAlertHelper instance;

    private LowStockAlertHelper(){}
    /**
     * Returns the singleton instance of this helper class.
     *
     * @return the one and only LowStockAlertHelper instance
     */
    public static LowStockAlertHelper getInstance() {
        if(instance == null) {
           instance = new LowStockAlertHelper();
        }
        return instance;
    }
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
     *
     * @param lowStockService the service used to get low stock items
     */

    public void bindService(LowStockNotificationServiceApplication lowStockService){
        this.lowStockService = lowStockService;
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
    public void checkLowStockAndShowAlert() {

        List<LowStockReport> lowStockItems = lowStockService.getDashboardFacade().getAllLowStockMedicine();


        if (lowStockItems.isEmpty()) {
            redCircle.setVisible(false);
            toggleButton.setOnMouseClicked(null);
            return;
        }

        redCircle.setVisible(true);
        toggleButton.setOnMouseClicked(event -> {
            Stage currentPage = (Stage) node.getScene().getWindow();
            LowStockNotificationController.showLowStockModal(currentPage, lowStockItems);
        });
    }
}
