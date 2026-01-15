package com.rocs.infirmary.application.controller.lowstock;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockItems;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code NotificationController} handles the UI for displaying
 * low stock alert notifications in the application.
 **/
public class NotificationController {

    @FXML
    private VBox alertCardParent;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private static Stage activeModal;
    private static NotificationController activeController;
    /**
     * Sets the alert title and message content in the notification modal
     * @param title   the title of the alert
     * @param message the detailed message to be displayed in the alert
     */
    public void createNotificationCard(String title, String message) {
        VBox alertCard = new VBox();
        alertCard.getStyleClass().add("alert-card");
        alertCard.setSpacing(10);
        alertCard.setPadding(new Insets(10));
        alertCard.setMaxWidth(440);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("alert-title");

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("alert-message");
        messageLabel.setWrapText(true);

        Button closeBtn = new Button("X");
        closeBtn.getStyleClass().add("alert-close-button");
        closeBtn.setOnAction(e -> alertCardParent.getChildren().remove(alertCard));

        HBox header = new HBox();
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(titleLabel, closeBtn);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        alertCard.getChildren().addAll(header, messageLabel);
        alertCardParent.getChildren().addFirst(alertCard);
        alertCard.applyCss();
        alertCard.layout();
    }



    /**
     * adds a new low stock item on notification
     * to notify the user about inventory medicine that have low stock.
     * @param ownerStage       the main window where the alert should appear beside
     * @param lowStockMedicine a list of Inventory Medicine that are low in stock
     */
    public static void addLowStockItemOnAlert(Stage ownerStage, List<LowStockItems> lowStockMedicine) {
        String lowStockProductNames = lowStockMedicine
                .stream()
                .map(lowStockReport -> lowStockReport.getDescription() + " (Quantity: " + lowStockReport.getQuantityAvailable() + ")")
                .collect(Collectors.joining("\n"));

        if(!lowStockProductNames.isEmpty()){
            String message = lowStockMedicine.size() + " product(s) have low stock. Check those products to re-order\n"
                    + "before the stock reaches zero.\nProduct(s):\n" + lowStockProductNames;
            addNotification(ownerStage, "Low Stock Alert", message);
        }
    }
    /**
     * add expiring medicine on notification
     * to notify the user about inventory medicine that is expiring.
     * @param ownerStage       the main window where the alert should appear beside
     * @param expiringMedicine a list of Inventory Medicine that are expiring
     */
    public static void addExpiringMedicineOnAlert(Stage ownerStage, List<Medicine> expiringMedicine){
        String expiringMedicines = expiringMedicine
                .stream()
                .map(medicine -> medicine.getItemName() + " ( Expiry Date: "+medicine.getExpirationDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")")
                .collect(Collectors.joining("\n"));
        if(!expiringMedicines.isEmpty()){
            String message = expiringMedicine.size() + " product(s) is near expiring. Check those products to re-order\n"
                    + "before the stock expires.\nProduct(s):\n" + expiringMedicines;
            addNotification(ownerStage, "Expiring Item Alert", message);
        }
    }
    private static void addNotification(Stage ownerStage, String title, String message) {
        if (activeModal == null) {
            showNotificationWindow(ownerStage);
        }
        activeController.createNotificationCard(title, message);
    }
    private static void showNotificationWindow(Stage ownerStage) {
        try {

            FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource("/views/LowStockNotificationModal.fxml"));
            VBox root = loader.load();

            activeController = loader.getController();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initOwner(ownerStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            activeModal = stage;

            stage.setOnShown(windowEvent -> updateModalPosition(stage, ownerStage));
            ownerStage.xProperty().addListener((observableValue, oldValue, newValue) -> updateModalPosition(stage, ownerStage));
            ownerStage.yProperty().addListener((observableValue, oldValue, newValue) -> updateModalPosition(stage, ownerStage));
            ownerStage.widthProperty().addListener((observableValue, oldValue, newValue) -> updateModalPosition(stage, ownerStage));
            ownerStage.heightProperty().addListener((observableValue, oldValue, newValue) -> updateModalPosition(stage, ownerStage));
            stage.show();

        } catch (IOException e) {
            LOGGER.error("IO Exception Occurred: {}", e.getMessage());
        }
    }
    @FXML
    private void onCloseButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        activeModal = null;
        activeController = null;
    }
    private static void updateModalPosition(Stage modalStage, Stage ownerStage) {
        double x = ownerStage.getX() + ownerStage.getWidth() - modalStage.getWidth() - 20;
        double y = ownerStage.getY() + ownerStage.getHeight() - modalStage.getHeight() - 20;
        modalStage.setX(x);
        modalStage.setY(y);
    }

}


