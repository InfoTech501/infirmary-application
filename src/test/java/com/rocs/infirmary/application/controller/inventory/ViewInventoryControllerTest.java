package com.rocs.infirmary.application.controller.inventory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(ApplicationExtension.class)
public class ViewInventoryControllerTest {

    private Button QuantityButton;
    private  Button Inventory_Filter_Button_A;
    private  Button Inventory_Filter_Button_Z;
    private TextField searchTextField;
    private Button InventoryClearFilterButton;


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
        QuantityButton = robot.lookup("#QuantityButton").queryAs(Button.class);
        Inventory_Filter_Button_A = robot.lookup("#Inventory_Filter_Button_A").queryAs(Button.class);
        Inventory_Filter_Button_Z = robot.lookup("#Inventory_Filter_Button_Z").queryAs(Button.class);
        InventoryClearFilterButton = robot.lookup("#InventoryClearFilterButton").queryAs(Button.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);

        assertNotNull(QuantityButton);
        assertNotNull(Inventory_Filter_Button_A);
        assertNotNull(Inventory_Filter_Button_Z);
        assertNotNull(InventoryClearFilterButton);
        assertNotNull(searchTextField);
    }

    @Disabled
    @Test
    public void viewInventoryTest(FxRobot robot) {

        // Test searching and filtering of items
        robot.clickOn(QuantityButton);
        robot.sleep(1000);
        robot.clickOn(Inventory_Filter_Button_A);
        robot.sleep(1000);
        robot.clickOn(Inventory_Filter_Button_Z);
        robot.sleep(1000);
        robot.clickOn(searchTextField);
        robot.sleep(1000);
        robot.write("Antacid");
        robot.sleep(1000);
        robot.clickOn(InventoryClearFilterButton);
        robot.sleep(1000);
        robot.clickOn(searchTextField);
        robot.sleep(1000);
        robot.write("Aspirin");
        robot.sleep(1000);
        robot.clickOn(InventoryClearFilterButton);
        robot.sleep(1000);
    }
}
