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
        loader.setControllerFactory(param -> new ManageStudentMedicalRecordsController(null, null));
        BorderPane mainLayout = loader.load();
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
        robot.write("37.5");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Rest and fluids");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals("Flu", updateIllnessTextField.getText());
        assertEquals("37.5", updateTemperatureTextField.getText());
        assertEquals("Rest and fluids", updateTreatmentTextField.getText());
        assertEquals(LocalDate.now(), updateVisitDatePicker.getValue());
    }

    @Test
    void testInvalidTemperatureFormat(FxRobot robot) {
        robot.clickOn(updateIllnessTextField);
        robot.write("Flu");
        robot.clickOn(updateTemperatureTextField);
        robot.write("thirty seven");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Rest");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals("thirty seven", updateTemperatureTextField.getText());
    }

    @Test
    void testTemperatureOutOfRange(FxRobot robot) {
        robot.clickOn(updateIllnessTextField);
        robot.write("Flu");
        robot.clickOn(updateTemperatureTextField);
        robot.write("37.0");
        robot.clickOn(updateTreatmentTextField).write("Rest");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals("37.0", updateTemperatureTextField.getText());
        assertEquals("Flu", updateIllnessTextField.getText());
        assertEquals("Rest", updateTreatmentTextField.getText());
        assertEquals(LocalDate.now(), updateVisitDatePicker.getValue());
    }
    @Test
    void testVisitDateInFuture(FxRobot robot) {
        robot.clickOn(updateIllnessTextField);
        robot.write("Cough");
        robot.clickOn(updateTemperatureTextField);
        robot.write("36.8");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Medicine");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now().plusDays(1)));
        robot.clickOn(confirmChangesBtn);

        assertEquals(LocalDate.now().plusDays(1), updateVisitDatePicker.getValue());
    }

    @Test
    void testFieldsEmpty(FxRobot robot) {
        robot.interact(() -> {
            updateIllnessTextField.clear();
            updateTemperatureTextField.clear();
            updateTreatmentTextField.clear();
            updateVisitDatePicker.setValue(null);
        });

        robot.clickOn(confirmChangesBtn);

        assertTrue(updateIllnessTextField.getText().isEmpty());
        assertTrue(updateTemperatureTextField.getText().isEmpty());
        assertTrue(updateTreatmentTextField.getText().isEmpty());
        assertNull(updateVisitDatePicker.getValue());
    }

    @Test
    void testIllnessTooLong(FxRobot robot) {
        String longIllness = "A".repeat(251);
        robot.clickOn(updateIllnessTextField);
        robot.write(longIllness);
        robot.clickOn(updateTemperatureTextField);
        robot.write("37.0");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Rest");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals(251, updateIllnessTextField.getText().length());
    }

    @Test
    void testTreatmentTooLong(FxRobot robot) {
        String longTreatment = "B".repeat(501);
        robot.clickOn(updateIllnessTextField);
        robot.write("Flu");
        robot.clickOn(updateTemperatureTextField);
        robot.write("37.0");
        robot.clickOn(updateTreatmentTextField);
        robot.write(longTreatment);
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals(501, updateTreatmentTextField.getText().length());
    }

    @Test
    void testDeleteMedicalRecordButton(FxRobot robot) {
        robot.clickOn(deleteMedicalRecordBtn);
        assertTrue(deleteMedicalRecordBtn.isVisible());
        assertFalse(deleteMedicalRecordBtn.isDisable());
    }
}
