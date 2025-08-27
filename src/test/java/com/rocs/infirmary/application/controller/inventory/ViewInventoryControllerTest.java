package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.List;
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
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(ApplicationExtension.class)
public class ViewInventoryControllerTest {

    private Button quantityButton;
    private  Button inventory_Filter_Button_A;
    private  Button inventory_Filter_Button_Z;
    private TextField searchTextField;
    private Button inventoryClearFilterButton;
    private TableView<Medicine> medDetailsTable;


    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/InventoryPage.fxml"));
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup (FxRobot robot){
        quantityButton = robot.lookup("#QuantityButton").queryAs(Button.class);
        medDetailsTable = robot.lookup("#medDetailsTable").queryAs(TableView.class);
        inventory_Filter_Button_A = robot.lookup("#Inventory_Filter_Button_A").queryAs(Button.class);
        inventory_Filter_Button_Z = robot.lookup("#Inventory_Filter_Button_Z").queryAs(Button.class);
        inventoryClearFilterButton = robot.lookup("#InventoryClearFilterButton").queryAs(Button.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);

        assertNotNull(searchTextField);
        assertNotNull(quantityButton);
        assertNotNull(medDetailsTable);
        assertNotNull(inventory_Filter_Button_A);
        assertNotNull(inventory_Filter_Button_Z);
        assertNotNull(inventoryClearFilterButton);

    }

    @Disabled
    @Test
    public void testQuantityButton(FxRobot robot){
        robot.clickOn(quantityButton);
        assertEquals(medDetailsTable.getItems().stream().map(Medicine::getQuantity).toList(),
                medDetailsTable.getItems().stream().map(Medicine::getQuantity).sorted().toList());
    }

    @Disabled
    @Test
    public void testInventory_Filter_Button_A(FxRobot robot){
        robot.clickOn(inventory_Filter_Button_A);
        assertEquals(medDetailsTable.getItems().stream().map(Medicine::getItemName).toList(),
                medDetailsTable.getItems().stream().map(Medicine::getItemName).sorted().toList());
    }

    @Disabled
    @Test
    public void testInventory_Filter_Button_Z(FxRobot robot){
        robot.clickOn(inventory_Filter_Button_Z);
        assertEquals(medDetailsTable.getItems().stream().map(Medicine::getItemName).toList(),
                medDetailsTable.getItems().stream().map(Medicine::getItemName).sorted(Comparator.reverseOrder()).toList());
    }


    @Disabled
    @Test
    public void testInventoryClearFilterButton(FxRobot robot){
        List<String> originalArrangement = medDetailsTable.getItems().stream().map(Medicine::getItemName).toList();
        robot.clickOn(inventory_Filter_Button_A);
        robot.clickOn(inventoryClearFilterButton);
        List <String> afterFilterArrangment = medDetailsTable.getItems().stream().map(Medicine::getItemName).toList();
        assertEquals(afterFilterArrangment,originalArrangement);
    }

    @Disabled
    @Test
    public void testInventoryClearFilterButtonInTextField(FxRobot robot) {
        robot.clickOn(searchTextField);
        robot.write("Aspirin");
        robot.clickOn(inventoryClearFilterButton);
        assertEquals("", searchTextField.getText());
    }

    @Disabled
    @Test
    public void testSearchTextField(FxRobot robot){
        robot.clickOn(searchTextField);
        robot.write("Antacid");
        assertEquals("Antacid", searchTextField.getText());

        boolean found = medDetailsTable.getItems().stream().anyMatch(med -> "Antacid".equals(med.getItemName()));
        assertTrue(found);
        assertTrue(robot.lookup("Antacid").tryQuery().isPresent());
    }
}
