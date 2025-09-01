package com.rocs.infirmary.application.controller.student.record;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileController;
import com.rocs.infirmary.application.controller.student.profile.StudentHealthProfileModalController;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ManageStudentMedicalRecordsControllerTest {

    @Mock
    private StudentHealthProfileController mockParentController;

    @Mock
    private StudentHealthProfileModalController mockModalController;
    private ManageStudentMedicalRecordsController controller;
    private TextField updateIllnessTextField;
    private TextField updateTemperatureTextField;
    private TextField updateTreatmentTextField;
    private DatePicker updateVisitDatePicker;
    private Button confirmChangesBtn;
    private Button deleteMedicalRecordBtn;
    private Label illnessLabel;
    private Label temperatureLabel;
    private Label bloodPressureLabel;
    private Label pulseRateLabel;
    private Label respiratoryRate;
    private Label treatmentLabel;

    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ManageStudentMedicalRecords.fxml"));
        loader.setControllerFactory(param -> {
            controller = new ManageStudentMedicalRecordsController(mockParentController, mockModalController);
            return controller;
        });

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

        illnessLabel = robot.lookup("#illnessLabel").queryAs(Label.class);
        temperatureLabel = robot.lookup("#temperatureLabel").queryAs(Label.class);
        bloodPressureLabel = robot.lookup("#bloodPressureLabel").queryAs(Label.class);
        pulseRateLabel = robot.lookup("#pulseRateLabel").queryAs(Label.class);
        respiratoryRate = robot.lookup("#respiratoryRate").queryAs(Label.class);
        treatmentLabel = robot.lookup("#treatmentLabel").queryAs(Label.class);

        assertNotNull(updateIllnessTextField);
        assertNotNull(updateTemperatureTextField);
        assertNotNull(updateTreatmentTextField);
        assertNotNull(updateVisitDatePicker);
        assertNotNull(confirmChangesBtn);
        assertNotNull(deleteMedicalRecordBtn);

        robot.interact(() -> setupTestData());
    }

    private void setupTestData() {
        Student mockStudent = mock(Student.class);
        when(mockStudent.getLrn()).thenReturn("12345678910");
        when(mockStudent.getFirstName()).thenReturn("John");

        MedicalRecord mockRecord = mock(MedicalRecord.class);
        when(mockRecord.getMedicalRecordId()).thenReturn(1L);
        when(mockRecord.getSymptoms()).thenReturn("Fever");
        when(mockRecord.getTemperatureReadings()).thenReturn("37.5");
        when(mockRecord.getTreatment()).thenReturn("Rest");
        when(mockRecord.getBloodPressure()).thenReturn("120/80");
        when(mockRecord.getPulseRate()).thenReturn(Integer.valueOf("72"));
        when(mockRecord.getRespiratoryRate()).thenReturn(Integer.valueOf("16"));

        controller.setSelectedStudentRecord(mockStudent, mockRecord);
    }

    @Disabled
    @Test
    void checkLabels(FxRobot robot) {
        assertEquals("Fever", illnessLabel.getText());
        assertEquals("37.5", temperatureLabel.getText());
        assertEquals("Rest", treatmentLabel.getText());
        assertEquals("120/80", bloodPressureLabel.getText());
        assertEquals("72", pulseRateLabel.getText());
        assertEquals("25", respiratoryRate.getText());
    }

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
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

    @Disabled
    @Test
    void testIllnessTooLong(FxRobot robot) {
        String invalidIllnessInput = "A".repeat(251);
        robot.clickOn(updateIllnessTextField);
        robot.write(invalidIllnessInput);
        robot.clickOn(updateTemperatureTextField);
        robot.write("37.0");
        robot.clickOn(updateTreatmentTextField);
        robot.write("Rest");
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals(251, updateIllnessTextField.getText().length());
    }

    @Disabled
    @Test
    void testTreatmentTooLong(FxRobot robot) {
        String InvalidTreatmentInput = "B".repeat(501);
        robot.clickOn(updateIllnessTextField);
        robot.write("Flu");
        robot.clickOn(updateTemperatureTextField);
        robot.write("37.0");
        robot.clickOn(updateTreatmentTextField);
        robot.write(InvalidTreatmentInput);
        robot.interact(() -> updateVisitDatePicker.setValue(LocalDate.now()));
        robot.clickOn(confirmChangesBtn);

        assertEquals(501, updateTreatmentTextField.getText().length());
    }

    @Disabled
    @Test
    void testDeleteMedicalRecordButton(FxRobot robot) {
        robot.clickOn(deleteMedicalRecordBtn);
        assertTrue(deleteMedicalRecordBtn.isVisible());
        assertFalse(deleteMedicalRecordBtn.isDisable());
    }
}