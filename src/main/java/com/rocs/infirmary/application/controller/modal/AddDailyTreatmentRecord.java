package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.controller.dashboard.ClinicVisitLogPageController;
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
    public TextField bodyTempField;
    @FXML
    public TextField pulseRateField;
    @FXML
    public TextField respiratoryRateField;
    @FXML
    public TextField bloodPressureField;
    @FXML
    public TextField symptomsField;
    @FXML
    public TextField nurseInChargeField;
    @FXML
    public TextField treatmentField;
    @FXML
    public TextField medicineName;
    @FXML
    public TextField invDispensingOutField;
    @FXML
    public TextField dateTimeField;

    private ObservableList<Student> studentList;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();
    private ClinicVisitLogPageController clinicVisitLogPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LrnField.requestFocus();
    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {
        if (validateFields()) {
           addDailyRecord();
        }
    }

    private boolean validateFields() {
        if (LrnField.getText() == null || LrnField.getText().isBlank()) {
            showWarning("LRN cannot be empty");
            return false;
        } else if (nameField.getText() == null || nameField.getText().isBlank()) {
            showWarning("Name cannot be empty");
            return false;
        } else if (gradeSectionField.getText() == null || gradeSectionField.getText().isBlank()) {
            showWarning("Grade & Section cannot be empty");
            return false;
        } else if (bodyTempField.getText() == null || bodyTempField.getText().isBlank()) {
            showWarning("Body temperature is required");
            return false;
        } else if (pulseRateField.getText() == null || pulseRateField.getText().isBlank()) {
            showWarning("Pulse rate is required");
            return false;
        } else if (respiratoryRateField.getText() == null || respiratoryRateField.getText().isBlank()) {
            showWarning("Respiratory rate is required");
            return false;
        } else if (bloodPressureField.getText() == null || bloodPressureField.getText().isBlank()) {
            showWarning("Blood pressure is required");
            return false;
        } else if (symptomsField.getText() == null || symptomsField.getText().isBlank()) {
            showWarning("Symptoms field cannot be empty");
            return false;
        } else if (nurseInChargeField.getText() == null || nurseInChargeField.getText().isBlank()) {
            showWarning("Nurse intervention cannot be empty");
            return false;
        } else if (treatmentField.getText() == null || treatmentField.getText().isBlank()) {
            showWarning("Treatment cannot be empty");
            return false;
        } else if (medicineName.getText() == null || medicineName.getText().isBlank()) {
            showWarning("Medicine name is required");
            return false;
        } else if (invDispensingOutField.getText() == null || invDispensingOutField.getText().isBlank()) {
            return false;
        } else if (dateTimeField.getText() == null || dateTimeField.getText().isBlank()) {
            showWarning("Date and Time must be specified");
            return false;
        }
        return true;
    }

    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();
            medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addStudentMedicalRecord(record);

            if (clinicVisitLogPageController != null) {
                clinicVisitLogPageController.addStudentMedicalRecord(record);
            }

            showWarning("Success, Record Added Successfully.");

            Stage stage = (Stage) LrnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showWarning("Error, Failed to save record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Student createStudentMedicalRecordFromForm() {
        Student student = new Student();
        student.setLrn(Long.parseLong(LrnField.getText()));
        student.setFirstName(nameField.getText());
        student.setGradeLevel(gradeSectionField.getText());
        student.setTemperatureReadings(String.valueOf(Double.parseDouble(bodyTempField.getText())));
        student.setPulseRate(Integer.parseInt(pulseRateField.getText()));
        student.setRespiratoryRate(Integer.parseInt(respiratoryRateField.getText()));
        student.setBloodPressure(bloodPressureField.getText());
        student.setSymptoms(symptomsField.getText());
        student.setNurseInCharge(nurseInChargeField.getText());
        student.setChiefComplaint(treatmentField.getText());
//        student.setMedicineName(medicineName.getText());
//        student.setMedicineDispensed(Integer.parseInt(invDispensingOutField.getText()));
//        student.setVisitDate(dateTimeField.getText());
        return student;
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

}