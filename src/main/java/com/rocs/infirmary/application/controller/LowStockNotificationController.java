package com.rocs.infirmary.application.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class LowStockNotificationController {

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

    @FXML
    private void onCloseButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}


