package com.rocs.infirmary.application.controller.student.record;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileController;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileModalController;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.module.student.profile.StudentHealthProfileApplication;
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
    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();

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
        this.selectedMedicalRecord = selectedMedicalRecord;

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
          String illness = updateIllnessTextField.getText().trim();
          String temperature = updateTemperatureTextField.getText().trim();
          String treatment = updateTreatmentTextField.getText().trim();
          LocalDate visitLocalDate = updateVisitDatePicker.getValue();
          Date visitDate = (visitLocalDate != null) ? Date.valueOf(visitLocalDate) : null;

          String validationErrors = MedicalRecordUpdateInputValidation.validateMedicalRecordInputs(
                illness, temperature, treatment, visitLocalDate
          );

          if (!validationErrors.isEmpty()) {
              ControllerHelper.showDialog("Input error", validationErrors);
              LOGGER.warn("Input validation failed: {}", validationErrors.replace("\n", "; "));
              return;
          }

            Optional<ButtonType> result = ControllerHelper.alertAction("Confirm Update", "Are you sure you want to update this medical record?");
            if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES){
                try {
                    handleRecordUpdate(selectedStudent, illness, temperature, visitDate, treatment);
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

    private void handleRecordUpdate(Student student, String illness, String temperature, Date visitDate, String treatment) {
        boolean isUpdated = studentMedicalRecordApplication.getStudentMedicalRecordFacade().updateStudentMedicalRecord(
                illness.isEmpty() ? null : illness,
                temperature.isEmpty() ? null : temperature,
                visitDate,
                treatment.isEmpty() ? null : treatment,
                student.getLrn(),
                selectedMedicalRecord.getMedicalRecordId()
        );

        if (isUpdated) {
            parentController.loadData();
            ControllerHelper.showDialog("Success", "Medical record updated successfully.");

            refreshDataAfterUpdate(student);
            clearTextFields();
        } else {
            ControllerHelper.showDialog("Error", "Failed to update medical record.");
        }
    }

    private void refreshDataAfterUpdate(Student student) {
        try {
            LOGGER.info("Refreshing parent controller data...");
            parentController.loadData();

            LOGGER.info("Refreshing current view data...");
            refreshMedicalRecords(student);

            LOGGER.info("All data refreshed successfully after update");
        } catch (Exception e) {
            LOGGER.error("Error occurred while refreshing data after update: {}", e.getMessage(), e);
        }
    }

    private void refreshMedicalRecords(Student student) {
        try {
            List<MedicalRecord> records = studentHealthProfileApplication.getStudentHealthProfileFacade().getStudentHealthProfileByLRN(student.getLrn());
            if (records != null && !records.isEmpty()) {
                MedicalRecord updatedRecord = findMedicalRecordById(records, selectedMedicalRecord.getMedicalRecordId());
                if (updatedRecord != null) {
                    this.selectedMedicalRecord = updatedRecord;
                    setLabels(updatedRecord);
                    LOGGER.info("Current medical record refreshed successful: {}", updatedRecord.getMedicalRecordId());
                } else {
                    LOGGER.warn("Updated medical record not found for ID: {}", selectedMedicalRecord.getMedicalRecordId());
                    this.selectedMedicalRecord = records.getFirst();
                    setLabels(this.selectedMedicalRecord);
                }
            } else {
                LOGGER.warn("No medical records found during refresh for LRN: {}", student.getLrn());
            }
        } catch (Exception e) {
            LOGGER.error("Error refreshing current medical record: {}", e.getMessage(), e);
        }
    }

    private MedicalRecord findMedicalRecordById(List<MedicalRecord> records, Long medicalRecordId) {
        if (medicalRecordId == null || records == null ) {
              return null;
         }
        return records.stream().filter(record -> record.getMedicalRecordId() != null && record.getMedicalRecordId().equals(medicalRecordId)).findFirst().orElse(null);
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
            boolean isDeleted = studentMedicalRecordApplication.getStudentMedicalRecordFacade().deleteStudentMedicalRecordByLrn(selectedMedicalRecord.getMedicalRecordId());
            if (isDeleted) {
                 refreshDataAfterUpdate(student);
                modalController.closeModal();
                ControllerHelper.showDialog("Success", "Medical record deleted successfully.");
                LOGGER.info("Medical record deleted successfully for student: {}", selectedStudent.getFirstName());
            } else {
                ControllerHelper.showDialog("Failed to update", "Medical record not deleted.");
                LOGGER.warn("Record deletion failure");
            }
        } else {
            LOGGER.error("No student record selected for deletion");
        }
    }
}
