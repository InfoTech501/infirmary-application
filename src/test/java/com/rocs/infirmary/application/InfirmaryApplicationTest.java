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



        //check searhcing and filtering of items
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
//
//        //check adding of items
        clickOn("#InventoryAddItemButton");
        sleep(1000);
        clickOn("#ProductNameTextField");
        sleep(1000);
        write("Acetaminophen");
        sleep(1000);
        clickOn("#QuantityTextField");
        sleep(1000);
        write("60");
        sleep(1000);
        clickOn("#ExpirationDateTextField");
        write("2026-05-02");
        sleep(1000);

//
//
//        //check removing of items

//        Node firstRow = lookup(".table-row-cell").nth(0).query();
//        moveTo(firstRow).moveBy(-345, 12).clickOn();
//        sleep(1000);
//        clickOn("#InventoryRemoveItemsButton");
//        sleep(1000);
//        clickOn("#ButtonConfirm");
//        sleep(1000);
//
//
//

    }

}