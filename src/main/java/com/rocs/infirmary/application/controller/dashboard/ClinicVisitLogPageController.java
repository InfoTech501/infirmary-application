package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.controller.modal.ViewStudentVisitLog;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClinicVisitLogPageController implements Initializable {

    @FXML
    private TableView<Student> VisitLogTable;
    @FXML
    private TableColumn<Student, String> NameColumn;
    @FXML
    private TableColumn<Student, String> GradeSectionColumn;
    @FXML
    private TableColumn<Student, String> TempReadingsColumn;
    @FXML
    private TableColumn<Student, Integer> PulseRateColumn;
    @FXML
    private TableColumn<Student, String> BloodPressureColumn;
    @FXML
    private TableColumn<Student, String> SymptomsColumn;
    @FXML
    private TableColumn<Student, String> MedicineNameColumn;
    @FXML
    private TableColumn<Student, Integer> DispensingOutColumn;
    @FXML
    private TableColumn<Student, String> VisitDateColumn;
    @FXML
    private TextField searchTextField;

    private ObservableList<Student> student;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        studentSearch();
        VisitLogTable.setItems(student);

        VisitLogTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Student selectedStudent = row.getItem();
                    openViewStudentVisitLogModal(selectedStudent);
                }
            });
            return row;
        });

    }

    private void setup() {
        NameColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            String fullName = student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName();
            return new SimpleStringProperty(fullName);
        });
        NameColumn.setStyle("-fx-alignment: CENTER;");
        GradeSectionColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        GradeSectionColumn.setStyle("-fx-alignment: CENTER;");
        TempReadingsColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureReadings"));
        TempReadingsColumn.setStyle("-fx-alignment: CENTER;");
        PulseRateColumn.setCellValueFactory(new PropertyValueFactory<>("pulseRate"));
        PulseRateColumn.setStyle("-fx-alignment: CENTER;");
        BloodPressureColumn.setCellValueFactory(new PropertyValueFactory<>("bloodPressure"));
        BloodPressureColumn.setStyle("-fx-alignment: CENTER;");
        SymptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        SymptomsColumn.setStyle("-fx-alignment: CENTER;");
        MedicineNameColumn.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        MedicineNameColumn.setStyle("-fx-alignment: CENTER;");
        DispensingOutColumn.setCellValueFactory(new PropertyValueFactory<>("dispensingOut"));
        DispensingOutColumn.setStyle("-fx-alignment: CENTER;");
        VisitDateColumn.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        VisitDateColumn.setStyle("-fx-alignment: CENTER;");

    }
    private void refresh() {
        List<Student> studentList = medicalRecordInfoMgtApplication
                .getStudentMedicalRecordFacade()
                .getAllStudentMedicalRecords();

        student = FXCollections.observableArrayList(studentList);
        VisitLogTable.setItems(student);
    }

    private void showModalAddEntry(ActionEvent actionEvent,String location) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(location)));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }

    public void handleAddEntryButton(ActionEvent actionEvent) throws IOException {
        showModalAddEntry(actionEvent,"/views/AddDailyTreatmentRecord.fxml");
    }

    private void studentSearch() {
        FilteredList<Student> filteredList = new FilteredList<>(student, student -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(student -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return (String.valueOf(student.getStudentId()).toLowerCase().contains(searchKeyword)
                        || student.getFirstName().toLowerCase().contains(searchKeyword)
                        || student.getLastName().toLowerCase().contains(searchKeyword)
                        || student.getGradeLevel().toLowerCase().contains(searchKeyword));
            });
        });

        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(VisitLogTable.comparatorProperty());
        VisitLogTable.setItems(sortedList);
    }

    private void openViewStudentVisitLogModal(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViewStudentVisitLog.fxml"));
            Parent root = loader.load();

            ViewStudentVisitLog controller = loader.getController();
            controller.setStudentData(student);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Student Medical Record");
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addStudentMedicalRecord(Student record) {
        if (record == null) return;

        student.add(record);
        VisitLogTable.refresh(); 
    }

}

