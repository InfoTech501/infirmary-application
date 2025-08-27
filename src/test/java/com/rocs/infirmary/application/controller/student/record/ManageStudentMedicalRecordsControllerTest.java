package com.rocs.infirmary.application.controller.student.record;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ManageStudentMedicalRecordsControllerTest {

    private TextField updateIllnessTextField;
    private TextField updateTemperatureTextField;
    private TextField updateTreatmentTextField;
    private DatePicker updateVisitDatePicker;
    private Button confirmChangesBtn;
    private Button deleteMedicalRecordBtn;


    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ManageStudentMedicalRecords.fxml"));
        BorderPane mainLayout;
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup(FxRobot robot) {
        updateIllnessTextField = robot.lookup("#updateIllnessTextField").queryAs(TextField.class);
        updateTemperatureTextField = robot.lookup("#updateTemperatureTextField").queryAs(TextField.class);
        updateTreatmentTextField = robot.lookup("#updateTreatmentTextField").queryAs(TextField.class);
        updateVisitDatePicker = robot.lookup("#updateVisitDatePicker").queryAs(DatePicker.class);
        confirmChangesBtn = robot.lookup("#confirmChangesBtn").queryAs(Button.class);
        deleteMedicalRecordBtn = robot.lookup("#deleteMedicalRecordBtn").queryAs(Button.class);

        assertNotNull(updateIllnessTextField);
        assertNotNull(updateTemperatureTextField);
        assertNotNull(updateTreatmentTextField);
        assertNotNull(updateVisitDatePicker);
        assertNotNull(confirmChangesBtn);
        assertNotNull(deleteMedicalRecordBtn);

    }

    @Test
    void testValidInputs(FxRobot robot) {
        robot.clickOn(updateIllnessTextField);
        robot.write("Flu");
        robot.clickOn(updateTemperatureTextField);
        robot.write("37");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Rest");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));

        assertEquals("Flu", updateIllnessTextField.getText());
        assertEquals("37", updateTemperatureTextField.getText());
        assertEquals("Rest", updateTreatmentTextField.getText());
        assertEquals(LocalDate.now(), updateVisitDatePicker.getValue());
    }

    @Test
    void testInvalidTemperatureFormat(FxRobot robot) {
        robot.clickOn(updateTemperatureTextField).write("one");
        assertEquals("one", updateTemperatureTextField.getText(), "Temperature must be a valid number");
    }

    @Test
    void testAllFieldsEmpty(FxRobot robot) {
        robot.clickOn(confirmChangesBtn);
        assertTrue(updateIllnessTextField.getText().isEmpty());
        assertTrue(updateTemperatureTextField.getText().isEmpty());
        assertTrue(updateTreatmentTextField.getText().isEmpty());
        assertNull(updateVisitDatePicker.getValue());
    }
}
