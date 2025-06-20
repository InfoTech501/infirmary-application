package com.rocs.infirmary.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.TestFx;

class InfirmaryApplicationTest extends ApplicationTest {
    private final InfirmaryApplication infirmaryApplication = new InfirmaryApplication();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/views/InventoryPage.fxml"));

        BorderPane mainLayout;
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @TestFx
    public void inventoryPageTest() {


        //Test searching and filtering of items
        clickOn("#QuantityButton");
        sleep(1000);
        clickOn("#Inventory_Filter_Button_A");
        sleep(1000);
        clickOn("#Inventory_Filter_Button_Z");
        sleep(1000);
        clickOn("#SearchTextField");
        sleep(1000);
        write("Antacid");
        sleep(1000);
        clickOn("#InventoryClearFilterButton");
        sleep(1000);
        clickOn("#SearchTextField");
        sleep(1000);
        write("Aspirin");
        sleep(1000);
        clickOn("#InventoryClearFilterButton");
        sleep(1000);

    }

}