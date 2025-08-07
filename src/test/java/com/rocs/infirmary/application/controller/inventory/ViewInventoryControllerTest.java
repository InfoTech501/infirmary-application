package com.rocs.infirmary.application.controller.inventory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class ViewInventoryControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/InventoryPage.fxml"));
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @Disabled
    @Test
    public void viewInventoryTest() {

        // Test searching and filtering of items
        clickOn("#QuantityButton");
        sleep(1000);
        clickOn("#Inventory_Filter_Button_A");
        sleep(1000);
        clickOn("#Inventory_Filter_Button_Z");
        sleep(1000);
        clickOn("#searchTextField");
        sleep(1000);
        write("Antacid");
        sleep(1000);
        clickOn("#InventoryClearFilterButton");
        sleep(1000);
        clickOn("#searchTextField");
        sleep(1000);
        write("Aspirin");
        sleep(1000);
        clickOn("#InventoryClearFilterButton");
        sleep(1000);
    }
}
