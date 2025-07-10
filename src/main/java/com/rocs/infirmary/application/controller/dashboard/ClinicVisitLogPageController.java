package com.rocs.infirmary.application.controller.dashboard;

import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.controller.modal.AddDailyTreatmentRecord;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * {@code ClinicVisitLogPageController} is used to handle event processes of the Medical Record of the Student,
 * this implements Initializable interface
 **/
public class ClinicVisitLogPageController implements Initializable {

    @FXML
    private TableView<Student> visitLogTable;
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
    @FXML
    public Label paginationLabel;
    @FXML
    public Label rowsPageLabel;
    private int rowsPerPage = 10;
    private int currentPage = 1;

    private List<Student> fullStudentList;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        refresh();
        studentSearch();
        updatePage();
        visitLogTable.setRowFactory(tv -> {
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
        GradeSectionColumn.setCellValueFactory(cellData -> {
            String grade = cellData.getValue().getGradeLevel();
            String section = cellData.getValue().getSection();
            return new javafx.beans.property.SimpleStringProperty(grade + " - " + section);
        });
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
        VisitDateColumn.setCellValueFactory(cellData -> {
            Date visitDate = cellData.getValue().getVisitDate();
            String formatted = visitDate != null
                    ? new SimpleDateFormat("MMMM dd, yyyy").format(visitDate)
                    : "N/A";
            return new SimpleStringProperty(formatted);
        });
        VisitDateColumn.setStyle("-fx-alignment: CENTER;");

    }

    private void refresh() {
        fullStudentList = medicalRecordInfoMgtApplication
                .getStudentMedicalRecordFacade()
                .getAllStudentMedicalRecords();

        updatePage();
    }

    /**
     * This method opens the modal window to add a new daily treatment record.
     * @param actionEvent triggered when the Add Entry button is clicked.
     */
    public void handleAddEntryButton(ActionEvent actionEvent) throws IOException {
        showModalAddEntry(actionEvent,"/views/AddDailyTreatmentRecord.fxml");
    }
    private void showModalAddEntry(ActionEvent actionEvent, String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();

        AddDailyTreatmentRecord controller = loader.getController();
        controller.setClinicVisitLogPageController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setTitle("Add Daily Treatment Record");
        stage.showAndWait();
    }

    private void studentSearch() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) {
                updatePage();
                return;
            }

            ObservableList<Student> tableItems = visitLogTable.getItems();
            if (tableItems == null || tableItems.isEmpty()) return;

            FilteredList<Student> filteredList = new FilteredList<>(tableItems, s -> true);

            filteredList.setPredicate(student -> {
                String keyword = newValue.toLowerCase();
                String fullName = (student.getFirstName() + " " +
                        student.getMiddleName() + " " +
                        student.getLastName()).toLowerCase();

                String grade = student.getGradeLevel() != null ? student.getGradeLevel().toLowerCase() : "";
                String section = student.getSection() != null ? student.getSection().toLowerCase() : "";
                String visitDate = student.getVisitDate() != null ? student.getVisitDate().toString().toLowerCase() : "";

                return String.valueOf(student.getStudentId()).contains(keyword)
                        || fullName.contains(keyword)
                        || grade.contains(keyword)
                        || section.contains(keyword)
                        || visitDate.contains(keyword);
            });

            SortedList<Student> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(visitLogTable.comparatorProperty());
            visitLogTable.setItems(sortedList);
        });
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

    /**
     * This method adds a new student record to the current page and refreshes the TableView.
     * @param newRecord the student record to add.
     */
    public void addStudentMedicalRecord(Student newRecord) {
        if (newRecord == null) return;

        fullStudentList = medicalRecordInfoMgtApplication
                .getStudentMedicalRecordFacade()
                .getAllStudentMedicalRecords();

        updatePage();
    }

    private void updatePage() {
        int total = fullStudentList.size();
        int fromIndex = (currentPage - 1) * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, total);

        List<Student> pageData = fullStudentList.subList(fromIndex, toIndex);
        ObservableList<Student> pageItems = FXCollections.observableArrayList(pageData);
        visitLogTable.setItems(pageItems);

        searchTextField.setText("");
        studentSearch();

        int displayedCount = pageItems.size();
        paginationLabel.setText((fromIndex + 1) + " - " + toIndex + " of " + total);
        rowsPageLabel.setText(String.valueOf(displayedCount));
    }

    @FXML
    private void handleToggleLeft(ActionEvent actionEvent) {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    @FXML
    private void handleToggleRight(ActionEvent actionEvent) {
        int totalRecords = fullStudentList != null ? fullStudentList.size() : 0;
        int maxPage = (int) Math.ceil((double) totalRecords / rowsPerPage);

        if (currentPage < maxPage) {
            currentPage++;
            updatePage();
        }
    }
}

