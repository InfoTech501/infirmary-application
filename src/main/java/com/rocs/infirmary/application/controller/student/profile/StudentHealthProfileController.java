package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.StudentHealthProfileApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
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

    private StudentHealthProfileFacade studentHealthProfileFacade;
    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();
    private final List<Student> students = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTableList();
        fetch();
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

    private void fetch() {
        List<Student> studentList = studentHealthProfileApplication.getStudentHealthProfileFacade().getAllStudentHealthProfile();

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

        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(studentList);
        StudentTable.setItems(studentObservableList);
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

        rootStackPane.getChildren().add(root);
    }
}
