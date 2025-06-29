package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.controller.lowstock.LowStockNotificationController;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.module.lowstock.notification.service.LowStockNotificationServiceApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {




    @FXML
    private ImageView RedCircle;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private VBox ParentVbox;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkLowStockAndShowAlert();

    }

    private LowStockNotificationServiceApplication lowStockNotificationServiceApplication = new LowStockNotificationServiceApplication();
    private List<LowStockReport> lowStockItems = new ArrayList<>();
    private boolean lowLowStock = false;


    private void checkLowStockAndShowAlert() {

        lowStockItems = lowStockNotificationServiceApplication.getDashboardFacade().getAllLowStockMedicine();

        RedCircle.setVisible(false);

        if (!lowStockItems.isEmpty()) {
            List<String> productInfo = lowStockItems.stream()
                    .map(lowStockReport -> lowStockReport.getDescription() + " (Quantity : " + lowStockReport.getQuantityAvailable() + ")")
                    .toList();



            RedCircle.setVisible(true);

            toggleButton.setOnMouseClicked(event -> {
                Stage stage = (Stage) ParentVbox.getScene().getWindow();
                LowStockNotificationController.showLowStockModal(stage, productInfo);
            });

            lowLowStock = true;
        }


    }


}
