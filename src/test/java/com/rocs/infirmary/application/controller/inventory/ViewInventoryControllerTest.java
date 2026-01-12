package com.rocs.infirmary.application.controller.inventory;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.controller.lowstock.helper.LowStockAlertHelper;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import com.rocs.infirmary.application.module.lowstock.notification.service.application.LowStockNotificationServiceApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class ViewInventoryControllerTest {

    private Button quantityButton;
    private Button inventoryFilterButtonA;
    private Button inventoryFilterButtonZ;
    private TextField searchTextField;
    private Button inventoryClearFilterButton;
    private TableView<Medicine> medDetailsTable;

    @Start
    public void start(Stage stage) throws IOException {

        LowStockNotificationServiceApplication lowStockNotificationServiceApplicationMock = mock(LowStockNotificationServiceApplication.class);
        DashboardFacade dashboardFacadeMock = mock(DashboardFacade.class);

        when(lowStockNotificationServiceApplicationMock.getDashboardFacade()).thenReturn(dashboardFacadeMock);
        when(dashboardFacadeMock.getAllLowStockMedicine()).thenReturn(Collections.emptyList());

        LowStockAlertHelper lowStockAlertHelper = new LowStockAlertHelper();
        lowStockAlertHelper.bindService(lowStockNotificationServiceApplicationMock);
        lowStockAlertHelper.bindUI(new ImageView(), new ToggleButton());
        lowStockAlertHelper.setMainNode(new Pane());


        LowStockAlertHelper.checkLowStockAndShowAlert();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/InventoryPage.fxml"));
        StackPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup(FxRobot robot) {

        quantityButton = robot.lookup("#quantityButton1").queryAs(Button.class);
        medDetailsTable = robot.lookup("#medDetailsTable").queryAs(TableView.class);
        inventoryFilterButtonA = robot.lookup("#inventoryFilterButtonA1").queryAs(Button.class);
        inventoryFilterButtonZ = robot.lookup("#inventoryFilterButtonZ1").queryAs(Button.class);
        inventoryClearFilterButton = robot.lookup("#inventoryClearFilterButton1").queryAs(Button.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);

        assertNotNull(searchTextField);
        assertNotNull(quantityButton);
        assertNotNull(medDetailsTable);
        assertNotNull(inventoryFilterButtonA);
        assertNotNull(inventoryFilterButtonZ);
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
    public void testInventoryFilterButtonA(FxRobot robot){
        robot.clickOn(inventoryFilterButtonA);
        assertEquals(medDetailsTable.getItems().stream().map(Medicine::getItemName).toList(),
                medDetailsTable.getItems().stream().map(Medicine::getItemName).sorted().toList());
    }

    @Disabled
    @Test
    public void testInventoryFilterButtonZ(FxRobot robot){
        robot.clickOn(inventoryFilterButtonZ);
        assertEquals(medDetailsTable.getItems().stream().map(Medicine::getItemName).toList(),
                medDetailsTable.getItems().stream().map(Medicine::getItemName).sorted(Comparator.reverseOrder()).toList());
    }


    @Disabled
    @Test
    public void testInventoryClearFilterButton(FxRobot robot){
        List<String> originalArrangement = medDetailsTable.getItems().stream().map(Medicine::getItemName).toList();
        robot.clickOn(inventoryFilterButtonA);
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
