package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ViewStudentHealthProfileControllerTest {

    private TableView<Student> studentTableView;
    private TextField searchTextField;
    private ComboBox<String> sectionComboBox;
    private ComboBox<String> sexComboBox;
    private Button clearFilterBtn;


    private static final int MOCK_STUDENT_COUNT = 2;

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/StudentHealthProfile.fxml"));
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();

        stage.toFront();
    }

    @BeforeEach
    void setup(FxRobot robot) throws TimeoutException {
        studentTableView = robot.lookup("#studentTableView").queryAs(TableView.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);
        sectionComboBox = robot.lookup("#sectionComboBox").queryAs(ComboBox.class);
        sexComboBox = robot.lookup("#sexComboBox").queryAs(ComboBox.class);
        clearFilterBtn = robot.lookup("#clearFilterBtn").queryAs(Button.class);

        assertNotNull(studentTableView, "studentTableView should not be null");
        assertNotNull(searchTextField, "searchTextField should not be null");
        assertNotNull(sectionComboBox, "sectionComboBox should not be null");
        assertNotNull(sexComboBox, "sexComboBox should not be null");
        assertNotNull(clearFilterBtn, "clearFilterBtn should not be null");


        robot.interact(() -> {
            searchTextField.clear();
            sectionComboBox.getSelectionModel().selectFirst();
            sexComboBox.getSelectionModel().selectFirst();
            studentTableView.getItems().clear();
        });
    }

    @Disabled
    @Test
    void testProfilesDisplayed(FxRobot robot) {

        loadMockStudents(robot);

        robot.interact(() -> {
            assertEquals(MOCK_STUDENT_COUNT, studentTableView.getItems().size(), "Should display all mock students after loading.");
            assertFalse(studentTableView.getItems().isEmpty(), "Student TableView should not be empty.");
            assertFalse(studentTableView.getColumns().isEmpty(), "Student TableView should have columns.");
        });
    }

    private void loadMockStudents(FxRobot robot) {
        ObservableList<Student> mockStudents = FXCollections.observableArrayList();


        robot.interact(() -> {

            Student s1 = new Student();
            s1.setLrn("2024001");
            s1.setFirstName("Juan");
            s1.setLastName("Dela Cruz");
            s1.setGradeLevel("10");
            s1.setSection("A");
            s1.setGender("Male");
            s1.setAge(15);


            Student s2 = new Student();
            s2.setLrn("2024002");
            s2.setFirstName("Maria");
            s2.setLastName("Santos");
            s2.setGradeLevel("9");
            s2.setSection("B");
            s2.setGender("Female");
            s2.setAge(14);

            mockStudents.addAll(s1, s2);


            studentTableView.setItems(mockStudents);
        });
    }

    @Disabled
    @Test
    void testSearchByName(FxRobot robot) {
        loadMockStudents(robot); // Load data first!

        final String searchName = "Juan";
        robot.clickOn(searchTextField);
        robot.write(searchName);

        robot.sleep(500);

        assertEquals(searchName, searchTextField.getText(), "Search field should contain the written name.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the name search.");
            assertEquals("Juan", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Juan.");
        });
    }

    @Disabled
    @Test
    void testSearchByLRN(FxRobot robot) {
        loadMockStudents(robot);

        final String searchLRN = "2024002";
        robot.clickOn(searchTextField);
        robot.write(searchLRN);

        robot.sleep(500); // Give time for the filter listener to fire

        assertEquals(searchLRN, searchTextField.getText(), "Search field should contain the written LRN.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the LRN search.");
            assertEquals("Maria", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Maria.");
        });
    }

    @Disabled
    @Test
    void testFilterBySection(FxRobot robot) {
        loadMockStudents(robot);

        robot.interact(() -> {
            sectionComboBox.setItems(FXCollections.observableArrayList("All Sections", "A", "B"));
            sectionComboBox.getSelectionModel().select("A");
        });

        final String selectedSection = "A";
        robot.sleep(500);


        assertEquals(selectedSection, sectionComboBox.getValue(), "Section ComboBox should be set to 'A'.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the section filter.");
            assertEquals("Juan", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Juan (Section A).");
        });
    }

    @Disabled
    @Test
    void testFilterByGender(FxRobot robot) {
        loadMockStudents(robot);


        robot.interact(() -> {
            sexComboBox.setItems(FXCollections.observableArrayList("All Genders", "Male", "Female"));
            sexComboBox.getSelectionModel().select("Female");
        });

        final String selectedGender = "Female";

        robot.sleep(500);


        assertEquals(selectedGender, sexComboBox.getValue(), "Gender ComboBox should be set to 'Female'.");
        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the gender filter.");
            assertEquals("Maria", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Maria (Female).");
        });
    }

    @Disabled
    @Test
    void testClearFilter(FxRobot robot) {
        loadMockStudents(robot);

        robot.interact(() -> {
            searchTextField.setText("Juan");
            sectionComboBox.setItems(FXCollections.observableArrayList("All Sections", "A", "B"));
            sectionComboBox.getSelectionModel().select("A");
            sexComboBox.setItems(FXCollections.observableArrayList("All Genders", "Male", "Female"));
            sexComboBox.getSelectionModel().select("Male");
        });


        robot.sleep(500);


        robot.clickOn(clearFilterBtn);


        robot.sleep(500);


        assertEquals("", searchTextField.getText(), "Search field should be cleared.");
        assertEquals("All Sections", sectionComboBox.getValue(), "Section filter should be reset to 'All Sections'.");
        assertEquals("All Genders", sexComboBox.getValue(), "Gender filter should be reset to 'All Genders'.");
        
        robot.interact(() -> {
            assertEquals(MOCK_STUDENT_COUNT, studentTableView.getItems().size(), "All students should be displayed after clearing the filter.");
        });
    }
}