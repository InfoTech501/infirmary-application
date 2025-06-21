package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.StudentHealthProfileApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentHealthProfileController implements Initializable {

    // table
    @FXML
    public TableView<Student> StudentTable;
    @FXML
    public TableColumn<Student, Boolean> SelectColumn;
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
    public ComboBox<String> SectionComboBox;
    @FXML
    public ComboBox<String> SexComboBox;
    @FXML
    public Button AgeFilterBtn, AToZFilterBtn, ZToAFilterBtn, ClearFilterBtn;

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

        SelectColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        SelectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(SelectColumn));
        SelectColumn.setEditable(true);
        SelectColumn.setStyle("-fx-alignment: CENTER;");

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
        for (Student student : studentList) {
            if (student.isSelectedProperty() == null) {
                student.setIsSelected(false);
            }
        }
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(studentList);
        StudentTable.setItems(studentObservableList);
    }
}
