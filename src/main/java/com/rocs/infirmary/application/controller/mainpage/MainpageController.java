package com.rocs.infirmary.application.controller.mainpage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.rocs.infirmary.application.InfirmaryApplication;
import com.rocs.infirmary.application.controller.notification.helper.NotificationAlertHelper;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.lowstock.notification.service.application.LowStockNotificationServiceApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * {@code MainpageController} is used to handle navigation between different pages,
 * this implements Initializable interface
 **/
public class MainpageController implements Initializable {

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button clinicVisitLogBtn;

    @FXML
    private Button studentHealthProfileBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private StackPane homepageScene;

    @FXML
    private Label pageLabel;

    @FXML
    private ImageView redCircle;

    @FXML
    private ToggleButton toggleButton;

    private Button activeButton;
    private NotificationAlertHelper alertHelper = new NotificationAlertHelper() ;
    private final LowStockNotificationServiceApplication lowStockService = new LowStockNotificationServiceApplication();
    private final InventoryManagementApplication inventoryManagementApplication = new InventoryManagementApplication();

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        loadDashboard();
        alertHelper.bindService(lowStockService,inventoryManagementApplication);
        alertHelper.bindUI(redCircle,toggleButton);
        alertHelper.setMainNode(homepageScene);
        NotificationAlertHelper.getNotification();
    }

    private void loadDashboard() {
        try {
            switchScene("/views/DashboardPage.fxml");
            pageLabel.setText("Dashboard");
            setActiveButton(dashboardBtn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method handles the action triggered when the clinic visit log button is clicked.
     */
    @FXML
    void setClinicVisitLogBtn() throws IOException {
        switchScene("/views/ClinicVisitLogPage.fxml");
        pageLabel.setText("Clinic Visit Log");
        clinicVisitLogBtn.setDefaultButton(true);
        setActiveButton(clinicVisitLogBtn);
    }

    /**
     * this method handles the action triggered when the dashboard button is clicked.
     */
    @FXML
    void setDashboardBtn() throws IOException {
        switchScene("/views/DashboardPage.fxml");
        pageLabel.setText("Dashboard");
        setActiveButton(dashboardBtn);
    }

    /**
     * this method handles the action triggered when the student health profile button is clicked.
     */
    @FXML
    void setStudentHealthProfileBtn() throws IOException {
        switchScene("/views/StudentHealthProfilePage.fxml");
        pageLabel.setText("Student Health Profile");
        setActiveButton(studentHealthProfileBtn);
    }

    /**
     * this method handles the action triggered when the inventory button is clicked.
     */
    @FXML
    public void setInventoryBtn() throws IOException {
        switchScene("/views/InventoryPage.fxml");
        pageLabel.setText("Inventory");
        setActiveButton(inventoryBtn);
    }

    private void switchScene(String fxmlFile) throws IOException {
        homepageScene.getChildren().clear();
        Parent newPage = FXMLLoader.load(Objects.requireNonNull(InfirmaryApplication.class.getResource(fxmlFile)));
        homepageScene.getChildren().add(newPage);
    }

    private void setActiveButton(Button button){
        if(activeButton != null){
            activeButton.getStyleClass().remove("active");
        }
        activeButton = button;
        if(!button.getStyleClass().contains("active")){
            button.getStyleClass().add("active");
        }
    }
}