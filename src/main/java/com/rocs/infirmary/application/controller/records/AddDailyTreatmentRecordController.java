package com.rocs.infirmary.application.controller.records;

import static com.rocs.infirmary.application.controller.helper.ControllerHelper.showDialog;

import com.rocs.infirmary.application.data.model.person.employee.Employee;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.module.inventory.management.application.InventoryManagementApplication;
import com.rocs.infirmary.application.module.medical.record.management.application.MedicalRecordInfoMgtApplication;
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
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private TextField ageField;
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
    private ComboBox<Employee> nurseInChargeComboBox;
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
        setupMedicineNameComboBoxDisplay();
        List<Medicine> available = inventoryApp.getMedicineInventoryFacade().getAllMedicine();
        medicineNameComboBox.setItems(FXCollections.observableArrayList(available));
        List<Employee> nurses = medicalRecordInfoMgtApplication.getEmployeeInformationFacade().getAllNurseAccounts();
        nurseInChargeComboBox.setItems(FXCollections.observableArrayList(nurses));

    }

    private void addLrnAutoFillListener() {
        lrnField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank() || newValue.trim().length() < 12) {
                return;
            }

            String lrn = newValue.trim();
            LOGGER.info("Attempting to autofill for LRN: {}", lrn);
            try {
                List<MedicalRecord> existingStudentRecord = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(lrn);

                if (existingStudentRecord != null && !existingStudentRecord.isEmpty()) {
                    MedicalRecord matchedStudent = existingStudentRecord.get(0);
                    String fullName = Stream.of(matchedStudent.getFirstName(), matchedStudent.getMiddleName(), matchedStudent.getLastName())
                            .filter(part -> part != null && !part.isBlank())
                            .map(String::trim)
                            .collect(Collectors.joining(" "));
                    nameField.setText(fullName);
                    String grade = matchedStudent.getGradeLevel() != null ? matchedStudent.getGradeLevel().trim() : "";
                    String section = matchedStudent.getSection() != null ? matchedStudent.getSection().trim() : "";
                    gradeSectionField.setText(grade + " - " + section);
                    ageField.setText(String.valueOf(matchedStudent.getAge()));
                }
            } catch (Exception e) {
                showDialog("Warning", "Error retrieving student data. Please check LRN input.");
                LOGGER.warn("Exception occurred while retrieving student info for LRN '{}': {}", lrn, e.getMessage(), e);
            }
        });
    }

    private void setupMedicineNameComboBoxDisplay() {
        medicineNameComboBox.setEditable(true);
        medicineNameComboBox.setConverter(new StringConverter<Medicine>() {
            @Override
            public String toString(Medicine medicine) {
                return (medicine == null) ? "" : medicine.getItemName();
            }
            @Override
            public Medicine fromString(String string) {
                if (string == null || string.trim().isEmpty()) return null;
                return inventoryApp.getMedicineInventoryFacade().getAllMedicine().stream()
                        .filter(m -> m.getItemName().equalsIgnoreCase(string.trim()))
                        .findFirst()
                        .orElse(null);
            }
        });
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
            showDialog("Warning", "Dispensing quantity must be a number.");
            return;
        } else if (datePickerTextField.getValue() == null) {
            showDialog("Warning", "Visit date must be selected.");
            return;
        }

        addDailyRecord();
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    private void addDailyRecord() {
        try {
            MedicalRecord medicalRecord = createStudentMedicalRecordFromForm();
            if (medicalRecord == null) return;

            Medicine matchedMedicine = SelectedMedicine(medicineNameComboBox.getEditor().getText());
            if (matchedMedicine == null) return;
            if (!updateInventoryDispensed(medicalRecord, matchedMedicine)) return;
            if (!populateExistingStudentInfo(medicalRecord)) return;
            medicalRecord.setMedicineId(matchedMedicine.getMedicineId());
            Employee selectedNurse = nurseInChargeComboBox.getSelectionModel().getSelectedItem();
            if (selectedNurse == null || selectedNurse.getNurseInChargeId() == null) {
                showDialog("Error", "Please select a nurse in charge.");
                return;
            }
            if (!saveMedicalRecordAndTreatment(medicalRecord, selectedNurse)) return;
            if (clinicVisitLogPageController != null) {
                clinicVisitLogPageController.addStudentMedicalRecord(medicalRecord);
            }

            showDialog("Notification", "Success, Record Added Successfully.");
            ((Stage) lrnField.getScene().getWindow()).close();

        } catch (Exception e) {
            LOGGER.error("Unhandled exception in addDailyRecord", e);
            showDialog("Error", "Unexpected error. Record not saved.");
        }
    }

    private MedicalRecord createStudentMedicalRecordFromForm() {
        MedicalRecord patient = new MedicalRecord();
        Employee employee = new Employee();

        String lrn = lrnField.getText().trim();
        double temperature = Double.parseDouble(bodyTempField.getText().trim());
        int pulse = Integer.parseInt(pulseRateField.getText().trim());
        int respiration = Integer.parseInt(respiratoryRateField.getText().trim());
        int dispensingOut = Integer.parseInt(invDispensingOutField.getText().trim());

        patient.setLrn(lrn);
        String[] nameParts = nameField.getText().trim().split("\\s+");
        patient.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        patient.setMiddleName(nameParts.length == 3 ? nameParts[1] : "");
        patient.setLastName(nameParts.length == 3 ? nameParts[2] : (nameParts.length == 2 ? nameParts[1] : ""));
        String[] parts = gradeSectionField.getText().split(" - ");
        patient.setGradeLevel(parts.length > 0 ? parts[0].trim() : "");
        patient.setSection(parts.length > 1 ? parts[1].trim() : "");
        patient.setTemperatureReadings(String.valueOf(temperature));
        patient.setPulseRate(pulse);
        patient.setRespiratoryRate(respiration);
        patient.setBloodPressure(bloodPressureField.getText().trim());
        patient.setDispensingOut(dispensingOut);
        patient.setSymptoms(symptomsField.getText().trim());
        patient.setTreatment(treatmentField.getText().trim());
        Medicine selectedMedicine = medicineNameComboBox.getValue();
        if (selectedMedicine != null) {
            patient.setMedicineName(selectedMedicine.getItemName());
        } else {
            String fallback = medicineNameComboBox.getEditor().getText().trim();
            patient.setMedicineName(fallback);
        }
        Employee selectedNurse = nurseInChargeComboBox.getSelectionModel().getSelectedItem();
        if (selectedNurse != null) {
            employee.setNurseInChargeId(selectedNurse.getNurseInChargeId());
            String fullName = Stream.of(selectedNurse.getFirstName(), selectedNurse.getMiddleName(), selectedNurse.getLastName()).filter(n -> n != null && !n.isBlank())
                    .map(String::trim)
                    .collect(Collectors.joining(" "));
            patient.setNurseInCharge(fullName);
        }
        LocalDate selectedDate = datePickerTextField.getValue();
        patient.setVisitDate(Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return patient;
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

    private boolean updateInventoryDispensed(MedicalRecord medicalRecord, Medicine medicine) {
        int dispensingQty = medicalRecord.getDispensingOut();
        int updatedQty = medicine.getQuantity() - dispensingQty;

        if (updatedQty <= 0) {
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

    private boolean populateExistingStudentInfo(MedicalRecord medicalRecord) {
        List<MedicalRecord> existingStudentRecord = medicalRecordInfoMgtApplication
                .getStudentMedicalRecordFacade()
                .getMedicalInformationByLRN(medicalRecord.getLrn());

        if (existingStudentRecord == null || existingStudentRecord.isEmpty()) {
            showDialog("Warning", "Student with this LRN was not found. Please register the student first.");
            return false;
        }

        MedicalRecord existingStudent = existingStudentRecord.get(0);
        medicalRecord.setStudentId(existingStudent.getStudentId());
        medicalRecord.setFirstName(existingStudent.getFirstName());
        medicalRecord.setMiddleName(existingStudent.getMiddleName());
        medicalRecord.setLastName(existingStudent.getLastName());
        medicalRecord.setGradeLevel(existingStudent.getGradeLevel());
        medicalRecord.setSection(existingStudent.getSection());

        return true;
    }

    private boolean saveMedicalRecordAndTreatment(MedicalRecord medicalRecord, Employee employee) {
        try {
            boolean saved = medicalRecordInfoMgtApplication.getPatientMedicalRecordFacade().addMedicalRecord(medicalRecord, employee);
            if (!saved) {
                showDialog("Warning", "Failed to save medical record.");
                return false;
            }
            if (medicalRecord.getMedicalRecordId() == null) {
                showDialog("Warning", "Medical record ID was not assigned. Cannot proceed with medicine administration.");
                return false;
            }

            boolean administered = medicalRecordInfoMgtApplication.getPatientMedicalRecordFacade().addMedicineAdministered(medicalRecord, employee);
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

}