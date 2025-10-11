package com.rocs.infirmary.application.controller.records;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * {@code ViewStudentVisitLogController} is used to handle event processes of viewing
 * information of a student medical record, this implements Initializable interface
 */
public class ViewStudentVisitLogController implements Initializable {
    @FXML
    private StackPane addDailyTreatmentRecord;
    @FXML
    private Label viewLrn;
    @FXML
    private Label viewFirstname;
    @FXML
    private Label viewMiddleName;
    @FXML
    private Label viewLastname;
    @FXML
    private Label viewSex;
    @FXML
    private Label viewAge;
    @FXML
    private Label viewGradeSection;
    @FXML
    private Label viewContactNum;
    @FXML
    private Label viewHomeAdd;
    @FXML
    private Label viewEmailAdd;
    @FXML
    private Label viewBodyTemp;
    @FXML
    private Label viewPulseRate;
    @FXML
    private Label viewRespiratoryRate;
    @FXML
    private Label viewBloodPressure;
    @FXML
    private Label viewAilment;
    @FXML
    private Label viewNurseIntervention;
    @FXML
    private Label viewTreatment;
    @FXML
    private Label viewMedicineName;
    @FXML
    private Label viewDispensingOut;
    @FXML
    private Label viewVisitDate;

    private static final String TEMPERATURE_UNIT = "°C";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method displays student data on the form fields for viewing.
     *
     * @param medicalRecord the Student object to be displayed.
     */
    public void setStudentData(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            return;
        }

        viewLrn.setText(formatValue(medicalRecord.getLrn()));
        viewFirstname.setText(formatValue(medicalRecord.getFirstName()));
        viewMiddleName.setText(formatValue(medicalRecord.getMiddleName()));
        viewLastname.setText(formatValue(medicalRecord.getLastName()));
        viewSex.setText(formatValue(medicalRecord.getGender()));
        viewAge.setText(formatValue(medicalRecord.getAge()));
        viewGradeSection.setText(formatValue(medicalRecord.getGradeLevel() + " - " + medicalRecord.getSection()));
        viewContactNum.setText(formatValue(medicalRecord.getContactNumber()));
        viewHomeAdd.setText(formatValue(medicalRecord.getAddress()));
        viewEmailAdd.setText(formatValue(medicalRecord.getEmail()));
        viewBodyTemp.setText(formatValue(medicalRecord.getTemperatureReadings()) + " " + TEMPERATURE_UNIT);
        viewPulseRate.setText(formatValue(medicalRecord.getPulseRate()));
        viewRespiratoryRate.setText(formatValue(medicalRecord.getRespiratoryRate()));
        viewBloodPressure.setText(formatValue(medicalRecord.getBloodPressure()) + " mmHg");
        viewAilment.setText(formatValue(medicalRecord.getSymptoms()));
        viewNurseIntervention.setText(formatValue(medicalRecord.getNurseInCharge()));
        viewTreatment.setText(formatValue(medicalRecord.getTreatment()));
        viewMedicineName.setText(formatValue(medicalRecord.getMedicineName()));
        viewDispensingOut.setText(formatValue(medicalRecord.getDispensingOut()));
        if (medicalRecord.getVisitDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            viewVisitDate.setText(sdf.format(medicalRecord.getVisitDate()));
        } else {
            viewVisitDate.setText("");
        }
    }

    private String formatValue(Object value) {
        return value != null ? String.valueOf(value) : "";
    }

    @FXML
    private void handleCloseButton(ActionEvent actionEvent) {
        addDailyTreatmentRecord.setVisible(false);
        addDailyTreatmentRecord.setDisable(true);
        addDailyTreatmentRecord.getChildren().clear();
    }
}