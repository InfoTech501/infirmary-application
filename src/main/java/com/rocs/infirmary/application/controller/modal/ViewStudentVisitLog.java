package com.rocs.infirmary.application.controller.modal;

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
public class ViewStudentVisitLog implements Initializable {

    @FXML
    private Label viewLrn;
    @FXML
    private Label viewFirstname;
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
     * @param student the Student object to be displayed.
     */
    public void setStudentData(Student student) {
        if (student == null) {
            return;
        }

        viewLrn.setText(formatValue(student.getLrn()));
        viewFirstname.setText(formatValue(student.getFirstName()));
        viewLastname.setText(formatValue(student.getLastName()));
        viewSex.setText(formatValue(student.getGender()));
        viewAge.setText(formatValue(student.getAge()));
        viewGradeSection.setText(formatValue(student.getGradeLevel() + " - " + student.getSection()));
        viewContactNum.setText(formatValue(student.getContactNumber()));
        viewHomeAdd.setText(formatValue(student.getAddress()));
        viewEmailAdd.setText(formatValue(student.getEmail()));
        viewBodyTemp.setText(formatValue(student.getTemperatureReadings()));
        viewPulseRate.setText(formatValue(student.getPulseRate()));
        viewRespiratoryRate.setText(formatValue(student.getRespiratoryRate()));
        viewBloodPressure.setText(formatValue(student.getBloodPressure()) + " mmHg");
        viewSymptoms.setText(formatValue(student.getSymptoms()));
        viewNurseIntervention.setText(formatValue(student.getNurseInCharge()));
        viewTreatment.setText(formatValue(student.getTreatment()));
        viewMedicineName.setText(formatValue(student.getMedicineName()));
        viewDispensingOut.setText(formatValue(student.getDispensingOut()));
        if (student.getVisitDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
            viewVisitDate.setText(sdf.format(student.getVisitDate()));
        } else {
            viewVisitDate.setText("N/A");
        }
    }

    /**
     * This method converts the given value to a string or "N/A" if null.
     * @param value the object to format.
     * @return the formatted string.
     */
    private String formatValue(Object value) {
        return value != null ? String.valueOf(value) : "N/A";
    }

    /**
     * This method handles the action triggered when the cancel button is clicked.
     * @param actionEvent the event triggered by the confirm button click
     */
    public void handleCloseButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}