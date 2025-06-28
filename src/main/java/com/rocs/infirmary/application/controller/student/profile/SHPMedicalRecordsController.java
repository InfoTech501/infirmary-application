package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.StudentMedicalRecordApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SHPMedicalRecordsController implements Initializable {
    @FXML
    public Label illnessLabel;
    @FXML
    public Label visitDateLabel;
    @FXML
    public Label givenMedicationLabel;
    @FXML
    public Label temperatureLabel;
    @FXML
    public Label bloodPressureLabel;
    @FXML
    public Label pulseRateLabel;
    @FXML
    public Label respiratoryRate;
    @FXML
    public Label treatmentLabel;
    @FXML
    public TextField updateIllnessTextField;
    @FXML
    public TextField updateTemperatureTextField;
    @FXML
    public TextField updateTreatmentTextField;
    @FXML
    public DatePicker updateVisitDatePicker;

    @FXML
    public Button confirmChangesBtn;
    @FXML
    public Button deleteMedicalRecordBtn;

    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();
    private Student student;

    private StudentHealthProfileController parentController;

    public void setParentController(StudentHealthProfileController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmChangesBtn.setOnAction(event -> confirmChangesBtnClicked());
        deleteMedicalRecordBtn.setOnAction(event -> confirmDeletion());
    }

    public void setSelectedStudentRecord(Student student) {
        this.student = student;
        Student record = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(student.getLrn());

        if (record != null) {
            illnessLabel.setText(student.getSymptoms());
            visitDateLabel.setText(String.valueOf(student.getVisitDate()));
            temperatureLabel.setText(student.getTemperatureReadings());
            bloodPressureLabel.setText(student.getBloodPressure());
            pulseRateLabel.setText(String.valueOf(student.getPulseRate()));
            respiratoryRate.setText(String.valueOf(student.getRespiratoryRate()));
            treatmentLabel.setText(student.getTreatment());
        } else {
            illnessLabel.setText("No record found");
        }
    }

    public void confirmChangesBtnClicked() {
        if (student != null) {
            handleRecordUpdate(student);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student record selected.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void handleRecordUpdate(Student student) {
        boolean updated = studentMedicalRecordApplication.getStudentMedicalRecordFacade().updateStudentMedicalRecord(
                updateIllnessTextField.getText(),
                updateTemperatureTextField.getText(),
                java.sql.Date.valueOf(updateVisitDatePicker.getValue()),
                updateTreatmentTextField.getText(),
                student.getLrn()
        );

        if (updated){
            parentController.fetch();
            Student updatedStudent = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(student.getLrn());
            setSelectedStudentRecord(updatedStudent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update medical record. Please try again.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void confirmDeletion() {
        if (student != null) {
            handleRecordDeletion(student);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student record selected.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void handleRecordDeletion(Student student) {
        if (student != null) {
            boolean isDeleted = studentMedicalRecordApplication.getStudentMedicalRecordFacade().deleteStudentMedicalRecordByLrn(student.getLrn());
            Alert alert;
            if (isDeleted) {
                parentController.fetch();
                alert = new Alert(Alert.AlertType.INFORMATION, "Medical record deleted successfully.", ButtonType.OK);
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "Failed to delete medical record. Please try again.", ButtonType.OK);
            }
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No student record selected for deletion.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
