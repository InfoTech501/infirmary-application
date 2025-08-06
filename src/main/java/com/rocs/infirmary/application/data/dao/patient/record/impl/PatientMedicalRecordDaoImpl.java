package com.rocs.infirmary.application.data.dao.patient.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.record.QueryConstants.*;

import com.rocs.infirmary.application.data.dao.patient.record.PatientMedicalRecordDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The PatientMedicalRecordDaoImpl class implements the PatientMedicalRecordDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving and adding patient medical records.
 */
public class PatientMedicalRecordDaoImpl implements PatientMedicalRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    @Override
    public List<MedicalRecord> findAllPatientMedicalRecords() {
        LOGGER.info("Fetching all student medical records...");
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_STUDENTS_MEDICAL_RECORDS);
             ResultSet rs = stmt.executeQuery()) {

            LOGGER.info("Executing query: {}", GET_ALL_STUDENTS_MEDICAL_RECORDS);

            while (rs.next()) {
                try {
                    MedicalRecord record = new MedicalRecord();
                    record.setStudentId(rs.getLong("person_id"));
                    record.setLrn(rs.getString("LRN"));
                    record.setFirstName(rs.getString("first_name"));
                    record.setMiddleName(rs.getString("middle_name"));
                    record.setLastName(rs.getString("last_name"));
                    record.setGradeLevel(rs.getString("grade_level"));
                    record.setSection(rs.getString("section"));
                    record.setAge(rs.getInt("age"));
                    record.setGender(rs.getString("gender"));
                    record.setEmail(rs.getString("email"));
                    record.setAddress(rs.getString("address"));
                    record.setContactNumber(rs.getString("contact_number"));
                    record.setSymptoms(rs.getString("symptoms"));
                    record.setTemperatureReadings(rs.getString("temperature_readings"));
                    record.setBloodPressure(rs.getString("blood_pressure"));
                    record.setPulseRate(rs.getInt("pulse_rate"));
                    record.setRespiratoryRate(rs.getInt("respiratory_rate"));
                    record.setVisitDate(rs.getDate("visit_date"));
                    record.setTreatment(rs.getString("treatment"));
                    record.setMedicineName(rs.getString("medicine_name"));
                    record.setDispensingOut(rs.getInt("medicine_quantity"));

                    String nurseFirst = rs.getString("nurse_first_name");
                    String nurseLast = rs.getString("nurse_last_name");
                    record.setNurseInCharge((nurseFirst + " " + nurseLast).trim());

                    LOGGER.info("Retrieved Patient Record:\n"
                            + "Student ID       : " + record.getStudentId() + "\n"
                            + "LRN              : " + record.getLrn() + "\n"
                            + "Name             : " + record.getFirstName() + " " + record.getLastName() + "\n"
                            + "Grade Level      : " + record.getGradeLevel() + "\n"
                            + "Section          : " + record.getSection() + "\n"
                            + "Age              : " + record.getAge() + "\n"
                            + "Gender           : " + record.getGender() + "\n"
                            + "Email            : " + record.getEmail() + "\n"
                            + "Address          : " + record.getAddress() + "\n"
                            + "Contact Number   : " + record.getContactNumber() + "\n"
                            + "Symptoms         : " + record.getSymptoms() + "\n"
                            + "Temperature      : " + record.getTemperatureReadings() + "\n"
                            + "Blood Pressure   : " + record.getBloodPressure() + "\n"
                            + "Pulse Rate       : " + record.getPulseRate() + "\n"
                            + "Respiratory Rate : " + record.getRespiratoryRate() + "\n"
                            + "Visit Date       : " + record.getVisitDate() + "\n"
                            + "Treatment        : " + record.getTreatment() + "\n"
                            + "Nurse In-Charge  : " + record.getNurseInCharge() + "\n"
                            + "Medicine Name    : " + record.getMedicineName() + "\n"
                            + "Dispensing Out   : " + record.getDispensingOut());

                    medicalRecords.add(record);

                } catch (Exception ex) {
                    LOGGER.warn("Error mapping record. Row skipped: {}", ex.getMessage());
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException occurred while retrieving student medical records: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching student medical records", e);
        }

        return medicalRecords;
    }

    public boolean addMedicalRecord(MedicalRecord medicalRecord, Employee employee) {
        try (Connection con = ConnectionHelper.getConnection()) {
            con.setAutoCommit(false);

            Long ailmentId = findAilmentIdBySymptoms(con, medicalRecord.getSymptoms());
            if (ailmentId == null) {
                ailmentId = addNewAilment(con, medicalRecord.getSymptoms());
            }
            if (ailmentId == null) {
                LOGGER.warn("No ailment_id found or inserted for symptoms: {}", medicalRecord.getSymptoms());
                return false;
            }

            try (PreparedStatement medStmt = con.prepareStatement(ADD_STUDENT_MEDICAL_RECORD)) {
                medStmt.setLong(1, medicalRecord.getStudentId());
                medStmt.setLong(2, ailmentId);
                medStmt.setLong(3, employee.getNurseInChargeId());
                medStmt.setString(4, medicalRecord.getSymptoms());
                medStmt.setString(5, medicalRecord.getTemperatureReadings());
                medStmt.setString(6, medicalRecord.getBloodPressure());
                medStmt.setInt(7, medicalRecord.getPulseRate());
                medStmt.setInt(8, medicalRecord.getRespiratoryRate());
                medStmt.setTimestamp(9, new Timestamp(medicalRecord.getVisitDate().getTime()));
                medStmt.setString(10, medicalRecord.getTreatment());
                medStmt.setInt(11, 1);

                int affectedRows = medStmt.executeUpdate();
                if (affectedRows > 0) {
                    try (PreparedStatement selectStmt = con.prepareStatement(GET_LAST_INSERTED_MEDICAL_RECORD_ID)) {
                        selectStmt.setLong(1, medicalRecord.getStudentId());
                        try (ResultSet rs = selectStmt.executeQuery()) {
                            if (rs.next()) {
                                Long medRecordId = rs.getLong("id");
                                medicalRecord.setMedicalRecordId(medRecordId);
                                LOGGER.debug("Assigned medicalRecordId to patient: {}", medRecordId);
                                con.commit();
                                return true;
                            } else {
                                LOGGER.warn("Insert succeeded but no medical record ID returned.");
                                con.rollback();
                            }
                        }
                    }
                } else {
                    LOGGER.warn("Insert affected 0 rows for student ID {}", medicalRecord.getStudentId());
                    con.rollback();
                }

            } catch (SQLException e) {
                LOGGER.error("Error inserting medical record: {}", e.getMessage(), e);
                con.rollback();
            }

        } catch (SQLException e) {
            LOGGER.error("Connection or rollback failed: {}", e.getMessage(), e);
        }

        return false;
    }

    private Long findAilmentIdBySymptoms(Connection con, String symptoms) {
        String cleaned = symptoms.toLowerCase().trim();
        if (cleaned.isEmpty()) return null;

        try (PreparedStatement stmt = con.prepareStatement(FIND_AILMENT_ID_BY_SYMPTOMS)) {
            stmt.setString(1, cleaned);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Long existingId = rs.getLong("ailment_id");
                    LOGGER.info("Found existing ailment '{}', ID: {}", cleaned, existingId);
                    return existingId;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking existing ailment for '{}'", cleaned, e);
        }

        return null;
    }

    private Long addNewAilment(Connection con, String symptoms) {
        String cleaned = symptoms.toLowerCase().trim();
        if (cleaned.isEmpty()) return null;

        try (PreparedStatement insertStmt = con.prepareStatement(ADD_NEW_AILMENTS, new String[] { "ailment_id" })) {
            insertStmt.setString(1, cleaned);
            int affected = insertStmt.executeUpdate();

            if (affected > 0) {
                try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        Long newId = rs.getLong(1);
                        LOGGER.info("Inserted new ailment '{}', ID: {}", cleaned, newId);
                        return newId;
                    }
                }
            } else {
                LOGGER.warn("Insert failed for new ailment '{}'", cleaned);
            }
        } catch (SQLException e) {
            LOGGER.error("Error inserting new ailment '{}'", cleaned, e);
        }

        return null;
    }

    @Override
    public boolean addMedicineAdministered(MedicalRecord medicalRecord, Employee employee) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_MEDICINE_ADMINISTERED)) {

            stmt.setLong(1, medicalRecord.getMedicineId());
            stmt.setLong(2, medicalRecord.getMedicalRecordId());
            stmt.setLong(3, employee.getNurseInChargeId());
            stmt.setString(4, medicalRecord.getTreatment());
            stmt.setInt(5, medicalRecord.getDispensingOut());
            stmt.setTimestamp(6, new Timestamp(medicalRecord.getVisitDate().getTime()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.error("Failed to insert into medicine_administered", e);
            return false;
        }
    }
}
