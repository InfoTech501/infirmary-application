package com.rocs.infirmary.application.controller.page;

import com.rocs.infirmary.application.StudentHealthProfileInfoApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentHealthProfilePageController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, String> lrnColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> gradeSectionColumn;

    @FXML
    private TableColumn<Student, String> adviserColumn;

    private StudentHealthProfileFacade studentHealthProfileFacade;
    private ObservableList<Student> studentObservableList;
    private Object LRN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the facade via module
        StudentHealthProfileInfoApplication app = new StudentHealthProfileInfoApplication();
        studentHealthProfileFacade = app.getStudentHealthProfileFacade();

        // Initialize table
        initializeTableColumns();
        loadStudentProfiles();

        // Initialize filter combo box
        filterComboBox.setItems(FXCollections.observableArrayList(
                "Section", "Sex", "Age", "A-Z", "Z-A"
        ));
    }

    private void initializeTableColumns() {
        lrnColumn.setCellValueFactory(cellData -> new SimpleStringProperty());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradeSectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrade() + " - " + cellData.getValue().getSection()));
        adviserColumn.setCellValueFactory(new PropertyValueFactory<>("adviser"));
    }

    private void loadStudentProfiles() {
        List<Student> studentList = studentHealthProfileFacade.getAllStudentHealthProfile();
        studentObservableList = FXCollections.observableArrayList(studentList);
        tableView.setItems(studentObservableList);
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        String filter = filterComboBox.getValue();

        List<Student> filteredStudents = studentHealthProfileFacade.getStudentHealthProfileByLRN(Long LRN);
        studentObservableList.setAll(filteredStudents);
    }

//    @FXML
//    private void handleViewDetails() {
//        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
//        if (selectedStudent != null) {
//            studentHealthProfileFacade.showStudentDetailsModal(selectedStudent);
//        } else {
//            showAlert("No Student Selected", "Please select a student to view details.");
//        }
//    }
//
//    @FXML
//    private void handleEditProfile() {
//        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
//        if (selectedStudent != null) {
//            studentHealthProfileFacade.showEditStudentProfileModal(selectedStudent);
//            // After editing, refresh the table
//            loadStudentProfiles();
//        } else {
//            showAlert("No Student Selected", "Please select a student to edit.");
//        }
//    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
