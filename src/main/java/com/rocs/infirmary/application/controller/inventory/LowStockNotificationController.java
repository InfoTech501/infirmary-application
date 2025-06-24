package com.rocs.infirmary.application.controller.inventory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class LowStockNotificationController  {

    @FXML
    private VBox AlertContainer;

    @FXML
    private Label AlertTitle;

    @FXML
    private Label AlertMessage;

    public void setAlertDetails(String title, String message) {
        AlertTitle.setText(title);
        AlertMessage.setText(message);
        AlertContainer.setVisible(true);
        AlertContainer.setManaged(true);
    }
}
