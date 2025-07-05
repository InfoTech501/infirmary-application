package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.module.student.record.StudentMedicalRecordApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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
    public VBox tableViewWrapper;
    @FXML
    public Button CloseModalBtn;

    @FXML
    public TableColumn<Student, String> TemperatureColumn;
    @FXML
    public TableColumn<Student, String> BloodPressureColumn;
    @FXML
    public TableColumn<Student, String> PulseRateColumn;
    @FXML
    public TableColumn<Student, String> RespiratoryRateColumn;

    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();
    private Student selectedStudentRecord;
    private StudentHealthProfileController parentController;

    public void setParentController(StudentHealthProfileController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateClinicHistoryTable();
        EditHealthInfoBtn.setOnAction(event -> switchSceneToEditHealthInfo());
        CloseModalBtn.setOnAction(event -> closeModal());
    }

    public void setSelectedStudent(Student student) {
        getMedicalRecords(student);
        setStudentLabelData(student);
        EditHealthInfoBtn.setDisable(true);
        EditHealthInfoBtn.setOpacity(0);
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

        ClinicHistoryTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    selectedStudentRecord = row.getItem();
                    EditHealthInfoBtn.setDisable(false);
                    EditHealthInfoBtn.setOpacity(1.0);
                }
            });
            return row;
        });


        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(studentList);
        ClinicHistoryTable.setItems(studentObservableList);
    }

    public void switchSceneToEditHealthInfo() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SHPMedicalRecords.fxml"));
           Parent root = loader.load();

           tableViewWrapper.getChildren().setAll(root);

           SHPMedicalRecordsController controller = loader.getController();
           controller.setSelectedStudentRecord(selectedStudentRecord);
           controller.setParentController(this.parentController);

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
