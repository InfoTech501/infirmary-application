package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.module.student.profile.StudentHealthProfileApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Student Health Profile.
 * Displays common students data and allows selection of records.
 * It also allows the filtering of the students records.
 * Implements Initializable interface.
 */
public class StudentHealthProfileController implements Initializable {

    // table
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> lrnColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> gradeColumn;
    @FXML
    private TableColumn<Student, String> sectionColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> ageColumn;
    @FXML
    private TableColumn<Student, String> adviserColumn;

    // search
    @FXML
    private TextField searchTextField;

    // control buttons
    @FXML
    private ComboBox<String> sectionComboBox, sexComboBox;
    @FXML
    private Button ageFilterBtn, aToZFilterBtn, zToAFilterBtn, clearFilterBtn;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private BorderPane mainBorderPane;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileController.class);
    private ObservableList<Student> students;
    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        students = FXCollections.observableArrayList();

        aToZFilterBtn.setOnAction(event -> sortAToZ());
        zToAFilterBtn.setOnAction(event -> sortZToA());
        ageFilterBtn.setOnAction(event -> sortByAge());
        clearFilterBtn.setOnAction(event -> clearFilter());

        populateTableList();
        fetch();
        search();
        sortBySex();
    }

    /**
     * A public method which populates student tableview columns by mapping each table column to the corresponding Student object properties.
     */
    public void populateTableList() {
        studentTableView.setEditable(true);

        lrnColumn.setCellValueFactory(new PropertyValueFactory<>("lrn"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        adviserColumn.setCellValueFactory(new PropertyValueFactory<>("studentAdviser"));

        ObservableList<String> sectionNames = FXCollections.observableArrayList("Sections");
        sectionComboBox.setItems(sectionNames);

        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
        sexComboBox.setItems(genders);

        LOGGER.info("Populating student table");
    }

    /**
     * A method which fetches the students' medical records.
     * This method also checks the students' medical record status if it is active or inactive.
     */
    public void fetch() {
        try {
            List<Student> studentList = studentHealthProfileApplication.getStudentHealthProfileFacade().getAllStudentHealthProfile();
            List<Student> activeRecords = studentList.stream().filter(student -> student.getMedicalRecordStatus() == 1).toList();

            students.setAll(activeRecords);
            LOGGER.info("Fetching records successful");
        } catch (NullPointerException e) {
            LOGGER.error("Null pointer exception{}", String.valueOf(e));
        }

        studentTableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Student clikedStudent = row.getItem();
                    try {
                        onClickShowMoreInformation(clikedStudent);
                        LOGGER.info("Row selected");
                    } catch (IOException e) {
                        LOGGER.error("Row selection failure");
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onClickShowMoreInformation(Student selectedStudent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StudentHealthMoreInfoModal.fxml"));
            Parent root = loader.load();

            SHPMoreInfoModalController controller = loader.getController();
            controller.setSelectedStudent(selectedStudent);
            controller.setParentController(this);

            rootStackPane.getChildren().add(root);
            LOGGER.info("Showing more info modal");
        } catch (Exception e) {
            LOGGER.error("Showing more information exception{}", String.valueOf(e));
        }
    }

    private void search(){
        FilteredList<Student> filteredList = new FilteredList<>(students, b -> true);

        searchTextField.textProperty().addListener((observable,oldValue , newValue)->
                        filteredList.setPredicate(student -> {
                            if(newValue.isBlank()){
                                return true;
                            }
                            String searchKeyword = newValue.toLowerCase();

                            if(student.getFirstName().toLowerCase().contains(searchKeyword)){
                                return true;
                            }
                            if(student.getLastName().toLowerCase().contains(searchKeyword)){
                                return true;
                            }
                            return String.valueOf(student.getLrn()).contains(searchKeyword);
                        })
        );
        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTableView.comparatorProperty());
        studentTableView.setItems(sortedList);
        LOGGER.info("Sorted via keyword search");
    }

    private void sortAToZ() {
        studentTableView.getSortOrder().clear();
        lastNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        studentTableView.getSortOrder().add(lastNameColumn);
        studentTableView.sort();
        LOGGER.info("Table sorted to A to Z");
    }

    private void sortZToA() {
        studentTableView.getSortOrder().clear();
        lastNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        studentTableView.getSortOrder().add(lastNameColumn);
        studentTableView.sort();
        LOGGER.info("Table sorted to Z to A");
    }

    private void sortByAge() {
        studentTableView.getSortOrder().clear();
        ageColumn.setSortType(TableColumn.SortType.ASCENDING);
        studentTableView.getSortOrder().add(ageColumn);
        studentTableView.sort();
        LOGGER.info("Table sorted by Age");
    }

    private void sortBySex() {
        FilteredList<Student> filteredList = new FilteredList<>(students, b -> true);

        sexComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredList.setPredicate(student -> student.getGender().equalsIgnoreCase(newValue));
            } else {
                filteredList.setPredicate(b -> true);
            }
        });

        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTableView.comparatorProperty());
        studentTableView.setItems(sortedList);

        LOGGER.info("Table sorted by Sex");
    }

    private void clearFilter() {
        sectionComboBox.getSelectionModel().clearSelection();
        sexComboBox.getSelectionModel().clearSelection();
        searchTextField.clear();
        fetch();
        search();
        LOGGER.info("Sorting cleared");
    }
}
