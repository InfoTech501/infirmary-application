package com.rocs.infirmary.application.controller.page;

import com.rocs.infirmary.application.StudentHealthProfileInfoApplication;
import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MoreInformationStudentProfileController implements Initializable {

    @FXML
    private Label MoreInformationLabel;

    @FXML
    private Label ContactInfoLabel;

    @FXML
    private Label ContactNumLabel;

    @FXML
    private Label EmailAddressLabel;

    @FXML
    private Label AddressLabel;

    @FXML
    private Label StudentAdviserLabel;

    @FXML
    private Label HealthProbLabel;

    @FXML
    private Label heartDiseaseLabel;

    @FXML
    private TableView<Student> historyTable;

    @FXML
    private TableColumn<Student, String> illnessColumn;

    @FXML
    private TableColumn<Student, String> dateColumn;

    @FXML
    private TableColumn<Student, String> medicationColumn;

    @FXML
    private TableColumn<Student, String> nurseColumn;

    @FXML
    private Button ButtonEditHealthInfo;
    private StudentMedicalRecordFacade medicalRecordFacade;

    public void setMedicalRecordFacade(StudentMedicalRecordFacade medicalRecordFacade) {
        this.medicalRecordFacade = medicalRecordFacade;
    }

    private ObservableList<Student> medicalHistoryObservableList;

    private Student currentStudent; // Hold current student

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentHealthProfileInfoApplication app = new StudentHealthProfileInfoApplication();
        medicalRecordFacade = app.getStudentMedicalRecordFacade();

        initializeTableColumns();

        // Connect the edit button to open the edit window
        ButtonEditHealthInfo.setOnAction(e -> openEditStudentHealthProfile());
    }

    private void initializeTableColumns() {
        illnessColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSymptoms()));
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVisitDate() != null
                        ? cellData.getValue().getVisitDate().toString()
                        : "N/A"));
        medicationColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTreatment()));
        nurseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNurseInCharge()));
    }

    /**
     * This method can be called externally to load data for a specific student.
     */
    public void loadStudentProfileData(long lrn) {
        Student student = medicalRecordFacade.getMedicalInformationByLRN(lrn);

        if (student != null) {
            this.currentStudent = student; // store current student

            MoreInformationLabel.setText("Student Health Profile - " + student.getFirstName() + " " + student.getLastName());
            ContactInfoLabel.setText("Contact Info for: " + student.getFirstName() + " " + student.getLastName());
            ContactNumLabel.setText("Contact No: " + student.getContactNumber());
            EmailAddressLabel.setText("Email: " + student.getEmail());
            AddressLabel.setText("Address: " + student.getAddress());
            StudentAdviserLabel.setText("Adviser: " + student.getStudentAdviser());

            String symptoms = student.getSymptoms() != null ? student.getSymptoms() : "N/A";
            HealthProbLabel.setText("Health Problem: " + symptoms);
            heartDiseaseLabel.setText("1. Heart Disease: " + (symptoms.toLowerCase().contains("heart") ? "Yes" : "No"));

            List<Student> medicalHistory = (List<Student>) medicalRecordFacade.getMedicalInformationByLRN(lrn);
            medicalHistoryObservableList = FXCollections.observableArrayList(medicalHistory);
            historyTable.setItems(medicalHistoryObservableList);
        } else {
            showAlert("Student Not Found", "No student found for LRN: " + lrn);
        }
    }

    /**
     * Opens the EditStudentHealthProfile.fxml and passes the student's health data.
     */
    private void openEditStudentHealthProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rocs/infirmary/application/resources/views/EditStudentHealthProfile.fxml"));
            Parent root = loader.load();

            // Get the controller of the edit profile
            EditStudentHealthProfileController editController = loader.getController();

            // Pass the student's current health data
            if (currentStudent != null) {
                editController.setStudentHealthProblem(currentStudent.getSymptoms());
                editController.setStudent(currentStudent); // Optional: if you want the whole student
            }

            Stage stage = new Stage();
            stage.setTitle("Edit Student Health Profile");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not open Edit Student Health Profile window.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
