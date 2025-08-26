package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
class DeleteMedicineControllerTest {

    private TableView<Medicine> medDetailsTable;
    private Button removeButton;
    private Button confirmDeleteButton;
    private Button cancelDeleteButton;

    @BeforeEach
    void setup(FxRobot robot){
        medDetailsTable = robot.lookup("#medDetailsTable").queryAs(TableView.class);
        removeButton = robot.lookup("#InventoryRemoveItemsButton").queryAs(Button.class);
        confirmDeleteButton = robot.lookup("#ButtonConfirm").queryAs(Button.class);
        cancelDeleteButton = robot.lookup("#ButtonCancel").queryAs(Button.class);

        assertNotNull(medDetailsTable);
        assertNotNull(removeButton);
        assertNotNull(confirmDeleteButton);
        assertNotNull(cancelDeleteButton);
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
    void deleteMedicineConfirmed(FxRobot robot){

        robot.clickOn("#productNameTextField").write("Amoxicillin");
        robot.clickOn("#quantityTextField").write("20");
        robot.clickOn("#expirationDateTextField").write("2026-12-31");
        robot.clickOn("#ButtonConfirm");

        boolean added = medDetailsTable.getItems().stream()
                .anyMatch(m -> "Amoxicillin".equals(m.getItemName()));
        assertTrue(added);


        robot.interact(() -> medDetailsTable.getSelectionModel().select(0));
        robot.clickOn(removeButton);
        robot.clickOn(confirmDeleteButton);


        boolean exists = medDetailsTable.getItems().stream()
                .anyMatch(m -> "Amoxicillin".equals(m.getItemName()));
        assertFalse(exists);

        assertTrue(robot.lookup("Medicine successfully Deleted").tryQuery().isPresent());
    }


    @Disabled
    @Test
    void cancelDeleteKeepsMedicine(FxRobot robot){
        // Arrange: add a medicine
        robot.clickOn("#productNameTextField").write("Paracetamol");
        robot.clickOn("#quantityTextField").write("10");
        robot.clickOn("#expirationDateTextField").write("2027-05-05");
        robot.clickOn("#ButtonConfirm");

        boolean added = medDetailsTable.getItems().stream()
                .anyMatch(m -> "Paracetamol".equals(m.getItemName()));
        assertTrue(added);


        robot.interact(() -> medDetailsTable.getSelectionModel().select(0));
        robot.clickOn(removeButton);
        robot.clickOn(cancelDeleteButton);


        boolean exists = medDetailsTable.getItems().stream()
                .anyMatch(m -> "Paracetamol".equals(m.getItemName()));
        assertTrue(exists);
    }


    @Disabled
    @Test
    void deleteNonExistentMedicineShowsError(FxRobot robot){

        medDetailsTable.getItems().clear();

        robot.clickOn(removeButton);

        assertTrue(robot.lookup("No Items selected").tryQuery().isPresent());
        robot.clickOn("Ok");
    }
}
