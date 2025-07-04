package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * {@code AddDailyTreatmentRecordController} is used to handle event processes of adding new daily treatment record of a student
 * this implements Initializable interface
 **/
public class AddDailyTreatmentRecord implements Initializable {

    @FXML
    public TextField lrnField;
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
    public TextField medicineNameField;
    @FXML
    public TextField invDispensingOutField;
    @FXML
    public DatePicker datePickerTextField;

    private ObservableList<Student> studentList;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();
    private ClinicVisitLogPageController clinicVisitLogPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lrnField.requestFocus();
    }

    public void setClinicVisitLogPageController(ClinicVisitLogPageController controller) {
        this.clinicVisitLogPageController = controller;
    }
    /**
     * This method handles the action triggered when the confirm button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {
        addDailyRecord();
    }

    /**
     * This method saves a daily medical record if student exists based on student LRN
     * and shows warnings if not.
     */
    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();
            Student existing = medicalRecordInfoMgtApplication
                    .getStudentMedicalRecordFacade()
                    .getMedicalInformationByLRN(record.getLrn());

            if (existing != null) {
                record.setStudentId(existing.getStudentId());
            } else {
                showWarning("Student with this LRN was not found. Please register the student first.");
                return;
            }

            medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addStudentMedicalRecord(record);

            if (clinicVisitLogPageController != null) {
                clinicVisitLogPageController.addStudentMedicalRecord(record);
            }

            showWarning("Success, Record Added Successfully.");

            Stage stage = (Stage) lrnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showWarning("Error, Failed to save record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method builds a Student object using form input values.
     * @return a Student Medical Record.
     */
    private Student createStudentMedicalRecordFromForm() {
        Student student = new Student();
        student.setLrn(Long.parseLong(lrnField.getText()));
        String[] nameParts = nameField.getText().trim().split("\\s+");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String middleName = nameParts.length == 3 ? nameParts[1] : "";
        String lastName = nameParts.length == 3 ? nameParts[2] :
                (nameParts.length == 2 ? nameParts[1] : "");
        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);

        String[] parts = gradeSectionField.getText().split(" - ");
        String grade = parts.length > 0 ? parts[0].trim() : "";
        String section = parts.length > 1 ? parts[1].trim() : "";
        student.setGradeLevel(grade);
        student.setSection(section);

        student.setTemperatureReadings(String.valueOf(Double.parseDouble(bodyTempField.getText())));
        student.setPulseRate(Integer.parseInt(pulseRateField.getText()));
        student.setRespiratoryRate(Integer.parseInt(respiratoryRateField.getText()));
        student.setBloodPressure(bloodPressureField.getText());
        student.setSymptoms(symptomsField.getText());
        student.setNurseInCharge(nurseInChargeField.getText());
        student.setTreatment(treatmentField.getText());
        student.setMedicineName(medicineNameField.getText());
        student.setDispensingOut(Integer.parseInt(invDispensingOutField.getText()));
        LocalDate selectedDate = datePickerTextField.getValue();
        if (selectedDate != null) {
            Date convertedDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            student.setVisitDate(convertedDate);
        }
        return student;
    }

    /**
     * This method shows a warning dialog with a custom message.
     * @param message the warning text.
     */
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

}