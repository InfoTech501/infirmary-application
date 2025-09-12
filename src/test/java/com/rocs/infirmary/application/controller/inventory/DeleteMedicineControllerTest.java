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

    private Button confirmButton;
    private Button cancelButton;
    private TableView<Medicine> medDetailsTable;

    @BeforeEach
    void setup(FxRobot robot){
        medDetailsTable = robot.lookup("#medDetailsTable").queryAs(TableView.class);
        confirmButton = robot.lookup("#ButtonConfirm").queryAs(Button.class);
        cancelButton = robot.lookup("#ButtonCancel").queryAs(Button.class);

        assertNotNull(medDetailsTable);
        assertNotNull(confirmButton);
        assertNotNull(cancelButton);
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/InventoryAddItemModal.fxml"));
        BorderPane mainLayout;
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }


    @Disabled
    @Test
    void testDeleteMedicine(FxRobot robot) {
        robot.clickOn(confirmButton);

        assertTrue(robot.lookup("Medicine successfully Deleted").tryQuery().isPresent());

    }


    @Disabled
    @Test
    void testDeleteConfirmation(FxRobot robot) {
        robot.clickOn(confirmButton);

        assertTrue(robot.lookup("Are you sure you want to delete?").tryQuery().isPresent());
    }


    @Disabled
    @Test
    void testCancelDeletion(FxRobot robot) {
        robot.clickOn(cancelButton);

        assertFalse(medDetailsTable.getItems().isEmpty() || medDetailsTable.getItems().isEmpty());
    }

    @Disabled
    @Test
    void testNoSelectedItems(FxRobot robot) {
        robot.interact(() -> medDetailsTable.getItems().clear());

        robot.clickOn(confirmButton);

        assertTrue(robot.lookup("Medicine doesn’t exist").tryQuery().isPresent());
    }

}
