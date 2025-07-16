package com.rocs.infirmary.application.controller.mainpage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MainpageControllerTest {

    private Label pageLabel;
    private StackPane homepageScene;
    private Button dashboardBtn;
    private Button inventoryBtn;
    private Button clinicVisitLogBtn;
    private Button studentHealthProfileBtn;

    @Start
    private void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Mainpage.fxml"));
        BorderPane mainLayout;
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup(FxRobot robot) {
        pageLabel = robot.lookup("#pageLabel").queryAs(Label.class);
        homepageScene = robot.lookup("#homepageScene").queryAs(StackPane.class);
        dashboardBtn = robot.lookup("#dashboardBtn").queryAs(Button.class);
        inventoryBtn = robot.lookup("#inventoryBtn").queryAs(Button.class);
        clinicVisitLogBtn = robot.lookup("#clinicVisitLogBtn").queryAs(Button.class);
        studentHealthProfileBtn = robot.lookup("#studentHealthProfileBtn").queryAs(Button.class);

        assertNotNull(pageLabel);
        assertNotNull(homepageScene);
        assertNotNull(dashboardBtn);
        assertNotNull(inventoryBtn);
        assertNotNull(clinicVisitLogBtn);
        assertNotNull(studentHealthProfileBtn);
    }
    @Disabled
    @Test
    void testInitialDashboardLoad(FxRobot robot) {
        assertEquals("Dashboard", pageLabel.getText());
    }

    @Disabled
    @Test
    void testInventoryButton(FxRobot robot) {
        robot.clickOn(inventoryBtn);
        assertEquals("Inventory", pageLabel.getText(), "Inventory page can't load ");}

    @Disabled
    @Test
    void testClinicVisitLogButton(FxRobot robot) {
        robot.clickOn(clinicVisitLogBtn);
        assertEquals("Clinic Visit Log", pageLabel.getText(), "Clinic Visit Log page can't load "); }

    @Disabled
    @Test
    void testStudentHealthProfileButton(FxRobot robot) {
        robot.clickOn(studentHealthProfileBtn);
        assertEquals("Student Health Profile", pageLabel.getText(), "Student Health Profile page can't load "); }

    @Disabled
    @Test
    void testDashboardButton(FxRobot robot) {
        robot.clickOn(dashboardBtn);
        assertEquals("Dashboard", pageLabel.getText(), "Dashboard page can't load ");
    }
}
