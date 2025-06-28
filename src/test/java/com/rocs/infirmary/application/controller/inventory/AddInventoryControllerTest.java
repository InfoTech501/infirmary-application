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

import javax.swing.text.TabableView;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class AddInventoryControllerTest {

    private Button confirmButton;
    private Button removeButton;
    private TextField ProductNameTextField;
    private TextField QuantityTextField;
    private TextField ExpirationDateTextField;
    private TableView<Medicine> MedDetailsTable;

    @BeforeEach
    void setup(FxRobot robot){
        ProductNameTextField = robot.lookup("#ProductNameTextField").queryAs(TextField.class);
        QuantityTextField = robot.lookup("#QuantityTextField").queryAs(TextField.class);
        ExpirationDateTextField = robot.lookup("#ExpirationDateTextField").queryAs(TextField.class);
        MedDetailsTable = robot.lookup("#MedDetailsTable").queryAs(TableView.class);
        confirmButton = robot.lookup("#ButtonConfirm").queryAs(Button.class);
        removeButton = robot.lookup("#InventoryRemoveItemsButton").queryAs(Button.class);

        assertNotNull(MedDetailsTable);
        assertNotNull(ProductNameTextField);
        assertNotNull(QuantityTextField);
        assertNotNull(ExpirationDateTextField);
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
        robot.clickOn(QuantityTextField);
        robot.write("50");
        robot.clickOn(ExpirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Product Name cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void emptyQuantityDialogTest(FxRobot robot){
        robot.clickOn(ProductNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(ExpirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Quantity cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void emptyExpirationDateDialogTest(FxRobot robot){
        robot.clickOn(ProductNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(QuantityTextField);
        robot.write("50");
        robot.clickOn(confirmButton);
        assertTrue(robot.lookup("Expiration date cannot be empty").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
    @Disabled
    @Test
    void testInvalidProductName(FxRobot robot) {
        robot.clickOn(ProductNameTextField);
        robot.write("''123ABC''");
        robot.clickOn(QuantityTextField);
        robot.write("10");
        robot.clickOn(ExpirationDateTextField);
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
        robot.clickOn(ProductNameTextField);
        robot.write("Mefenamic Acid");
        robot.clickOn(QuantityTextField);
        robot.write("50");
        robot.clickOn(ExpirationDateTextField);
        robot.write("2027-01-01");
        robot.clickOn(confirmButton);

        boolean found = MedDetailsTable.getItems().stream().anyMatch(med -> "Mefenamic Acid".equals(med.getItemName()));
        assertTrue(found);
        assertTrue(robot.lookup("Successfully Added").tryQuery().isPresent());
    }
    @Disabled
    @Test
    void removeMedicine(FxRobot robot){
        robot.interact(() -> {
            MedDetailsTable.getItems().get(0).setIsSelected(true);
        });
        robot.clickOn(removeButton);
        robot.clickOn("#ButtonConfirm");
    }
}