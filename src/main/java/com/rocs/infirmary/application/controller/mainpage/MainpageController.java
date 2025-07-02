package com.rocs.infirmary.application.controller.mainpage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.rocs.infirmary.application.InfirmaryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainpageController implements Initializable {

    @FXML
    private ToggleButton accountBtn;

    @FXML
    private Button clinicVisitLogBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private ImageView dropdownAccountBtn;

    @FXML
    private VBox homepageScene;

    @FXML
    private BorderPane infirmaryContainerPage;

    @FXML
    private Button inventoryBtn;

    @FXML
    private VBox mainpageEnvironmentVbox;

    @FXML
    private Label pageLabel;

    @FXML
    private Button studentHealthProfileBtn;

    @FXML
    private ToggleButton notificationBtn;

    @FXML
    private Label welcomeText;


    @Override
    public void initialize (URL url, ResourceBundle rb) {
        loadDashboard();
    }

    public boolean loadDashboard() {
        try {
            switchScene("/views/DashboardPage.fxml");
//            Parent root = FXMLLoader.load(Objects.requireNonNull(InfirmaryApplication.class.getResource("/views/DashboardPage.fxml")));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
////            stage.setMinWidth(400);
////            stage.setMinHeight(600);
            pageLabel.setText(" Dashboard");
            dashboardBtn.isDefaultButton();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @FXML
    void setClinicVisitLogBtn() throws IOException {
        switchScene("/views/ClinicVisitLogPage.fxml");
        pageLabel.setText("Clinic Visit Log");
    }

    @FXML
    void setDashboardBtn() throws IOException {
        switchScene("/views/DashboardPage.fxml");
        pageLabel.setText("Dashboard");
    }

    @FXML
    void setStudentHealthProfileBtn() throws IOException {
        switchScene("/views/StudentHealthProfilePage.fxml");
        pageLabel.setText("Student Health Profile");
    }

    @FXML
    public void setInventoryBtn() throws IOException {
        switchScene("/views/InventoryPage.fxml");
        pageLabel.setText("Inventory");
    }

    private void switchScene(String fxmlFile) throws IOException {
        BorderPane nextVbox = FXMLLoader.load(Objects.requireNonNull(InfirmaryApplication.class.getResource(fxmlFile)));
        infirmaryContainerPage.setCenter(nextVbox);
    }
}