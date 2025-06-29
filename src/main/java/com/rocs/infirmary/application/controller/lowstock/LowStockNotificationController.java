package com.rocs.infirmary.application.controller.lowstock;

import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;


public class LowStockNotificationController {

    @FXML
    private VBox AlertContainer;

    @FXML
    private Label AlertTitle;

    @FXML
    private Label AlertMessage;



    /**
     * Sets the alert title and message content in the notification modal,
     * and ensures the alert container is visible and managed.
     *
     * @param title   the title of the alert
     * @param message the detailed message to be displayed in the alert
     */

    public void setAlertDetails(String title, String message) {
        AlertTitle.setText(title);
        AlertMessage.setText(message);
        AlertContainer.setVisible(true);
        AlertContainer.setManaged(true);
    }

    /**
     * Closes the current modal window when the close button is clicked.
     *
     * @param event the mouse click event triggered by the close button
     */
    @FXML
    private void onCloseButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Shows a custom alert window in the bottom-right corner of the main window
     * to notify the user about inventory medicine that have low stock.
     *
     * @param ownerStage       the main window where the alert should appear beside
     * @param lowStockMedicine a list of Inventory Medicine that are low in stock
     */

    public static void showLowStockModal(Stage ownerStage, List<String> lowStockMedicine) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LowStockNotificationController.class.getResource("/views/LowStockNotificationModal.fxml"));
            VBox root = loader.load();

            LowStockNotificationController controller = loader.getController();

            String message = lowStockMedicine.size() + " product(s) have low stock. Check those products to re-order\n"
                    + "before the stock reaches zero.\n\nProduct(s):\n" + String.join("\n", lowStockMedicine );



            controller.setAlertDetails("Low Stock Alert", message);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage modalStage = new Stage();

            modalStage.setScene(scene);
            modalStage.initOwner(ownerStage);
            modalStage.sizeToScene();
            modalStage.initModality(Modality.NONE);
            modalStage.initStyle(StageStyle.TRANSPARENT);


            double x = ownerStage.getX() + ownerStage.getWidth() - 472 - 60;
            double y = ownerStage.getY() + ownerStage.getHeight() - 580 - 20;

            modalStage.setX(x);
            modalStage.setY(y);

            modalStage.show();

        } catch (IOException e) {
            System.out.println(" Error Occurred" +  e.getMessage());
        }
    }

}


