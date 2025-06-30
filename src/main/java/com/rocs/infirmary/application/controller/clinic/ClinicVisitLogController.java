package com.rocs.infirmary.application.controller.clinic;

import com.rocs.infirmary.application.controller.lowstock.helper.LowStockAlertHelper;
import com.rocs.infirmary.application.module.lowstock.notification.service.LowStockNotificationServiceApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * {@code ClinicVisitLogController} is used to handle event processes of the ClinicVisitLog,
 * this implements Initializable interface
 **/
public class ClinicVisitLogController implements Initializable {

    @FXML
    private ImageView redCircle;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private ScrollPane parentScrollPane;


    private LowStockNotificationServiceApplication lowStockNotificationServiceApplication = new LowStockNotificationServiceApplication();

    private LowStockAlertHelper alertHelper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alertHelper = new LowStockAlertHelper(lowStockNotificationServiceApplication,redCircle,toggleButton);
        alertHelper.checkLowStockAndShowAlert(parentScrollPane);
    }
}
