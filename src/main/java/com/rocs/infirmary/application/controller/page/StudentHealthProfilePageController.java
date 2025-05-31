package com.rocs.infirmary.application.controller.page;

import com.rocs.infirmary.application.StudentHealthProfileInfoApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.app.facade.student.record.StudentMedicalRecordFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentHealthProfilePageController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> SectionComboBox;

    @FXML
    private ComboBox<String> SexComboBox;

    @FXML
    private Button AgeFilterBtn;

    @FXML
    private Button Filter_Button_A;

    @FXML
    private Button Filter_Button_Z;

    @FXML
    private Button ClearFilterButton;

    @FXML
    private Button ShowInformationButton;

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
    private StudentMedicalRecordFacade medicalRecordFacade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentHealthProfileInfoApplication app = new StudentHealthProfileInfoApplication();
        studentHealthProfileFacade = app.getStudentHealthProfileFacade();

        initializeTableColumns();
        loadStudentProfiles();

        SectionComboBox.setItems(FXCollections.observableArrayList("All Sections", "A", "B", "C"));
        SexComboBox.setItems(FXCollections.observableArrayList("All", "Male", "Female"));

        Filter_Button_A.setOnAction(e -> sortProfilesAZ());
        Filter_Button_Z.setOnAction(e -> sortProfilesZA());
        AgeFilterBtn.setOnAction(e -> filterByAge());
        ClearFilterButton.setOnAction(e -> loadStudentProfiles());
        ShowInformationButton.setOnAction(e -> handleShowMoreInformation());

        searchField.setOnAction(e -> handleSearch());
    }

    private void initializeTableColumns() {
        lrnColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLrn())));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradeSectionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGrade() + " - " + cellData.getValue().getSection()));
        adviserColumn.setCellValueFactory(new PropertyValueFactory<>("adviser"));
    }

    private void loadStudentProfiles() {
        List<Student> students = studentHealthProfileFacade.getAllStudentHealthProfile();
        studentObservableList = FXCollections.observableArrayList(students);
        tableView.setItems(studentObservableList);
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            loadStudentProfiles();
            return;
        }

        try {
            Long lrn = Long.parseLong(searchQuery);
            List<Student> result = studentHealthProfileFacade.getStudentHealthProfileByLRN(lrn);
            studentObservableList.setAll(result);
        } catch (NumberFormatException e) {
            showAlert("Invalid LRN", "Please enter a numeric LRN.");
        }
    }

    private void sortProfilesAZ() {
        FXCollections.sort(studentObservableList,
                (s1, s2) -> s1.getFirstName().compareToIgnoreCase(s2.getFirstName()));
    }

    private void sortProfilesZA() {
        FXCollections.sort(studentObservableList,
                (s1, s2) -> s2.getFirstName().compareToIgnoreCase(s1.getFirstName()));
    }

    private void filterByAge() {
        showAlert("Filter by Age", "Filtering by age is not implemented yet.");
    }

    @FXML
    private void handleShowMoreInformation() {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MoreInformationStudentProfile.fxml"));
                Parent root = loader.load();

                MoreInformationStudentProfileController controller = loader.getController();
                controller.setMedicalRecordFacade(this.medicalRecordFacade);
                controller.loadStudentProfileData(selectedStudent.getLrn());

                Stage stage = new Stage();
                stage.setTitle("More Information - Student Profile");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Unable to load the student health profile page.");
            }
        } else {
            showAlert("No Student Selected", "Please select a student to view more information.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
