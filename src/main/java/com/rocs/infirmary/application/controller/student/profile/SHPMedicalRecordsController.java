package com.rocs.infirmary.application.controller.student.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rocs.infirmary.application.module.student.record.StudentMedicalRecordApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Controller for the Student Medical Records management scene.
 * Displays detailed medical history and allows management of student medical records.
 * Implements Initializable interface.
 */
public class SHPMedicalRecordsController implements Initializable {
    @FXML
    private Label illnessLabel;
    @FXML
    private Label visitDateLabel;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label bloodPressureLabel;
    @FXML
    private Label pulseRateLabel;
    @FXML
    private Label respiratoryRate;
    @FXML
    private Label treatmentLabel;
    @FXML
    private TextField updateIllnessTextField;
    @FXML
    private TextField updateTemperatureTextField;
    @FXML
    private TextField updateTreatmentTextField;
    @FXML
    private DatePicker updateVisitDatePicker;

    @FXML
    private Button confirmChangesBtn;
    @FXML
    private Button deleteMedicalRecordBtn;

    private static final Logger LOGGER = LoggerFactory.getLogger(SHPMedicalRecordsController.class);
    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();
    private Student student;

    private StudentHealthProfileController parentController;
    private SHPMoreInfoModalController modalController;

    /**
     * Sets the parent controller reference for communication with the parent view.
     *
     * @param parentController the StudentHealthProfileController instance that manages the main view
     */
    public void setParentController(StudentHealthProfileController parentController) {
        this.parentController = parentController;
    }

    /**
     * Sets the modal controller reference to enable modal operations like closing the modal after a record deletion.
     *
     * @param modalController the SHPMoreInfoModalController instance that manages the modal dialog
     */
    public void setModalController(SHPMoreInfoModalController modalController) {
        this.modalController = modalController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmChangesBtn.setOnAction(event -> confirmChangesBtnClicked());
        deleteMedicalRecordBtn.setOnAction(event -> confirmDeletion());
    }

    /**
     * A function that sets the specific student record using the student LRN.
     *
     * @param student the Student object containing student information and medical records
     */
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

            LOGGER.info("Student data successfully set");
        } else {
            illnessLabel.setText("No record found");
            LOGGER.info("No record found");
        }
    }

    private void confirmChangesBtnClicked() {
        if (student != null) {
            handleRecordUpdate(student);
            LOGGER.info("Record update confirmed");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student record selected.", ButtonType.OK);
            LOGGER.warn("No student record selected");
            alert.showAndWait();
        }
    }

    private void handleRecordUpdate(Student student) {
        StringBuilder errorMessage = new StringBuilder();
        String illness = updateIllnessTextField.getText().trim();
        String temperature = updateTemperatureTextField.getText().trim();
        String treatment = updateTreatmentTextField.getText().trim();
        java.sql.Date visitDate = null;

        try {
            LocalDate selectedDate = updateVisitDatePicker.getValue();
            if (selectedDate != null) {
                visitDate = java.sql.Date.valueOf(selectedDate);
            }
        } catch (Exception e) {
            LOGGER.error("Error processing visit date: {}", e.getMessage());
            errorMessage.append("Invalid visit date format.\n");
        }

        if (illness.length() > 250) {
            errorMessage.append("Illness must be less than 250 characters.\n");
        } else if (hasInvalidCharacters(illness)) {
            errorMessage.append("Illness contains invalid characters.\n");
        }

        if (!isValidTemperature(temperature)) {
            errorMessage.append("Temperature must be a valid number between 30.0 and 50.0°C (e.g., 37.5).\n");
        }

        if (treatment.length() > 500) {
            errorMessage.append("Treatment must be less than 500 characters.\n");
        } else if (hasInvalidCharacters(treatment)) {
            errorMessage.append("Treatment contains invalid characters.\n");
        }

        if (isVisitDateInFuture(visitDate)) {
            errorMessage.append("Visit date cannot be in the future.\n");
        }

        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage.toString(), ButtonType.OK);
            alert.setTitle("Input error");
            LOGGER.warn("Input validation failed for medical records update: {}", errorMessage.toString().replace("\n", "; "));
            alert.showAndWait();
            return;
        }

        boolean updated = studentMedicalRecordApplication.getStudentMedicalRecordFacade().updateStudentMedicalRecord(
                illness,
                temperature,
                visitDate,
                treatment,
                student.getLrn()
        );

        Alert alert;
        if (updated) {
            parentController.fetch();
            alert = new Alert(Alert.AlertType.INFORMATION, "Medical record updated successfully.", ButtonType.OK);
            Student updatedStudent = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(student.getLrn());
            setSelectedStudentRecord(updatedStudent);
            LOGGER.info("Medical records updated successfully for student LRN: {}", student.getLrn());
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR, "Failed to update medical record. Please try again.", ButtonType.OK);
            LOGGER.error("Medical records update failed for student LRN: {}", student.getLrn());
            alert.showAndWait();
        }
    }

    private boolean hasInvalidCharacters(String text) {
        if (text == null) return false;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s\\-'.,()]");
        return pattern.matcher(text).find();
    }

    private boolean isValidTemperature(String temperature) {
        if (temperature == null || temperature.trim().isEmpty()) {
            return false;
        }
        try {
            double temp = Double.parseDouble(temperature);
            return temp >= 30.0 && temp <= 50.0;
        } catch (NumberFormatException e) {
            LOGGER.error("Number format exception{}", String.valueOf(e));
            return false;
        }
    }

    private boolean isVisitDateInFuture(java.sql.Date visitDate) {
        if (visitDate == null) return false;
        return visitDate.after(new java.sql.Date(System.currentTimeMillis()));
    }

    private void confirmDeletion() {
        if (student != null) {
            handleRecordDeletion(student);
            LOGGER.info("Deletion confirmed");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student record selected.", ButtonType.OK);
            LOGGER.warn("No records selected");
            alert.showAndWait();
        }
    }

    private void handleRecordDeletion(Student student) {
        if (student != null) {
            boolean isDeleted = studentMedicalRecordApplication.getStudentMedicalRecordFacade().deleteStudentMedicalRecordByLrn(student.getLrn());
            Alert alert;
            if (isDeleted) {
                parentController.fetch();
                modalController.closeModal();
                alert = new Alert(Alert.AlertType.INFORMATION, "Medical record deleted successfully.", ButtonType.OK);
                LOGGER.info("Record deletion successful");
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "Failed to delete medical record. Please try again.", ButtonType.OK);
                LOGGER.warn("Record deletion failure");
            }
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No student record selected for deletion.", ButtonType.OK);
            LOGGER.error("No student record selected for deletion");
            alert.showAndWait();
        }
    }
}
