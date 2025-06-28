package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class AddInventoryControllerTest {

    private Button confirmButton;
    private Button removeButton;
    private TextField productNameTextField;
    private TextField quantityTextField;
    private TextField expirationDateTextField;
    private TableView<Medicine> medDetailsTable;

    @BeforeEach
    void setup(FxRobot robot){
        productNameTextField = robot.lookup("#productNameTextField").queryAs(TextField.class);
        quantityTextField = robot.lookup("#quantityTextField").queryAs(TextField.class);
        expirationDateTextField = robot.lookup("#expirationDateTextField").queryAs(TextField.class);
        medDetailsTable = robot.lookup("#medDetailsTable").queryAs(TableView.class);
        confirmButton = robot.lookup("#ButtonConfirm").queryAs(Button.class);
        removeButton = robot.lookup("#InventoryRemoveItemsButton").queryAs(Button.class);

        assertNotNull(medDetailsTable);
        assertNotNull(productNameTextField);
        assertNotNull(quantityTextField);
        assertNotNull(expirationDateTextField);
        assertNotNull(confirmButton);
        assertNotNull(removeButton);

    }
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/InventoryAddItemModal.fxml"));
        BorderPane mainLayout;
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @Disabled
    @Test
    void showWarningDialogTest(FxRobot robot) {
        robot.clickOn("#ButtonConfirm");
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void emptyProductNameDialogTest(FxRobot robot){
        robot.clickOn(quantityTextField);
        robot.write("50");
        robot.clickOn(expirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Product Name cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void emptyQuantityDialogTest(FxRobot robot){
        robot.clickOn(productNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(expirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Quantity cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void emptyExpirationDateDialogTest(FxRobot robot){
        robot.clickOn(productNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(quantityTextField);
        robot.write("50");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Expiration date cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void testInvalidProductName(FxRobot robot) {
        robot.clickOn(productNameTextField);
        robot.write("''123ABC''");
        robot.clickOn(quantityTextField);
        robot.write("10");
        robot.clickOn(expirationDateTextField);
        robot.write("2026-01-01");
        robot.clickOn(confirmButton);

        assertTrue(robot.lookup("Product Name must only contain letters.").tryQuery().isPresent());

    }
    @Disabled
    @Test
    void testRemoveButtonWithoutSelection(FxRobot robot) {
        robot.clickOn(removeButton);
        assertTrue(robot.lookup("No Items selected").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void addNewMedicine(FxRobot robot) throws InterruptedException {
        robot.clickOn(productNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(quantityTextField);
        robot.write("50");
        robot.clickOn(expirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);

        boolean found = medDetailsTable.getItems().stream().anyMatch(med -> "Mefenamic Acid".equals(med.getItemName()));
        assertTrue(found);
        assertTrue(robot.lookup("Successfully Added").tryQuery().isPresent());
    }
    @Disabled
    @Test
    void removeMedicine(FxRobot robot){
        robot.interact(() -> {
            medDetailsTable.getItems().get(0).setIsSelected(true);
        });
        robot.clickOn(removeButton);
        robot.clickOn("#ButtonConfirm");
    }
}