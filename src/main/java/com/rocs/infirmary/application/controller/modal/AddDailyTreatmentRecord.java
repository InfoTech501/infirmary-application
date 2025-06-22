package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDailyTreatmentRecord implements Initializable {

    @FXML
    public TextField LrnField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField gradeSectionField;
    @FXML
    public Label vitalSignsLabel;
    @FXML
    public TextField bodyTempField;
    @FXML
    public TextField pulseRateField;
    @FXML
    public TextField respiratoryRateField;
    @FXML
    public TextField bloodPressureField;
    @FXML
    public TextField chiefComplaintField;
    @FXML
    public TextField nurseInterventionField;
    @FXML
    public TextField symptomsField;
    @FXML
    public TextField medicineName;
    @FXML
    public TextField invDispensingOutField;
    @FXML
    public TextField dateTimeField;

    private ObservableList<Student> student;

    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();
    Student studentModel = new Student();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LrnField.requestFocus();
    }

    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();
            //medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().save(record);
            showAlert("Success", "Record added successfully");

            Stage stage = (Stage) LrnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert("Error", "Failed to save record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {
        if (LrnField.getText() == null || LrnField.getText().isBlank()) {
            showWarning("LRN cannot be empty");
        } else if (nameField.getText() == null || nameField.getText().isBlank()) {
            showWarning("Name cannot be empty");
        } else if (gradeSectionField.getText() == null || gradeSectionField.getText().isBlank()) {
            showWarning("Grade & Section cannot be empty");
        } else if (bodyTempField.getText() == null || bodyTempField.getText().isBlank()) {
            showWarning("Body temperature is required");
        } else if (pulseRateField.getText() == null || pulseRateField.getText().isBlank()) {
            showWarning("Pulse rate is required");
        } else if (respiratoryRateField.getText() == null || respiratoryRateField.getText().isBlank()) {
            showWarning("Respiratory rate is required");
        } else if (bloodPressureField.getText() == null || bloodPressureField.getText().isBlank()) {
            showWarning("Blood pressure is required");
        } else if (chiefComplaintField.getText() == null || chiefComplaintField.getText().isBlank()) {
            showWarning("Chief complaint is required");
        } else if (nurseInterventionField.getText() == null || nurseInterventionField.getText().isBlank()) {
            showWarning("Nurse intervention cannot be empty");
        } else if (symptomsField.getText() == null || symptomsField.getText().isBlank()) {
            showWarning("Symptoms field cannot be empty");
        } else if (medicineName.getText() == null || medicineName.getText().isBlank()) {
            showWarning("Medicine name is required");
        } else if (invDispensingOutField.getText() == null || invDispensingOutField.getText().isBlank()) {
            showWarning("Dispensing Out field cannot be empty");
        } else if (dateTimeField.getText() == null || dateTimeField.getText().isBlank()) {
            showWarning("Date and Time must be specified");
        } else {
            addDailyRecord();
        }
    }

        private void showWarning(String message) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Warning");
            dialog.setContentText(message);
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(okButton);
            dialog.showAndWait();
        }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }
    @FXML
    private Student createStudentMedicalRecordFromForm() {
        Student student = new Student();
        student.setLrn(Long.parseLong(LrnField.getText()));
        student.setFirstName(nameField.getText());
        student.setGradeLevel(gradeSectionField.getText());
        student.setTemperatureReadings(String.valueOf(Double.parseDouble(bodyTempField.getText())));
        student.setPulseRate(Integer.parseInt(pulseRateField.getText()));
        student.setRespiratoryRate(Integer.parseInt(respiratoryRateField.getText()));
        student.setBloodPressure(bloodPressureField.getText());
        student.setChiefComplaint(chiefComplaintField.getText());
        student.setNurseInCharge(nurseInterventionField.getText());
        student.setSymptoms(symptomsField.getText());
        //student.setMedicineName(medicineName.getText());
        //student.setMedicineDispensed(Integer.parseInt(invDispensingOutField.getText()));
        //student.setDateTime(dateTimeField.getText());
        return student;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}

