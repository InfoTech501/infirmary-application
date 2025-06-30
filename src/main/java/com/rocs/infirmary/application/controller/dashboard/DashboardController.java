package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.controller.lowstock.helper.LowStockAlertHelper;
import com.rocs.infirmary.application.module.lowstock.notification.service.LowStockNotificationServiceApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * {@code DashboardController} is used to handle event processes of the Dashboard,
 * this implements Initializable interface
 **/
public class DashboardController implements Initializable {

    @FXML
    private ImageView redCircle;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private VBox parentVbox;

    private LowStockNotificationServiceApplication lowStockNotificationServiceApplication = new LowStockNotificationServiceApplication();

    private LowStockAlertHelper alertHelper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertHelper = new LowStockAlertHelper(lowStockNotificationServiceApplication, redCircle, toggleButton);
        alertHelper.checkLowStockAndShowAlert(parentVbox);
    }
}