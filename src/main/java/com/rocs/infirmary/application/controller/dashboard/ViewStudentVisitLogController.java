package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.data.model.person.student.Patient;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private Label viewSymptoms;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method displays student data on the form fields for viewing.
     * @param patient the Student object to be displayed.
     */
    public void setStudentData(Patient patient) {
        if (patient == null) {
            return;
        }

        viewLrn.setText(formatValue(patient.getLrn()));
        viewFirstname.setText(formatValue(patient.getFirstName()));
        viewMiddleName.setText(formatValue(patient.getMiddleName()));
        viewLastname.setText(formatValue(patient.getLastName()));
        viewSex.setText(formatValue(patient.getGender()));
        viewAge.setText(formatValue(patient.getAge()));
        viewGradeSection.setText(formatValue(patient.getGradeLevel() + " - " + patient.getSection()));
        viewContactNum.setText(formatValue(patient.getContactNumber()));
        viewHomeAdd.setText(formatValue(patient.getAddress()));
        viewEmailAdd.setText(formatValue(patient.getEmail()));
        viewBodyTemp.setText(formatValue(patient.getTemperatureReadings()));
        viewPulseRate.setText(formatValue(patient.getPulseRate()));
        viewRespiratoryRate.setText(formatValue(patient.getRespiratoryRate()));
        viewBloodPressure.setText(formatValue(patient.getBloodPressure()) + " mmHg");
        viewSymptoms.setText(formatValue(patient.getSymptoms()));
        viewNurseIntervention.setText(formatValue(patient.getNurseInCharge()));
        viewTreatment.setText(formatValue(patient.getTreatment()));
        viewMedicineName.setText(formatValue(patient.getMedicineName()));
        viewDispensingOut.setText(formatValue(patient.getDispensingOut()));
        if (patient.getVisitDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            viewVisitDate.setText(sdf.format(patient.getVisitDate()));
        } else {
            viewVisitDate.setText("");
        }
    }

    private String formatValue(Object value) {
        return value != null ? String.valueOf(value) : "";
    }

    /**
     * This method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void handleCloseButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}