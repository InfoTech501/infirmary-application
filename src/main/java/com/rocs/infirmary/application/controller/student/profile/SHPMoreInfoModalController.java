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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Student Health Profile More Info modal dialog.
 * Displays detailed medical history and allows selection of medical records.
 * Implements Initializable interface.
 */
public class SHPMoreInfoModalController implements Initializable {
    @FXML
    private TableView<Student> clinicHistoryTableView;
    @FXML
    private TableColumn<Student, String> illnessColumn;
    @FXML
    private TableColumn<Student, String> dateColumn;
    @FXML
    private TableColumn<Student, String> medicationColumn;
    @FXML
    private TableColumn<Student, String> nurseColumn;
    //labels
    @FXML
    private Label studentFullNameLabel, ageLabel, addressLabel, contactNumberLabel, sexLabel, birthdateLabel;
    @FXML
    private Button editHealthInfoBtn;
    @FXML
    private StackPane rootModal;
    @FXML
    private VBox tableViewWrapper;
    @FXML
    private Button closeModalBtn;

    @FXML
    private TableColumn<Student, String> temperatureColumn;
    @FXML
    private TableColumn<Student, String> bloodPressureColumn;
    @FXML
    private TableColumn<Student, String> pulseRateColumn;
    @FXML
    private TableColumn<Student, String> respiratoryRateColumn;

    private static final Logger LOGGER = LoggerFactory.getLogger(SHPMoreInfoModalController.class);
    private final StudentMedicalRecordApplication studentMedicalRecordApplication = new StudentMedicalRecordApplication();
    private Student selectedStudentRecord;
    private StudentHealthProfileController parentController;

    /**
     * Sets the parent controller reference for communication with the parent view.
     *
     * @param parentController the StudentHealthProfileController instance that manages the main view
     */
    public void setParentController(StudentHealthProfileController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateClinicHistoryTable();
        editHealthInfoBtn.setOnAction(event -> switchSceneToEditHealthInfo());
        closeModalBtn.setOnAction(event -> closeModal());
    }

    /**
     * Sets the selected student and loads their medical records for display.
     * Initially disables the edit button until a specific record is selected.
     *
     * @param student the Student object containing student information and medical records
     */
    public void setSelectedStudent(Student student) {
        getMedicalRecords(student);
        setStudentLabelData(student);
        editHealthInfoBtn.setDisable(true);
        editHealthInfoBtn.setOpacity(0);
    }

    /**
     * A function which populates clinic history tableview columns by mapping each table column to the corresponding Student object properties.
     */
    public void populateClinicHistoryTable() {
        clinicHistoryTableView.setEditable(true);
        illnessColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureReadings"));
        bloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        pulseRateColumn.setCellValueFactory(new PropertyValueFactory<>("pulseRate"));
        respiratoryRateColumn.setCellValueFactory(new PropertyValueFactory<>("respiratoryRate"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        medicationColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        nurseColumn.setCellValueFactory(new PropertyValueFactory<>("nurseInChargeLastName"));

        LOGGER.info("Populating table");
    }

    private void setStudentLabelData(Student student) {
        final StringBuilder fullName = fullNameStringBuilder(student);

        studentFullNameLabel.setText(fullName.toString());
        ageLabel.setText(String.valueOf(student.getAge()));
        addressLabel.setText(student.getAddress() != null ? String.valueOf(student.getAddress()) : "");
        sexLabel.setText(student.getGender() != null ? String.valueOf(student.getGender()) : "");
        contactNumberLabel.setText(student.getContactNumber() != null ? String.valueOf(student.getContactNumber()) : "");
        birthdateLabel.setText(String.valueOf(student.getBirthdate()) != null ? String.valueOf(student.getBirthdate()) : "");

        getMedicalRecords(student);
        LOGGER.info("Medical records successfully set");
    }

    private StringBuilder fullNameStringBuilder(Student student) {
        String lastName = student.getLastName() != null ? parentController.firstLetterAutoCapitalization(student.getLastName()) : "";
        String firstName = student.getFirstName() != null ? parentController.firstLetterAutoCapitalization(student.getFirstName()) : "";
        String middleName = student.getMiddleName() != null ? parentController.firstLetterAutoCapitalization(student.getMiddleName()) : "";

        StringBuilder fullName = new StringBuilder();
        if (!lastName.isEmpty()) {
            fullName.append(lastName);
        }
        if (!firstName.isEmpty()){
            if (!fullName.isEmpty()) fullName.append(", ");
            fullName.append(firstName);
        }
        if (!middleName.isEmpty()) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(middleName);
        }
        return fullName;
    }

    /**
     * Retrieves and displays medical records for the specified student using their LRN.
     * Sets up row selection functionality to enable editing of individual records.
     *
     * @param studentLRN the Student object whose medical records will be retrieved using their LRN
     */
    public void getMedicalRecords(Student studentLRN) {
        try {
            if (studentLRN != null) {
                Student studentMedicalRecord = studentMedicalRecordApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(studentLRN.getLrn());
                ObservableList<Student> studentObservableList = FXCollections.observableArrayList(studentMedicalRecord);
                clinicHistoryTableView.setItems(studentObservableList);
                LOGGER.info("Getting medical info by LRN: Success");
            } else {
                LOGGER.warn("No records retrieved");
            }
        } catch (Exception e) {
            LOGGER.error("Error in getting medical records{}", String.valueOf(e));
        }

        clinicHistoryTableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    selectedStudentRecord = row.getItem();
                    editHealthInfoBtn.setDisable(false);
                    editHealthInfoBtn.setOpacity(1.0);
                    LOGGER.info("Row selected");
                }
            });
            return row;
        });
    }

    private void switchSceneToEditHealthInfo() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SHPMedicalRecords.fxml"));
           Parent root = loader.load();

           tableViewWrapper.getChildren().setAll(root);

           SHPMedicalRecordsController controller = loader.getController();
           controller.setSelectedStudentRecord(selectedStudentRecord);
           controller.setParentController(this.parentController);
           controller.setModalController(this);
           LOGGER.info("Switch scene successful");
       } catch (IOException e) {
           LOGGER.warn("Switching scene failure");
           throw new RuntimeException(e);
       }
    }

    /**
     * Closes the modal dialog by hiding, disabling it, and clearing it.
     */
    public void closeModal() {
        rootModal.setVisible(false);
        rootModal.setDisable(true);
        rootModal.getChildren().clear();
        LOGGER.info("Modal closed");
    }
}
