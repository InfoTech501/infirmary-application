package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ViewStudentHealthProfileControllerTest {

    private TableView<Student> studentTableView;
    private TextField searchTextField;
    private ComboBox<String> sectionComboBox;
    private ComboBox<String> sexComboBox;
    private Button clearFilterBtn;

    @BeforeEach
    void setup(FxRobot robot) {
        studentTableView = robot.lookup("#studentTableView").queryAs(TableView.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);
        sectionComboBox = robot.lookup("#sectionComboBox").queryAs(ComboBox.class);
        sexComboBox = robot.lookup("#sexComboBox").queryAs(ComboBox.class);
        clearFilterBtn = robot.lookup("#clearFilterBtn").queryAs(Button.class);


        assertNotNull(studentTableView);
        assertNotNull(searchTextField);
        assertNotNull(sectionComboBox);
        assertNotNull(sexComboBox);
        assertNotNull(clearFilterBtn);
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/StudentHealthProfile.fxml"));
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    @Disabled
    @Test
    void testProfilesDisplayed(FxRobot robot) {

        assertNotNull(studentTableView, "Student table should be initialized");
        assertNotNull(studentTableView.getItems(), "Table items should not be null");
        assertFalse(studentTableView.getItems().isEmpty(), "Student table should contain a list of students");
        assertFalse(studentTableView.getColumns().isEmpty(), "Student table should have defined columns");
    }

    @Disabled
    @Test
    void testSearchByName(FxRobot robot) {
        robot.clickOn(searchTextField);
        robot.write("John");

        assertTrue(searchTextField.getText().contains("John"));
    }

    @Disabled
    @Test
    void testSearchByLRN(FxRobot robot) {
        robot.clickOn(searchTextField);
        robot.write("1234567890");

        assertTrue(searchTextField.getText().contains("1234567890"));
    }

    @Disabled
    @Test
    void testFilterBySection(FxRobot robot) {
        robot.interact(() -> sectionComboBox.getSelectionModel().select("A"));

        assertEquals("A", sectionComboBox.getValue());
    }

    @Disabled
    @Test
    void testFilterByGender(FxRobot robot) {
        robot.interact(() -> sexComboBox.getSelectionModel().select("Female"));

        assertEquals("Female", sexComboBox.getValue());
    }

    @Disabled
    @Test
    void testClearFilter(FxRobot robot) {
        robot.interact(() -> {
            searchTextField.setText("Jane");
            sectionComboBox.getSelectionModel().select("A");
            sexComboBox.getSelectionModel().select("Male");
        });

        robot.clickOn(clearFilterBtn);

        assertEquals("", searchTextField.getText());
    }
}
