package com.rocs.infirmary.application.controller.lowstock.helper;

import com.rocs.infirmary.application.controller.lowstock.LowStockNotificationController;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockItems;
import com.rocs.infirmary.application.module.lowstock.notification.service.application.LowStockNotificationServiceApplication;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

/**
 * Handles low stock alerts by checking inventory and showing notifications.
 */
public class LowStockAlertHelper {

    private static LowStockNotificationServiceApplication lowStockService;
    private static ImageView redCircle;
    private static ToggleButton toggleButton;
    private static Node node;
    /**
     * LowStockAlertHelper()
     * is a no-argument constructor that allows creating a LowStockAlertHelper
     */
    public LowStockAlertHelper(){}
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
    public static void checkLowStockAndShowAlert() {

        List<LowStockItems> lowStockItems = lowStockService.getDashboardFacade().getAllLowStockMedicine();

        if (lowStockItems.isEmpty()) {
            redCircle.setVisible(false);
            toggleButton.setOnMouseClicked(null);
            return;
        }
        redCircle.setVisible(true);
        toggleButton.setOnMouseClicked(event -> {
            toggleButton.setDisable(true);
            Stage currentPage = (Stage) node.getScene().getWindow();
            Stage modal =  LowStockNotificationController.showLowStockModal(currentPage, lowStockItems);
            if(modal != null) {
                modal.setOnHidden(e -> toggleButton.setDisable(false));
            }
        });
    }
}
