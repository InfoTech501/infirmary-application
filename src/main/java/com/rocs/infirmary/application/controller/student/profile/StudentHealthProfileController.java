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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentHealthProfileController implements Initializable {

    // table
    @FXML
    public TableView<Student> StudentTable;
    @FXML
    public TableColumn<Student, String> LRNColumn;
    @FXML
    public TableColumn<Student, String> FirstNameColumn;
    @FXML
    public TableColumn<Student, String> LastNameColumn;
    @FXML
    public TableColumn<Student, String> GradeColumn;
    @FXML
    public TableColumn<Student, String> SectionColumn;
    @FXML
    public TableColumn<Student, String> GenderColumn;
    @FXML
    public TableColumn<Student, String> AgeColumn;
    @FXML
    public TableColumn<Student, String> AdviserColumn;

    // search
    @FXML
    public TextField SearchTextField;

    // control buttons
    @FXML
    public ComboBox<String> SectionComboBox, SexComboBox;
    @FXML
    public Button AgeFilterBtn, AToZFilterBtn, ZToAFilterBtn, ClearFilterBtn;
    @FXML
    public StackPane rootStackPane;
    @FXML
    public BorderPane mainBorderPane;

    private ObservableList<Student> students;
    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        students = FXCollections.observableArrayList();

        AToZFilterBtn.setOnAction(event -> sortAToZ());
        ZToAFilterBtn.setOnAction(event -> sortZToA());
        AgeFilterBtn.setOnAction(event -> sortByAge());
        ClearFilterBtn.setOnAction(event -> clearFilter());

        populateTableList();
        fetch();
        search();
        sortBySex();
    }

    public void populateTableList() {
        StudentTable.setEditable(true);

        LRNColumn.setCellValueFactory(new PropertyValueFactory<>("lrn"));
        FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        GradeColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        SectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        GenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        AgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        AdviserColumn.setCellValueFactory(new PropertyValueFactory<>("studentAdviser"));

        ObservableList<String> sectionNames = FXCollections.observableArrayList("Sections");
        SectionComboBox.setItems(sectionNames);

        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
        SexComboBox.setItems(genders);
    }

    public void fetch() {
        List<Student> studentList = studentHealthProfileApplication.getStudentHealthProfileFacade().getAllStudentHealthProfile();
        List<Student> activeRecords = studentList.stream().filter(student -> student.getMedicalRecordStatus() == 1).toList();

        StudentTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Student clikedStudent = row.getItem();
                    try {
                        onClickShowMoreInformation(clikedStudent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });

        students.setAll(activeRecords);
    }

    private void onClickShowMoreInformation(Student selectedStudent) throws IOException {
        if (selectedStudent == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a student first.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StudentHealthMoreInfoModal.fxml"));
        Parent root = loader.load();

        SHPMoreInfoModalController controller = loader.getController();
        controller.setSelectedStudent(selectedStudent);
        controller.setParentController(this);

        rootStackPane.getChildren().add(root);
    }

    private void search(){
        FilteredList<Student> filteredList = new FilteredList<>(students, b -> true);

        SearchTextField.textProperty().addListener((observable,oldValue , newValue)->
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
        sortedList.comparatorProperty().bind(StudentTable.comparatorProperty());
        StudentTable.setItems(sortedList);
    }

    private void sortAToZ() {
        StudentTable.getSortOrder().clear();
        LastNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        StudentTable.getSortOrder().add(LastNameColumn);
        StudentTable.sort();
    }

    private void sortZToA() {
        StudentTable.getSortOrder().clear();
        LastNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        StudentTable.getSortOrder().add(LastNameColumn);
        StudentTable.sort();
    }

    private void sortByAge() {
        StudentTable.getSortOrder().clear();
        AgeColumn.setSortType(TableColumn.SortType.ASCENDING);
        StudentTable.getSortOrder().add(AgeColumn);
        StudentTable.sort();
    }

    private void sortBySex() {
        FilteredList<Student> filteredList = new FilteredList<>(students, b -> true);

        SexComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredList.setPredicate(student -> student.getGender().equalsIgnoreCase(newValue));
            } else {
                filteredList.setPredicate(b -> true);
            }
        });

        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(StudentTable.comparatorProperty());
        StudentTable.setItems(sortedList);
    }

    private void clearFilter() {
        SectionComboBox.getSelectionModel().clearSelection();
        SexComboBox.getSelectionModel().clearSelection();
        SearchTextField.clear();
        fetch();
        search();
    }
}
