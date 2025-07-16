package com.rocs.infirmary.application.controller.modal;

import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
import com.rocs.infirmary.application.controller.mainpage.ClinicVisitLogPageController;
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
import java.util.Arrays;
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
    public TextField ageField;
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
        addLrnAutoFillListener();
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
        String lrn = lrnField.getText().trim();
        String temp = bodyTempField.getText().trim();
        String pulse = pulseRateField.getText().trim();
        String resp = respiratoryRateField.getText().trim();
        String bp = bloodPressureField.getText().trim();
        String dispense = invDispensingOutField.getText().trim();

        if (lrn.isEmpty() || !lrn.matches("^\\d+$")) {
            showDialog("Warning", "LRN cannot be empty.");
            return;
        } else if (nameField.getText().isBlank()) {
            showDialog("Warning", "Name cannot be empty.");
            return;
        } else if (gradeSectionField.getText().isBlank()) {
            showDialog("Warning", "Grade & Section cannot be empty.");
            return;
        } else if (temp.isEmpty() || !temp.matches("^\\d+(\\.\\d+)?$")) {
            showDialog("Warning", "Temperature must be a number.");
            return;
        } else if (pulse.isEmpty() || !pulse.matches("^\\d+$")) {
            showDialog("Warning", "Pulse rate must be a number.");
            return;
        } else if (resp.isEmpty() || !resp.matches("^\\d+$")) {
            showDialog("Warning", "Respiratory rate must be numeric.");
            return;
        } else if (bp.isEmpty() || !bp.matches("\\d{2,3}/\\d{2,3}")) {
            showDialog("Warning", "Blood pressure must be like '120/80'.");
            return;
        } else if (symptomsField.getText().isBlank()) {
            showDialog("Warning", "Symptoms cannot be empty.");
            return;
        } else if (treatmentField.getText().isBlank()) {
            showDialog("Warning", "Treatment cannot be empty.");
            return;
        } else if (dispense.isEmpty() || !dispense.matches("^\\d+$")) {
            showDialog("Warning", "Dispensing quantity must be numeric.");
            return;
        } else if (datePickerTextField.getValue() == null) {
            showDialog("Warning", "Visit date must be selected.");
            return;
        }

        addDailyRecord();
    }

    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();
            if (record == null) return;

            Medicine matchedMedicine = SelectedMedicine(medicineNameComboBox.getEditor().getText());
            if (matchedMedicine == null) return;
            if (!updateInventoryDispensed(record, matchedMedicine)) return;
            if (!populateExistingStudentInfo(record)) return;
            record.setMedicineId(matchedMedicine.getMedicineId());

            if (!saveMedicalRecordAndTreatment(record)) return;
            if (clinicVisitLogPageController != null) {
                clinicVisitLogPageController.addStudentMedicalRecord(record);
            }
            showDialog("Notification", "Success, Record Added Successfully.");
            ((Stage) lrnField.getScene().getWindow()).close();
        } catch (Exception e) {
            LOGGER.error("Unhandled exception in addDailyRecord", e);
            showDialog("Error", "Unexpected error. Record not saved.");
        }
    }

    private Medicine SelectedMedicine(String rawInput) {
        if (rawInput == null || rawInput.trim().isEmpty()) {
            showDialog("Warning", "Please enter or select medicine name(s).");
            return null;
        }

        List<String> medicineNames = Arrays.stream(rawInput.split("[,;]+")).map(String::trim).filter(s -> !s.isEmpty()).toList();
        List<Medicine> inventory = inventoryApp.getMedicineInventoryFacade().getAllMedicine();
        for (String name : medicineNames) {
            for (Medicine item : inventory) {
                if (item.getItemName().equalsIgnoreCase(name)) {
                    return item;
                }
            }
        }
        showDialog("Warning", "No matching medicine found in inventory.");
        return null;
    }

    private boolean updateInventoryDispensed(Student record, Medicine medicine) {
        int dispensingQty = record.getDispensingOut();
        int updatedQty = medicine.getQuantity() - dispensingQty;

        if (updatedQty < 0) {
            showDialog("Stock Warning", "Not enough stock to dispense.");
            return false;
        }
        try {
            boolean success = inventoryApp.getMedicineInventoryFacade().updateMedicineInventory(medicine.getInventoryId(), medicine.getMedicineId(), updatedQty, null, null);
            if (!success) {
                showDialog("Warning", "Failed to deduct medicine quantity.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Inventory deduction error", e);
            showDialog("Error", "Inventory update failed. Record not saved.");
            return false;
        }
        return true;
    }

    private boolean populateExistingStudentInfo(Student record) {
        Student existing = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade()
                .getMedicalInformationByLRN(record.getLrn());

        if (existing == null) {
            showDialog("Warning", "Student with this LRN was not found. Please register the student first.");
            return false;
        }

        record.setStudentId(existing.getStudentId());
        return true;
    }

    private boolean saveMedicalRecordAndTreatment(Student record) {
        try {
            Long medRecordId = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addStudentMedicalRecord(record);

            if (medRecordId == null) {
                showDialog("Warning", "Failed to save medical record.");
                return false;
            }
            record.setMedicalRecordId(medRecordId);
            boolean administered = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addMedicineAdministered(record);
            if (!administered) {
                showDialog("Warning", "Failed to save medicine administration record.");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error saving medical record or administering medicine", e);
            showDialog("Error", "Failed to save treatment record.");
            return false;
        }
        return true;
    }

    private void addLrnAutoFillListener() {
        lrnField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) return;
            try {
                long lrn = Long.parseLong(newValue.trim());
                Student existing = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(lrn);
                if (existing != null) {
                    List<String> nameParts = Arrays.asList(existing.getFirstName(), existing.getMiddleName(), existing.getLastName());
                    StringBuilder nameBuilder = new StringBuilder();
                    for (String parts : nameParts) {
                        if (parts != null && !parts.isBlank()) {
                            if (nameBuilder.length() > 0) nameBuilder.append(" ");
                            nameBuilder.append(parts.trim());
                        }
                    }
                    nameField.setText(nameBuilder.toString());
                    gradeSectionField.setText(
                            (existing.getGradeLevel() != null ? existing.getGradeLevel().trim() : "") + " - " +
                                    (existing.getSection() != null ? existing.getSection().trim() : "")
                    );
                    ageField.setText(String.valueOf(existing.getAge()));
                }
            } catch (NumberFormatException e) {
                showDialog("Warning", "Invalid LRN format. Please enter only numeric values.");
                LOGGER.warn("LRN input is not a valid number: {}", newValue, e);
            }
        });
    }

    private Student createStudentMedicalRecordFromForm() {
        Student student = new Student();

        long lrn = Long.parseLong(lrnField.getText().trim());
        double temperature = Double.parseDouble(bodyTempField.getText().trim());
        int pulse = Integer.parseInt(pulseRateField.getText().trim());
        int respiration = Integer.parseInt(respiratoryRateField.getText().trim());
        int dispensingOut = Integer.parseInt(invDispensingOutField.getText().trim());

        student.setLrn(lrn);
        String[] nameParts = nameField.getText().trim().split("\\s+");
        student.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        student.setMiddleName(nameParts.length == 3 ? nameParts[1] : "");
        student.setLastName(nameParts.length == 3 ? nameParts[2] : (nameParts.length == 2 ? nameParts[1] : ""));
        String[] parts = gradeSectionField.getText().split(" - ");
        student.setGradeLevel(parts.length > 0 ? parts[0].trim() : "");
        student.setSection(parts.length > 1 ? parts[1].trim() : "");
        student.setTemperatureReadings(String.valueOf(temperature));
        student.setPulseRate(pulse);
        student.setRespiratoryRate(respiration);
        student.setBloodPressure(bloodPressureField.getText().trim());
        student.setDispensingOut(dispensingOut);
        student.setSymptoms(symptomsField.getText().trim());
        student.setTreatment(treatmentField.getText().trim());

        Student selectedNurse = nurseInChargeComboBox.getSelectionModel().getSelectedItem();
        if (selectedNurse != null) {
            student.setNurseInChargeId(selectedNurse.getStudentId());
            student.setNurseInCharge(selectedNurse.getFirstName() + " " + selectedNurse.getLastName());
        }

        student.setMedicineName(medicineNameComboBox.getEditor().getText().trim());

        LocalDate selectedDate = datePickerTextField.getValue();
        student.setVisitDate(Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return student;
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

}