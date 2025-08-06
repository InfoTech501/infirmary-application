package com.rocs.infirmary.application.controller.records;

import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * {@code ClinicVisitLogPageController} is used to handle event processes of the Medical Record of the Student,
 * this implements Initializable interface
 **/
public class ClinicVisitLogPageController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicVisitLogPageController.class);
    @FXML
    private TableView<MedicalRecord> visitLogTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<MedicalRecord, String> gradeSectionColumn;
    @FXML
    private TableColumn<MedicalRecord, String> symptomsColumn;
    @FXML
    private TableColumn<MedicalRecord, String> visitDateColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    public ComboBox<Integer> rowsPerPageComboBox;
    @FXML
    public Label paginationLabel;
    @FXML
    public Label rowsPageLabel;
    private int rowsPerPage = 10;
    private int currentPage = 1;

    private List<MedicalRecord> fullStudentList = new ArrayList<>();
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        setupTableColumns();
        setupRowFactory();
        setupRowsPerPageSelector();
        refresh();
        updatePage();
        studentSearch();
    }

    /**
     * This method opens the modal window to add a new daily treatment record.
     * @param actionEvent triggered when the Add Entry button is clicked.
     */
    public void handleAddEntryButton(ActionEvent actionEvent) throws IOException {
        showModalAddEntry(actionEvent,"/views/AddDailyTreatmentRecord.fxml");
    }

    /**
     * This method adds a new student record to the current page and refreshes the TableView.
     * @param newRecord the student record to add.
     */
    public void addStudentMedicalRecord(Student newRecord) {
        if (newRecord != null) {
            List<MedicalRecord> records = medicalRecordInfoMgtApplication
                    .getPatientMedicalRecordFacade().getAllPatientMedicalRecords();

            fullStudentList = records != null ? records : new ArrayList<>();
            updatePage();
        }
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
        int maxPage = (int) Math.ceil((double) fullStudentList.size() / rowsPerPage);

        if (currentPage < maxPage) {
            currentPage++;
            updatePage();
        }
    }

    private void setup() {
        nameColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            String fullName = student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName();
            return new SimpleStringProperty(fullName);
        });
        gradeSectionColumn.setCellValueFactory(cellData -> {
            String grade = cellData.getValue().getGradeLevel();
            String section = cellData.getValue().getSection();
            return new SimpleStringProperty(grade + " - " + section);
        });
        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        visitDateColumn.setCellValueFactory(cellData -> {
            MedicalRecord patient = (MedicalRecord) cellData.getValue();
            Date visitDate = patient.getVisitDate();
            String formatted = visitDate != null ? new SimpleDateFormat("MMM dd, yyyy").format(visitDate) : "N/A";
            return new SimpleStringProperty(formatted);
        });
        visitDateColumn.setStyle("-fx-alignment: CENTER;");

    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(cellData -> {
            Student s = cellData.getValue();
            String fullName = Stream.of(s.getFirstName(), s.getMiddleName(), s.getLastName())
                    .filter(part -> part != null && !part.isBlank())
                    .map(String::trim)
                    .collect(Collectors.joining(" "));
            return new SimpleStringProperty(fullName);
        });

        gradeSectionColumn.setCellValueFactory(cellData -> {
            String grade = cellData.getValue().getGradeLevel();
            String section = cellData.getValue().getSection();
            return new SimpleStringProperty(grade + " - " + section);
        });
        gradeSectionColumn.setStyle("-fx-alignment: CENTER;");

        symptomsColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));
        symptomsColumn.setStyle("-fx-alignment: CENTER;");

        visitDateColumn.setCellValueFactory(cellData -> {
            MedicalRecord patient = (MedicalRecord) cellData.getValue();
            Date visitDate = patient.getVisitDate();
            String formatted = visitDate != null ? new SimpleDateFormat("MMM dd, yyyy").format(visitDate) : "N/A";
            return new SimpleStringProperty(formatted);
        });

        visitDateColumn.setStyle("-fx-alignment: CENTER;");
    }

    private void setupRowFactory() {
        visitLogTable.setRowFactory(tv -> {
            TableRow<MedicalRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    MedicalRecord selectedPatient = row.getItem();
                    openViewStudentVisitLogModal(selectedPatient);
                }
            });
            return row;
        });
    }

    private void setupRowsPerPageSelector() {
        List<Integer> rowOptions = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());

        rowsPerPageComboBox.setItems(FXCollections.observableArrayList(rowOptions));
        rowsPerPageComboBox.setValue(rowsPerPage);

        rowsPerPageComboBox.setOnAction(event -> {
            Integer selectedRows = rowsPerPageComboBox.getSelectionModel().getSelectedItem();
            if (selectedRows != null) {
                rowsPerPage = selectedRows;
                currentPage = 1;
                updatePage();
            }
        });
    }

    private void refresh() {
        fullStudentList = medicalRecordInfoMgtApplication
                .getPatientMedicalRecordFacade().getAllPatientMedicalRecords();
        updatePage();
    }

    private void updatePage() {
        List<MedicalRecord> studentList = fullStudentList != null ? fullStudentList : new ArrayList<>();
        int total = studentList.size();
        int fromIndex = (currentPage - 1) * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, total);

        List<MedicalRecord> pageData = studentList.subList(fromIndex, toIndex);
        ObservableList<MedicalRecord> pageItems = FXCollections.observableArrayList(pageData);
        visitLogTable.setItems(pageItems);

        searchTextField.setText("");
        studentSearch();

        int displayedCount = pageItems.size();
        paginationLabel.setText((fromIndex + 1) + " - " + toIndex + " of " + total);
        rowsPageLabel.setText(String.valueOf(displayedCount));
    }

    private void studentSearch() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) {
                updatePage();
                return;
            }

            ObservableList<MedicalRecord> tableItems = visitLogTable.getItems();
            if (tableItems == null || tableItems.isEmpty()) return;

            FilteredList<MedicalRecord> filteredList = new FilteredList<>(tableItems, s -> true);
            filteredList.setPredicate(student -> {
                String keyword = newValue.toLowerCase();

                String fullName = (student.getFirstName() + " " +
                        student.getMiddleName() + " " +
                        student.getLastName()).toLowerCase();

                String gradeSection = (student.getGradeLevel() + " - " +
                        student.getSection()).toLowerCase();

                String symptoms = student.getSymptoms() != null ? student.getSymptoms().toLowerCase() : "";

                String visitDate = student.getVisitDate() != null
                        ? new SimpleDateFormat("MMMM dd, yyyy").format(student.getVisitDate()).toLowerCase()
                        : "";

                return fullName.contains(keyword)
                        || gradeSection.contains(keyword)
                        || symptoms.contains(keyword)
                        || visitDate.contains(keyword);
            });

            SortedList<MedicalRecord> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(visitLogTable.comparatorProperty());
            visitLogTable.setItems(sortedList);
        });
    }


    private void showModalAddEntry(ActionEvent actionEvent, String location) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent root = loader.load();

        AddDailyTreatmentRecordController controller = loader.getController();
        controller.setClinicVisitLogPageController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void openViewStudentVisitLogModal(MedicalRecord medicalRecord) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViewStudentVisitLog.fxml"));
            Parent root = loader.load();

            ViewStudentVisitLogController controller = loader.getController();
            controller.setStudentData(medicalRecord);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            LOGGER.error("Error opening visit log modal for LRN '{}'",
                    medicalRecord != null ? medicalRecord.getLrn() : "unknown", e);
        }

    }

}

