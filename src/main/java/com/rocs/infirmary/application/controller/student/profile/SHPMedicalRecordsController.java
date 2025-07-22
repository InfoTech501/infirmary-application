package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rocs.infirmary.application.module.student.record.StudentMedicalRecordApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.sql.Date;

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
    private Student selectedStudent;

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
     * @param records the Student object containing student information and medical records
     */
    public void setSelectedStudentRecord(List<Student> records) {
        if (records == null || records.isEmpty()) {
            LOGGER.warn("No records found");
            return;
        }
        Student studentMedicalRecord = records.getFirst();
        this.selectedStudent = studentMedicalRecord;
        setLabels(studentMedicalRecord);
        LOGGER.info("Student data successfully set");
    }

    private void setLabels(Student studentMedicalRecord) {
        illnessLabel.setText(getOrEmpty(studentMedicalRecord.getSymptoms()));
        visitDateLabel.setText(getOrEmpty(studentMedicalRecord.getVisitDate()));
        temperatureLabel.setText(getOrEmpty(studentMedicalRecord.getTemperatureReadings()));
        bloodPressureLabel.setText(getOrEmpty(studentMedicalRecord.getBloodPressure()));
        pulseRateLabel.setText(getOrEmpty(studentMedicalRecord.getPulseRate()));
        respiratoryRate.setText(getOrEmpty(studentMedicalRecord.getRespiratoryRate()));
        treatmentLabel.setText(getOrEmpty(studentMedicalRecord.getTreatment()));
    }

    private String getOrEmpty(Object value) {
        return value != null ? value.toString() : "";
    }

    private void confirmChangesBtnClicked() {
        if (selectedStudent != null) {
            Optional<ButtonType> result = ControllerHelper.alertAction("Confirm Update", "Are you sure you want to update this medical record?");
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES){
                try {
                    handleRecordUpdate(selectedStudent);
                    LOGGER.info("Medical record updated successfully for student: {}", selectedStudent.getFirstName());
                } catch (Exception e) {
                    ControllerHelper.showDialog("Update failed", "Error updating record\", \"An error occurred while updating the medical record. No records updated");
                    LOGGER.error("failed to update medical record for student: {}", selectedStudent.getFirstName(), e);
                }
            }
        } else {
            ControllerHelper.showDialog("Error", "No student record selected.");
            LOGGER.warn("No student record selected");
        }
    }

    private void handleRecordUpdate(Student student) {
        StringBuilder errorMessage = new StringBuilder();
        String illness = updateIllnessTextField.getText().trim();
        String temperature = updateTemperatureTextField.getText().trim();
        String treatment = updateTreatmentTextField.getText().trim();
        Date visitDate = null;

        try {
            LocalDate selectedDate = updateVisitDatePicker.getValue();
            if (selectedDate != null) {
                visitDate = Date.valueOf(selectedDate);
            }
        } catch (Exception e) {
            LOGGER.error("Error processing visit date: {}", e.getMessage());
            errorMessage.append("Invalid visit date format.\n");
        }

        if (!illness.isEmpty()) {
            if (illness.length() > 250) {
                errorMessage.append("Illness must be less than 250 characters.\n");
            } else if (hasInvalidCharacters(illness)) {
                errorMessage.append("Illness contains invalid characters.\n");
            }
        }


        if (!temperature.isEmpty() && !isValidTemperature(temperature)) {
            errorMessage.append("Temperature must be a valid number between 30.0 and 50.0°C (e.g., 37.5).\n");
        }

        if (!treatment.isEmpty()) {
            if (treatment.length() > 500) {
                errorMessage.append("Treatment must be less than 500 characters.\n");
            } else if (hasInvalidCharacters(treatment)) {
                errorMessage.append("Treatment contains invalid characters.\n");
            }
        }

        if (visitDate != null && isVisitDateInFuture(visitDate)) {
            errorMessage.append("Visit date cannot be in the future.\n");
        }

        if (illness.isEmpty() && temperature.isEmpty() && treatment.isEmpty() && visitDate == null) {
            errorMessage.append("Please provide at least one field to update.\n");
        }

        if (!errorMessage.isEmpty()) {
            ControllerHelper.showDialog("Input error", errorMessage.toString());
            LOGGER.warn("Input validation failed for medical records update: {}", errorMessage.toString().replace("\n", "; "));
            return;
        }

        boolean isUpdated = studentMedicalRecordApplication.getStudentMedicalRecordFacade().updateStudentMedicalRecord(
                illness.isEmpty() ? null : illness,
                temperature.isEmpty() ? null : temperature,
                visitDate,
                treatment.isEmpty() ? null : treatment,
                student.getMedicalRecordId()
        );

        if (isUpdated) {
            parentController.loadData();
            ControllerHelper.showDialog("Success", "Medical record updated successfully.");
            loadMedicalRecords(student);
            clearTextFields();
            LOGGER.info("Medical records updated successfully for student LRN: {}", student.getLrn());
        } else {
            ControllerHelper.showDialog("Error", "Failed to update medical record.");
            LOGGER.error("Medical records update failed for student LRN: {}", student.getLrn());
        }
    }

    private void loadMedicalRecords(Student student) {
        List<Student> updatedRecord = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalRecordById(student.getMedicalRecordId());
        setSelectedStudentRecord(updatedRecord);
    }

    private boolean hasInvalidCharacters(String text) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s\\-'.,()]");
        return pattern.matcher(text).find();
    }

    private boolean isValidTemperature(String temperature) {
        try {
            double temp = Double.parseDouble(temperature);
            return temp >= 30.0 && temp <= 50.0;
        } catch (NumberFormatException e) {
            LOGGER.error("Number format exception{}", String.valueOf(e));
            return false;
        }
    }

    private boolean isVisitDateInFuture(Date visitDate) {
        return visitDate.after(new Date(System.currentTimeMillis()));
    }

    private void clearTextFields() {
        updateIllnessTextField.clear();
        updateTemperatureTextField.clear();
        updateTreatmentTextField.clear();
        updateVisitDatePicker.setValue(null);
    }

    private void confirmDeletion() {
        if (selectedStudent != null) {
            Optional<ButtonType> result = ControllerHelper.alertAction("Confirm Deletion", "Are you sure you want to delete this medical record?");
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES){
                try {
                    handleRecordDeletion(selectedStudent);
                    ControllerHelper.showDialog("Success", "Medical record deleted successfully.");
                    LOGGER.info("Medical record deleted successfully for student: {}", selectedStudent.getFirstName());
                } catch (Exception e) {
                    ControllerHelper.showDialog("Error", "Error Deleting Record.");
                    LOGGER.error("failed to delete medical record for student: {}", selectedStudent.getFirstName(), e);
                }
            }
        } else {
            ControllerHelper.showDialog("Error", "No student record selected.");
            LOGGER.warn("No records selected");
        }
    }

    private void handleRecordDeletion(Student student) {
        if (student != null) {
            boolean isDeleted = studentMedicalRecordApplication.getStudentMedicalRecordFacade().deleteStudentMedicalRecordById(selectedStudent.getMedicalRecordId());
            if (isDeleted) {
                parentController.loadData();
                modalController.closeModal();
                LOGGER.info("Record deletion successful");
            } else {
                LOGGER.warn("Record deletion failure");
            }
        } else {
            LOGGER.error("No student record selected for deletion");
        }
    }
}
