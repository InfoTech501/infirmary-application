package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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

    public void handleCloseButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void setStudentData(Student student) {
        if (student == null) {
            return;
        }

        viewLrn.setText(formatValue(student.getLrn()));
        viewFirstname.setText(formatValue(student.getFirstName()));
        viewLastname.setText(formatValue(student.getLastName()));
        viewSex.setText(formatValue(student.getGender()));
        viewAge.setText(formatValue(student.getAge()));
        viewGradeSection.setText(formatValue(student.getGradeLevel()));
        viewContactNum.setText(formatValue(student.getContactNumber()));
        viewHomeAdd.setText(formatValue(student.getAddress()));
        viewEmailAdd.setText(formatValue(student.getEmail()));
        viewBodyTemp.setText(formatValue(student.getTemperatureReadings()));
        viewPulseRate.setText(formatValue(student.getPulseRate()));
        viewRespiratoryRate.setText(formatValue(student.getRespiratoryRate()));
        viewBloodPressure.setText(formatValue(student.getBloodPressure()));
        viewSymptoms.setText(formatValue(student.getSymptoms()));
        viewNurseIntervention.setText(formatValue(student.getNurseInCharge()));
        viewTreatment.setText(formatValue(student.getTreatment()));
//        viewMedicineName.setText(formatValue(viewMedicineName));
//        viewDispensingOut.setText(formatValue(viewDispensingOut));
        viewVisitDate.setText(formatValue(student.getVisitDate()));
    }

    private String formatValue(Object value) {
        return value != null ? String.valueOf(value) : "N/A";
    }
}