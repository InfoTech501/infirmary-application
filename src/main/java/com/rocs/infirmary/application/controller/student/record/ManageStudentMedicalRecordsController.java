package com.rocs.infirmary.application.controller.student.record;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileController;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileModalController;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.controller.student.record.MedicalRecordInputValidation;
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
import java.sql.Date;

/**
 * Controller for the Student Medical Records management scene.
 * Displays detailed medical history and allows management of student medical records.
 * Implements Initializable interface.
 */
public class ManageStudentMedicalRecordsController implements Initializable {
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

    private Student selectedStudent;
    private MedicalRecord selectedMedicalRecord;
    private final StudentHealthProfileController parentController;
    private final StudentHealthProfileModalController modalController;
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageStudentMedicalRecordsController.class);
    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();

    /**
     * Constructs a ManageStudentMedicalRecordsController with parent and modal controller.
     *
     * @param parentController the StudentHealthProfileController instance that manages the main view
     * @param modalController the StudentHealthProfileModalController instance that manages modal dialog.
     */
    public ManageStudentMedicalRecordsController(StudentHealthProfileController parentController, StudentHealthProfileModalController modalController) {
        this.parentController = parentController;
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
     * @param selectedMedicalRecord the MedicalRecord object containing medical records
     */
    public void setSelectedStudentRecord(Student student, MedicalRecord selectedMedicalRecord) {
        this.selectedStudent = student;
        if (student == null) {
            LOGGER.warn("No records found");
            return;
        }
        setLabels(selectedMedicalRecord);
        LOGGER.info("passed student record{}", selectedMedicalRecord);
    }

    private void setLabels(MedicalRecord medicalRecord) {
        illnessLabel.setText(getOrEmpty(medicalRecord.getSymptoms()));
        visitDateLabel.setText(getOrEmpty(medicalRecord.getVisitDate()));
        temperatureLabel.setText(getOrEmpty(medicalRecord.getTemperatureReadings()));
        bloodPressureLabel.setText(getOrEmpty(medicalRecord.getBloodPressure()));
        pulseRateLabel.setText(getOrEmpty(medicalRecord.getPulseRate()));
        respiratoryRate.setText(getOrEmpty(medicalRecord.getRespiratoryRate()));
        treatmentLabel.setText(getOrEmpty(medicalRecord.getTreatment()));
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
        String illness = updateIllnessTextField.getText().trim();
        String temperature = updateTemperatureTextField.getText().trim();
        String treatment = updateTreatmentTextField.getText().trim();
        LocalDate visitLocalDate = updateVisitDatePicker.getValue();
        Date visitDate = (visitLocalDate != null) ? Date.valueOf(visitLocalDate) : null;

        String validationErrors = MedicalRecordInputValidation.validateMedicalRecordInputs(
                illness, temperature, treatment, visitLocalDate
        );

        if (!validationErrors.isEmpty()) {
            ControllerHelper.showDialog("Input error", validationErrors);
            LOGGER.warn("Input validation failed: {}", validationErrors.replace("\n", "; "));
            return;
        }

        boolean isUpdated = studentMedicalRecordApplication.getStudentMedicalRecordFacade().updateStudentMedicalRecord(
                illness.isEmpty() ? null : illness,
                temperature.isEmpty() ? null : temperature,
                visitDate,
                treatment.isEmpty() ? null : treatment,
                student.getLrn()
        );

        if (isUpdated) {
            parentController.loadData();
            ControllerHelper.showDialog("Success", "Medical record updated successfully.");
            loadMedicalRecords(student);
            clearTextFields();
        } else {
            ControllerHelper.showDialog("Error", "Failed to update medical record.");
        }
    }

    private void loadMedicalRecords(Student student) {
        try {
            List<MedicalRecord> records = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(student.getLrn());
            if (records != null && !records.isEmpty()) {
                final MedicalRecord updatedRecord = getMedicalRecord(records);
                this.selectedMedicalRecord = updatedRecord;
                setLabels(updatedRecord);

                LOGGER.info("Medical record labels refreshed for: {}", student.getLrn());
            } else {
                LOGGER.warn("No medical records found for: {}", student.getLrn());
            }
        } catch (Exception e) {
            LOGGER.error("Error refreshing medical record data: {}", e.getMessage(), e);
        }
    }

    private MedicalRecord getMedicalRecord(List<MedicalRecord> records) {
        MedicalRecord updatedRecord = null;
        if (selectedMedicalRecord != null && selectedMedicalRecord.getMedicalRecordId() != null) {
            for (MedicalRecord record : records) {
                if (record.getMedicalRecordId() != null && record.getMedicalRecordId().equals(selectedMedicalRecord.getMedicalRecordId())) {
                    updatedRecord = record;
                    break;
                }
            }
        }
        if (updatedRecord == null) {
            updatedRecord = records.getFirst();
        }
        return updatedRecord;
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
            boolean isDeleted = studentMedicalRecordApplication.getStudentMedicalRecordFacade().deleteStudentMedicalRecordByLrn(student.getLrn());
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
