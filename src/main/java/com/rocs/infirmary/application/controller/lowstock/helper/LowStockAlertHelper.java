package com.rocs.infirmary.application.controller.lowstock.helper;

import com.rocs.infirmary.application.controller.lowstock.LowStockNotificationController;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.module.lowstock.notification.service.LowStockNotificationServiceApplication;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.List;

/**
 * Handles low stock alerts by checking inventory and showing notifications.
 */
public class LowStockAlertHelper {

    private final LowStockNotificationServiceApplication lowStockService;
    private final ImageView redCircle;
    private final ToggleButton toggleButton;

    public LowStockAlertHelper(LowStockNotificationServiceApplication lowStockService, ImageView redCircle, ToggleButton toggleButton) {
        this.lowStockService = lowStockService;
        this.redCircle = redCircle;
        this.toggleButton = toggleButton;
    }

    /**
     * Checks for low stock items. If found, shows redCircle and
     * sets toggleButton to open the alert modal with product info.
     *
     * @param nodeFromCurrentPage a UI node to get the current window
     */
    public void checkLowStockAndShowAlert(Node nodeFromCurrentPage) {

        List<LowStockReport> lowStockItems = lowStockService.getDashboardFacade().getAllLowStockMedicine();

        redCircle.setVisible(false);

        if (lowStockItems.isEmpty()) {
            redCircle.setVisible(false);
            toggleButton.setOnMouseClicked(null);
            return;
        }
            List<String> productInfo = lowStockItems.stream()
                    .map(lowStockReport -> lowStockReport.getDescription() + " (Quantity : " + lowStockReport.getQuantityAvailable() + ")")
                    .toList();

            redCircle.setVisible(true);
            toggleButton.setOnMouseClicked(event -> {
                Stage currentPage = (Stage) nodeFromCurrentPage.getScene().getWindow();
                LowStockNotificationController.showLowStockModal(currentPage, productInfo);
            });

        }
    }
