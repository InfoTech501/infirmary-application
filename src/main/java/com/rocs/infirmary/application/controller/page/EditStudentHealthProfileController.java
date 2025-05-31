package com.rocs.infirmary.application.controller.page;

import com.rocs.infirmary.application.StudentHealthProfileInfoApplication;
import com.rocs.infirmary.application.app.facade.student.profile.StudentHealthProfileFacade;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for the "Edit Student Health Profile" page.
 */
public class EditStudentHealthProfileController {

    @FXML
    private Label LabelStudent;
    @FXML
    private Label LabelLRN;
    @FXML
    private Label LabelFirstname;
    @FXML
    private Label LabelLastname;
    @FXML
    private Label LabelSex;
    @FXML
    private Label LabelAge;
    @FXML
    private Label LabelGradeLevel;
    @FXML
    private Label LabelSection;
    @FXML
    private Label LabelContactNumber;
    @FXML
    private Label LabelHomeAddress;
    @FXML
    private Label LabelEmailAddress;

    @FXML
    private Label LabelStudentHealthInfo;
    @FXML
    private TextField TextfieldHealthProblem;

    @FXML
    private Button ButtonAddField;
    @FXML
    private Button ButtonConfirm;
    @FXML
    private Button ButtonCancel;

    private Student student;


    private final StudentHealthProfileInfoApplication application = new StudentHealthProfileInfoApplication();
    private String studentHealthProblem;

    @FXML
    private void initialize() {
        ButtonAddField.setOnAction(e -> handleAddField());
        ButtonConfirm.setOnAction(e -> handleConfirm());
        ButtonCancel.setOnAction(e -> handleCancel());
    }

    public void setStudent(Student student) {
        this.student = student;
        populateStudentData();
    }

    private void populateStudentData() {
        if (student != null) {
            LabelStudent.setText(student.getFirstName() + " " + student.getLastName());
            LabelLRN.setText(String.valueOf(student.getLrn()));
            LabelFirstname.setText(student.getFirstName());
            LabelLastname.setText(student.getLastName());
            LabelSex.setText(student.getGender());
            LabelAge.setText(String.valueOf(student.getAge()));
            LabelGradeLevel.setText(student.getGrade());
            LabelSection.setText(student.getSection());
            LabelContactNumber.setText(String.valueOf(student.getContactNumber()));
            LabelHomeAddress.setText(student.getAddress());
            LabelEmailAddress.setText(student.getEmail());
        }
    }

    private void handleAddField() {
        String healthProblem = TextfieldHealthProblem.getText().trim();
        if (!healthProblem.isEmpty()) {

            student.setSymptoms(healthProblem);
            showAlert("Added", "Health problem \"" + healthProblem + "\" added!");
            TextfieldHealthProblem.clear();
        } else {
            showAlert("Empty Field", "Please enter a health problem to add.");
        }
    }

    private void handleConfirm() {

        StudentHealthProfileFacade facade = application.getStudentHealthProfileFacade();


        List<Student> updatedProfile = facade.getStudentHealthProfileByLRN(student.getLrn());


        System.out.println("Updated profile: " + updatedProfile);

        showAlert("Confirmed", "Student health profile has been updated.");
        closeWindow();
    }

    private void handleCancel() {
        System.out.println("Cancelled editing for: " + student.getFirstName() + " " + student.getLastName());
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) ButtonCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setStudentHealthProblem(String studentHealthProblem) {
        this.studentHealthProblem = studentHealthProblem;
    }

    public String getStudentHealthProblem() {
        return studentHealthProblem;
    }
}

