package com.rocs.infirmary.application.controller.modal;

import com.rocs.infirmary.application.controller.helper.ControllerHelper;
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
        addDailyRecord();

    }

    @FXML
    private void addDailyRecord() {
        try {
            Student record = createStudentMedicalRecordFromForm();
            if (record == null) return;

            Medicine selectedMedicine = medicineNameComboBox.getSelectionModel().getSelectedItem();
            if (selectedMedicine == null) {
                showDialog("Warning", "Please select a medicine from the dropdown before saving.");
                return;
            }

            int dispensingQty = record.getDispensingOut();
            int currentQty = selectedMedicine.getQuantity();
            int updatedQty = currentQty - dispensingQty;

            if (updatedQty < 0) {
                showDialog("Stock Warning", "Not enough stock to dispense.");
                return;
            }
            try {
                boolean inventoryUpdated = inventoryApp.getMedicineInventoryFacade().updateMedicineInventory(selectedMedicine.getInventoryId(), selectedMedicine.getMedicineId(), updatedQty, null, null);
                if (!inventoryUpdated) {
                    showDialog("Warning", "Failed to deduct medicine quantity.");
                    return;
                }
            } catch (Exception invEx) {
                LOGGER.error("Inventory deduction error", invEx);
                showDialog("Error", "Inventory update failed. Record not saved.");
                return;
            }

            Student existingStudent = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().getMedicalInformationByLRN(record.getLrn());
            if (existingStudent == null) {
                showDialog("Warning", "Student with this LRN was not found. Please register the student first.");
                return;
            }
            record.setStudentId(existingStudent.getStudentId());
            record.setMedicineId(selectedMedicine.getMedicineId());
            try {
                Long medRecordId = medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addStudentMedicalRecord(record);
                if (medRecordId == null) {
                    showDialog("Warning", "Failed to save medical record.");
                    return;
                }
                record.setMedicalRecordId(medRecordId);
                medicalRecordInfoMgtApplication.getStudentMedicalRecordFacade().addMedicineAdministered(record);
            } catch (Exception medEx) {
                LOGGER.error("Error saving treatment record or administering medicine", medEx);
                showDialog("Error", "Failed to save treatment record.");
                return;
            }

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
    private void addLrnAutoFillListener() {
        lrnField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isBlank()) return;
            try {
                long lrn = Long.parseLong(newValue.trim());
                Student existing = medicalRecordInfoMgtApplication
                        .getStudentMedicalRecordFacade()
                        .getMedicalInformationByLRN(lrn);
                if (existing != null) {
                    List<String> nameParts = Arrays.asList(
                            existing.getFirstName(),
                            existing.getMiddleName(),
                            existing.getLastName()
                    );

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
        if (lrnField.getText().isBlank()
                || nameField.getText().isBlank()
                || gradeSectionField.getText().isBlank()
                || bodyTempField.getText().isBlank()
                || pulseRateField.getText().isBlank()
                || respiratoryRateField.getText().isBlank()
                || bloodPressureField.getText().isBlank()
                || symptomsField.getText().isBlank()
                || treatmentField.getText().isBlank()
                || invDispensingOutField.getText().isBlank()
                || datePickerTextField.getValue() == null) {
            ControllerHelper.showDialog("Incomplete Form", "Please complete all required fields before proceeding.");
            return null;
        }

        long lrn;
        double temperature;
        int pulse, respiration, dispensingOut;
        try {
            lrn = Long.parseLong(lrnField.getText().trim());
            temperature = Double.parseDouble(bodyTempField.getText().trim());
            pulse = Integer.parseInt(pulseRateField.getText().trim());
            respiration = Integer.parseInt(respiratoryRateField.getText().trim());
            dispensingOut = Integer.parseInt(invDispensingOutField.getText().trim());
        } catch (NumberFormatException e) {
            ControllerHelper.showDialog("Warning", "Please ensure all numeric fields contain valid values.");
            return null;
        }

        Student student = new Student();
        student.setLrn(lrn);

        String[] nameParts = nameField.getText().trim().split("\\s+");
        student.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        student.setMiddleName(nameParts.length == 3 ? nameParts[1] : "");
        student.setLastName(nameParts.length == 3 ? nameParts[2]
                : (nameParts.length == 2 ? nameParts[1] : ""));
        String[] parts = gradeSectionField.getText().split(" - ");
        student.setGradeLevel(parts.length > 0 ? parts[0].trim() : "");
        student.setSection(parts.length > 1 ? parts[1].trim() : "");
        student.setTemperatureReadings(String.valueOf(temperature));
        student.setPulseRate(pulse);
        student.setRespiratoryRate(respiration);
        student.setDispensingOut(dispensingOut);
        student.setBloodPressure(bloodPressureField.getText());
        student.setSymptoms(symptomsField.getText());
        student.setTreatment(treatmentField.getText());

        Student selectedNurse = nurseInChargeComboBox != null ? nurseInChargeComboBox.getSelectionModel().getSelectedItem() : null;
        if (selectedNurse != null) {
            student.setNurseInChargeId((Long) selectedNurse.getStudentId());
            student.setNurseInCharge(selectedNurse.getFirstName() + " " + selectedNurse.getLastName());
        }
        Medicine selectedMedicine = medicineNameComboBox != null ? medicineNameComboBox.getValue() : null;
        if (selectedMedicine != null) {
            student.setMedicineId(selectedMedicine.getMedicineId());
            student.setMedicineName(selectedMedicine.getItemName());
        }
        LocalDate selectedDate = datePickerTextField.getValue();
        Date convertedDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        student.setVisitDate(convertedDate);

        return student;
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

}