package com.rocs.infirmary.application.controller.dashboard;

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
import javafx.scene.Node;
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

/**
 * {@code ClinicVisitLogPageController} is used to handle event processes of the Medical Record of the Student,
 * this implements Initializable interface
 **/
public class ClinicVisitLogPageController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicVisitLogPageController.class);
    @FXML
    private TableView<Student> visitLogTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> gradeSectionColumn;
    @FXML
    private TableColumn<Student, String> symptomsColumn;
    @FXML
    private TableColumn<Student, String> visitDateColumn;
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

    private List<Student> fullStudentList;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
        setupTableColumns();
        setupRowFactory();
        setupRowsPerPageSelector();
        refresh();
        studentSearch();
        updatePage();
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
            Date visitDate = cellData.getValue().getVisitDate();
            String formatted = visitDate != null
                    ? new SimpleDateFormat("MMM dd, yyyy").format(visitDate)
                    : "N/A";
            return new SimpleStringProperty(formatted);
        });
        visitDateColumn.setStyle("-fx-alignment: CENTER;");

    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();

            List<String> nameParts = Arrays.asList(
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getLastName()
            );

            StringBuilder fullNameBuilder = new StringBuilder();
            for (String part : nameParts) {
                if (part != null && !part.isBlank()) {
                    if (fullNameBuilder.length() > 0) {
                        fullNameBuilder.append(" ");
                    }
                    fullNameBuilder.append(part.trim());
                }
            }
            String fullName = fullNameBuilder.toString();

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
            Date visitDate = cellData.getValue().getVisitDate();
            String formatted = visitDate != null ? new SimpleDateFormat("MMM dd, yyyy").format(visitDate) : "N/A";
            return new SimpleStringProperty(formatted);
        });
        visitDateColumn.setStyle("-fx-alignment: CENTER;");
    }

    private void setupRowFactory() {
        visitLogTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Student selectedStudent = row.getItem();
                    openViewStudentVisitLogModal(selectedStudent);
                }
            });
            return row;
        });
    }

    private void setupRowsPerPageSelector() {
        rowsPerPageComboBox.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10));
        rowsPerPageComboBox.setValue(rowsPerPage);

        rowsPerPageComboBox.setOnAction(event -> {
            Object selectedItem = rowsPerPageComboBox.getSelectionModel().getSelectedItem();
            if (selectedItem instanceof Integer selectedRows) {
                rowsPerPage = selectedRows;
                currentPage = 1;
                updatePage();
            }
        });
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

        AddDailyTreatmentRecordController controller = loader.getController();
        controller.setClinicVisitLogPageController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(root));
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

            SortedList<Student> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(visitLogTable.comparatorProperty());
            visitLogTable.setItems(sortedList);
        });
    }

    private void openViewStudentVisitLogModal(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViewStudentVisitLog.fxml"));
            Parent root = loader.load();

            ViewStudentVisitLogController controller = loader.getController();
            controller.setStudentData(student);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            LOGGER.error("Error opening visit log modal for LRN '{}'",
                    student != null ? student.getLrn() : "unknown", e);
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

