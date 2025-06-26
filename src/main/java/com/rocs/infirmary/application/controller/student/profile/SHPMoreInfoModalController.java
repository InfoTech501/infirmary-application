package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.StudentHealthProfileApplication;
import com.rocs.infirmary.application.StudentMedicalRecordApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SHPMoreInfoModalController implements Initializable {
    @FXML
    public TableView<Student> ClinicHistoryTable;
    @FXML
    public TableColumn<Student, String> IllnessColumn;
    @FXML
    public TableColumn<Student, String> DateColumn;
    @FXML
    public TableColumn<Student, String> MedicationColumn;
    @FXML
    public TableColumn<Student, String> NurseColumn;
    //labels
    @FXML
    public Label StudentFullNameLabel, AgeLabel, AddressLabel, ContactNumberLabel, SexLabel, BirthdateLabel;
    @FXML
    public Button EditHealthInfoBtn;
    @FXML
    public StackPane rootModal;
    @FXML
    public Button CloseModalBtn;
    @FXML
    public TableColumn<Student, String> MedHistoryColumn;
    @FXML
    public TableColumn<Student, String> TemperatureColumn;
    @FXML
    public TableColumn<Student, String> BloodPressureColumn;
    @FXML
    public TableColumn<Student, String> PulseRateColumn;
    @FXML
    public TableColumn<Student, String> RespiratoryRateColumn;

    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();
    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateClinicHistoryTable();
        EditHealthInfoBtn.setOnAction(event -> switchSceneToEditHealthInfo());
        CloseModalBtn.setOnAction(event -> closeModal());
    }

    public void setSelectedStudent(Student student) {
        getMedicalRecords(student);
        setStudentLabelData(student);
    }

    public void populateClinicHistoryTable() {
        ClinicHistoryTable.setEditable(true);
        IllnessColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        TemperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureReadings"));
        BloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        PulseRateColumn.setCellValueFactory(new PropertyValueFactory<>("pulseRate"));
        RespiratoryRateColumn.setCellValueFactory(new PropertyValueFactory<>("respiratoryRate"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        MedicationColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        NurseColumn.setCellValueFactory(new PropertyValueFactory<>("nurseInChargeLastName"));
    }

    public void setStudentLabelData(Student student) {
        StudentFullNameLabel.setText(student.getLastName() + ", " + student.getFirstName()+ " " + student.getMiddleName());
        AgeLabel.setText(String.valueOf(student.getAge()));
        AddressLabel.setText(student.getAddress());
        SexLabel.setText(student.getGender());
        ContactNumberLabel.setText(student.getContactNumber());
        BirthdateLabel.setText(String.valueOf(student.getBirthdate()));

        getMedicalRecords(student);
    }

    private void getMedicalRecords(Student studentLRN) {
        Student studentList = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(studentLRN.getLrn());
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(studentList);
        ClinicHistoryTable.setItems(studentObservableList);
    }

    public void switchSceneToEditHealthInfo() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditStudentHealthModal.fxml"));
           Parent root = loader.load();

           rootModal.getChildren().setAll(root);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    public void closeModal() {
        rootModal.setVisible(false);
        rootModal.setDisable(true);
        rootModal.getChildren().clear();
    }
}
