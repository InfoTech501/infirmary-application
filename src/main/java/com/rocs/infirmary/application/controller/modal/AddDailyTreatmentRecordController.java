package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.controller.dashboard.ClinicVisitLogPageController;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * {@code AddDailyTreatmentRecordController} is used to handle event processes of adding new daily treatment record of a student
 * this implements Initializable interface
 **/
public class AddDailyTreatmentRecordController implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddDailyTreatmentRecordController.class);
    @FXML
    private TextField lrnField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField gradeSectionField;
    @FXML
    private TextField bodyTempField;
    @FXML
    private TextField pulseRateField;
    @FXML
    private TextField respiratoryRateField;
    @FXML
    private TextField bloodPressureField;
    @FXML
    private TextField symptomsField;
    @FXML
    private ComboBox<Student> nurseInChargeComboBox;
    @FXML
    private TextField treatmentField;
    @FXML
    private ComboBox<Medicine> medicineNameComboBox;
    @FXML
    private TextField invDispensingOutField;
    @FXML
    private DatePicker datePickerTextField;

    private ObservableList<Student> studentList;
    private final MedicalRecordInfoMgtApplication medicalRecordInfoMgtApplication = new MedicalRecordInfoMgtApplication();
    private final InventoryManagementApplication inventoryApp = new InventoryManagementApplication();
    private ClinicVisitLogPageController clinicVisitLogPageController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lrnField.requestFocus();
        List<Medicine> available = inventoryApp.getMedicineInventoryFacade().getAllMedicine();
        medicineNameComboBox.setItems(FXCollections.observableArrayList(available));
        List<Student> nurses = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().getAllNurseAccounts();
        nurseInChargeComboBox.setItems(FXCollections.observableArrayList(nurses));

    }
    /**
     * Sets the controller responsible for updating the clinic visit log view.
     * Used to allow real-time updates to the record list after a new entry is added.
     *
     * @param controller the ClinicVisitLogPageController to link with this form
     */
    public void setClinicVisitLogPageController(ClinicVisitLogPageController controller) {
        this.clinicVisitLogPageController = controller;
    }

    @FXML
    private void handleConfirmButton(ActionEvent actionEvent) {
        addDailyRecord();
    }

    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();

            Medicine selectedMedicine = medicineNameComboBox.getSelectionModel().getSelectedItem();
            if (selectedMedicine != null) {
                record.setMedicineId(selectedMedicine.getMedicineId());
            } else {
                showWarning("Please select a medicine from the dropdown before saving.");
                return;
            }

            Student existing = medicalRecordInfoMgtApplication
                    .getStudentMedicalRecordFacade()
                    .getMedicalInformationByLRN(record.getLrn());

            if (existing != null) {
                record.setStudentId(existing.getStudentId());
            } else {
                showWarning("Student with this LRN was not found. Please register the student first.");
                return;
            }

            medicalRecordInfoMgtApplication
                    .getStudentMedicalRecordFacade()
                    .addStudentMedicalRecord(record);

            medicalRecordInfoMgtApplication
                    .getStudentMedicalRecordFacade()
                    .addMedicineAdministered(record);

            if (clinicVisitLogPageController != null) {
                clinicVisitLogPageController.addStudentMedicalRecord(record);
            }

            showWarning("Success, Record Added Successfully.");

            Stage stage = (Stage) lrnField.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            LOGGER.error("Failed to save daily treatment record", e);
            showWarning("Failed to save record.");
        }
    }

    private Student createStudentMedicalRecordFromForm() {
        Student student = new Student();

        student.setLrn(Long.parseLong(lrnField.getText()));

        String[] nameParts = nameField.getText().trim().split("\\s+");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String middleName = nameParts.length == 3 ? nameParts[1] : "";
        String lastName = nameParts.length == 3 ? nameParts[2]
                : (nameParts.length == 2 ? nameParts[1] : "");
        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);

        String[] parts = gradeSectionField.getText().split(" - ");
        String grade = parts.length > 0 ? parts[0].trim() : "";
        String section = parts.length > 1 ? parts[1].trim() : "";
        student.setGradeLevel(grade);
        student.setSection(section);

        student.setTemperatureReadings(String.valueOf(Double.parseDouble(bodyTempField.getText())));
        student.setPulseRate(Integer.parseInt(pulseRateField.getText()));
        student.setRespiratoryRate(Integer.parseInt(respiratoryRateField.getText()));
        student.setBloodPressure(bloodPressureField.getText());
        student.setSymptoms(symptomsField.getText());

        Student selectedNurse = nurseInChargeComboBox != null
                ? nurseInChargeComboBox.getSelectionModel().getSelectedItem()
                : null;

        if (selectedNurse != null) {
            student.setNurseInChargeId(Long.valueOf(selectedNurse.getStudentId()));
            student.setNurseInCharge(selectedNurse.getFirstName() + " " + selectedNurse.getLastName());
        }

        student.setTreatment(treatmentField.getText());

        Medicine selected = medicineNameComboBox != null
                ? medicineNameComboBox.getValue()
                : null;

        if (selected != null) {
            student.setMedicineId(Long.parseLong(String.valueOf(selected.getMedicineId())));
            student.setMedicineName(selected.getItemName());
        }

        student.setDispensingOut(Integer.parseInt(invDispensingOutField.getText()));

        if (datePickerTextField != null && datePickerTextField.getValue() != null) {
            LocalDate selectedDate = datePickerTextField.getValue();
            Date convertedDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            student.setVisitDate(convertedDate);
        }

        return student;
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

}