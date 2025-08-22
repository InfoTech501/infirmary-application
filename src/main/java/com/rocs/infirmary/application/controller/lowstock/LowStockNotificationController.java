package com.rocs.infirmary.application.controller.lowstock;

import com.rocs.infirmary.application.data.model.report.lowstock.LowStockItems;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code LowStockNotificationController} handles the UI for displaying
 * low stock alert notifications in the application.
 **/
public class LowStockNotificationController {

    @FXML
    private VBox alertContainer;

    @FXML
    private Label alertTitle;

    @FXML
    private Label alertMessage;

    private static final Logger LOGGER = LoggerFactory.getLogger(LowStockNotificationController.class);


    /**
     * Sets the alert title and message content in the notification modal
     * @param title   the title of the alert
     * @param message the detailed message to be displayed in the alert
     */
    public void setAlertDetails(String title, String message) {
        alertTitle.setText(title);
        alertMessage.setText(message);
        alertContainer.setVisible(true);
        alertContainer.setManaged(true);
    }

    @FXML
    private void onCloseButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Shows a custom alert window in the bottom-right corner of the main window
     * to notify the user about inventory medicine that have low stock.
     * @param ownerStage       the main window where the alert should appear beside
     * @param lowStockMedicine a list of Inventory Medicine that are low in stock
     */
    public static void showLowStockModal(Stage ownerStage, List<LowStockItems> lowStockMedicine) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LowStockNotificationController.class.getResource("/views/LowStockNotificationModal.fxml"));
            VBox root = loader.load();

            LowStockNotificationController controller = loader.getController();

            String lowStockProductNames = lowStockMedicine.stream()
                    .map(lowStockReport -> lowStockReport.getDescription() + " (Quantity: " + lowStockReport.getQuantityAvailable() + ")")
                    .collect(Collectors.joining("\n"));

            String message = lowStockMedicine.size() + " product(s) have low stock. Check those products to re-order\n"
                    + "before the stock reaches zero.\n\nProduct(s):\n" + lowStockProductNames;


            controller.setAlertDetails("Low Stock Alert", message);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage modalStage = new Stage();

            modalStage.setScene(scene);
            modalStage.initOwner(ownerStage);
            modalStage.sizeToScene();
            modalStage.initModality(Modality.NONE);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            modalStage.setOnShown(e -> {
                updateModalPosition(modalStage, ownerStage);
            });
            ownerStage.xProperty().addListener((obs, oldVal, newVal) -> updateModalPosition(modalStage, ownerStage));
            ownerStage.yProperty().addListener((obs, oldVal, newVal) -> updateModalPosition(modalStage, ownerStage));
            ownerStage.widthProperty().addListener((obs, oldVal, newVal) -> updateModalPosition(modalStage, ownerStage));
            ownerStage.heightProperty().addListener((obs, oldVal, newVal) -> updateModalPosition(modalStage, ownerStage));

            modalStage.show();

        } catch (IOException e) {
            LOGGER.error("IO Exception Occurred" + e.getMessage());
        }
    }

    private static void updateModalPosition(Stage modalStage, Stage ownerStage) {
        double x = ownerStage.getX() + ownerStage.getWidth() - modalStage.getWidth() - 20;
        double y = ownerStage.getY() + ownerStage.getHeight() - modalStage.getHeight() - 20;
        modalStage.setX(x);
        modalStage.setY(y);
    }

}


