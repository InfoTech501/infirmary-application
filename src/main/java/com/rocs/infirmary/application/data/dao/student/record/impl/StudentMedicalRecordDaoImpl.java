package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

import com.rocs.infirmary.application.data.model.person.nurse.Nurse;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.person.student.Patient;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The StudentMedicalRecordDaoImpl class implements the StudentMedicalRecordDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving, adding, updating, and deleting student medical records.
 */
public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    public Student findMedicalInformation(String LRN) {
        LOGGER.info("Starting medical record retrieval for LRN: {}", LRN);
        Patient studentMedicalRecord = null;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN)) {

            LOGGER.info("Preparing query: {}", GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            stmt.setString(1, LRN);
            LOGGER.info("Executing query with LRN: {}", LRN);

            try (ResultSet rs = stmt.executeQuery()) {
                LOGGER.info("Query executed successfully");

                if (rs.next()) {
                    studentMedicalRecord = new Patient();
                    studentMedicalRecord.setStudentId(rs.getLong("student_id"));
                    studentMedicalRecord.setLrn(rs.getString("LRN"));
                    studentMedicalRecord.setFirstName(rs.getString("first_name"));
                    studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                    studentMedicalRecord.setLastName(rs.getString("last_name"));
                    studentMedicalRecord.setAge(rs.getInt("age"));
                    studentMedicalRecord.setGradeLevel(rs.getString("grade_level"));
                    studentMedicalRecord.setSection(rs.getString("section"));
                    studentMedicalRecord.setGender(rs.getString("gender"));
                    studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                    studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                    studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                    studentMedicalRecord.setTreatment(rs.getString("treatment"));
                    studentMedicalRecord.setMedicineName(rs.getString("medicine_name"));
                    studentMedicalRecord.setDispensingOut(rs.getInt("dispensing_out"));

                    LOGGER.info("Data retrieved:" + "\n"
                            + "Student ID        : " + studentMedicalRecord.getStudentId() + "\n"
                            + "LRN               : " + studentMedicalRecord.getLrn() + "\n"
                            + "Name              : " + studentMedicalRecord.getFirstName() + " " + studentMedicalRecord.getMiddleName()  + " " + studentMedicalRecord.getLastName() + "\n"
                            + "Age               : " + studentMedicalRecord.getAge() + "\n"
                            + "Grade Level       : " + studentMedicalRecord.getGradeLevel() + " " + studentMedicalRecord.getSection() + "\n"
                            + "Gender            : " + studentMedicalRecord.getGender() + "\n"
                            + "Symptoms          : " + studentMedicalRecord.getSymptoms() + "\n"
                            + "Temperature Readings : " + studentMedicalRecord.getTemperatureReadings() + "\n"
                            + "Visit Date        : " + studentMedicalRecord.getVisitDate() + "\n"
                            + "Treatment         : " + studentMedicalRecord.getTreatment() + "\n"
                            + "Medicine Name     : " + studentMedicalRecord.getMedicineName() + "\n"
                            + "Dispensing Out    : " + studentMedicalRecord.getDispensingOut()
                    );
                } else {
                    LOGGER.warn("No medical record found for LRN: {}", LRN);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred while retrieving medical information: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return studentMedicalRecord;
    }

    @Override
    public List<Patient> findAllStudentMedicalRecords() {
        LOGGER.info("Fetching all student medical records...");
        List<Patient> medicalRecords = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_STUDENTS_MEDICAL_RECORDS);
             ResultSet rs = stmt.executeQuery()) {

            LOGGER.info("Executing query: {}", GET_ALL_STUDENTS_MEDICAL_RECORDS);

            while (rs.next()) {
                try {
                    Patient record = new Patient();
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

    @Override
    public List<Nurse> getAllNurseAccounts() {
        List<Nurse> nurses = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_NURSE_EMPLOYEE);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Nurse nurse = new Nurse();
                    nurse.setNurseInChargeId(rs.getLong("id"));
                    nurse.setFirstName(rs.getString("first_name"));
                    nurse.setMiddleName(rs.getString("middle_name"));
                    nurse.setLastName(rs.getString("last_name"));

                    String fullName = nurse.getFirstName() + " " + nurse.getLastName();
                    nurse.setNurseInCharge(fullName.trim());

                    nurses.add(nurse);
                    LOGGER.info("Mapped Nurse: {}", fullName.trim());
                } catch (Exception ex) {
                    LOGGER.warn("Error mapping nurse record. Row skipped: {}", ex.getMessage());
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching nurse accounts", e);
        }

        return nurses;
    }

    @Override
    public boolean addStudentMedicalRecord(Patient patient) {
        try (Connection con = ConnectionHelper.getConnection()) {
            con.setAutoCommit(false);

            Long ailmentId = addNewAilment(con, patient.getSymptoms());
            if (ailmentId == null) {
                LOGGER.warn("No ailment_id found for symptoms: {}", patient.getSymptoms());
                return false;
            }

            try (PreparedStatement medStmt = con.prepareStatement(ADD_STUDENT_MEDICAL_RECORD)) {
                medStmt.setLong(1, patient.getStudentId());
                medStmt.setLong(2, ailmentId);
                medStmt.setLong(3, patient.getNurseInChargeId());
                medStmt.setString(4, patient.getSymptoms());
                medStmt.setString(5, patient.getTemperatureReadings());
                medStmt.setString(6, patient.getBloodPressure());
                medStmt.setInt(7, patient.getPulseRate());
                medStmt.setInt(8, patient.getRespiratoryRate());
                medStmt.setTimestamp(9, new Timestamp(patient.getVisitDate().getTime()));
                medStmt.setString(10, patient.getTreatment());
                medStmt.setInt(11, 1);

                int affectedRows = medStmt.executeUpdate();
                if (affectedRows > 0) {
                    try (PreparedStatement selectStmt = con.prepareStatement(GET_LAST_INSERTED_MEDICAL_RECORD_ID)) {
                        selectStmt.setLong(1, patient.getStudentId());
                        try (ResultSet rs = selectStmt.executeQuery()) {
                            if (rs.next()) {
                                Long medRecordId = rs.getLong("id");
                                patient.setMedicalRecordId(medRecordId);
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
                    LOGGER.warn("Insert affected 0 rows for student ID {}", patient.getStudentId());
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

    private Long addNewAilment(Connection con, String symptoms) {
        String cleaned = symptoms.toLowerCase().trim();
        if (cleaned.isEmpty()) return null;

        try {
            try (PreparedStatement stmt = con.prepareStatement(FIND_AILMENT_ID_BY_SYMPTOMS)) {
                stmt.setString(1, cleaned);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Long existingId = rs.getLong("ailment_id");
                        LOGGER.info("Found existing ailment '{}', ID: {}", cleaned, existingId);
                        return existingId;
                    }
                }
            }

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
        } catch (SQLException e) {
            LOGGER.error("Error checking existing ailment for '{}'", cleaned, e);
        }
        return null;
    }

    @Override
    public boolean addMedicineAdministered(Patient patient) {
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_MEDICINE_ADMINISTERED)) {

            stmt.setLong(1, patient.getMedicineId());
            stmt.setLong(2, patient.getMedicalRecordId());
            stmt.setLong(3, patient.getNurseInChargeId());
            stmt.setString(4, patient.getTreatment());
            stmt.setInt(5, patient.getDispensingOut());
            stmt.setTimestamp(6, new Timestamp(patient.getVisitDate().getTime()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.error("Failed to insert into medicine_administered", e);
            return false;
        }
    }

    /**
     * Deactivates a student's medical record based on their LRN (Learner Reference Number).
     * Instead of completely removing the data, it likely updates the status
     * of the medical record in the database to indicate it's no longer active.
     * <p>
     * A status value of 0 means the record is no longer active (deleted),
     * while a status of 1 means the record is still active and present in the system.
     */
    @Override
    public boolean deleteStudentMedicalRecord(String LRN) {
        LOGGER.info("Delete medical records started");
        Student studentMedicalRecord = getStudent(LRN);

        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement preparedStatement = con.prepareStatement(DELETE_STUDENT_MEDICAL_RECORD);
            LOGGER.info("Query in use" + DELETE_STUDENT_MEDICAL_RECORD);
            preparedStatement.setLong(1, studentMedicalRecord.getStudentId());
            LOGGER.info("data inserted: " + "LRN: " + LRN);
            int affectedRow = preparedStatement.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, String LRN) {
        LOGGER.info("Update Student Medical Record Started for LRN: " + LRN);
        boolean updateSuccessful = false;

        try (Connection con = ConnectionHelper.getConnection()) {

            if (symptoms != null && !symptoms.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_SYMPTOMS)) {
                    LOGGER.info("Executing update for symptoms...");
                    LOGGER.info("Query: " + UPDATE_STUDENT_SYMPTOMS);
                    stmt.setString(1, symptoms);
                    stmt.setString(2, LRN);
                    LOGGER.info("Symptoms: " + symptoms + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Symptoms updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                } catch (SQLException e) {
                    LOGGER.info("SQL Exception Occurred on Symptoms " + symptoms);
                    System.out.println("SQL Exception Occurred when updating Symptom : " + e.getMessage());
                }
            }

            if (temperatureReadings != null && !temperatureReadings.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_TEMPERATURE_READINGS)) {
                    LOGGER.info("Executing update for temperature readings...");
                    LOGGER.info("Query: " + UPDATE_STUDENT_TEMPERATURE_READINGS);
                    stmt.setString(1, temperatureReadings);
                    stmt.setString(2, LRN);
                    LOGGER.info("TemperatureReadings: " + temperatureReadings + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Temperature readings updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                } catch (SQLException e) {
                    LOGGER.info("SQL Exception Occurred on Temperature Readings" + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Temperature Readings : " + e.getMessage());
                }
            }

            if (visitDate != null) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_VISIT_DATE)) {
                    LOGGER.info("Executing update for visit date...");
                    LOGGER.info("Query: " + UPDATE_STUDENT_VISIT_DATE);
                    stmt.setTimestamp(1, new java.sql.Timestamp(visitDate.getTime()));
                    stmt.setString(2, LRN);
                    LOGGER.info("Parameters - visitDate: " + visitDate + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Visit date updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                } catch (SQLException e) {
                    LOGGER.info("SQL Exception Occurred on Visit Date " + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Visit Date : " + e.getMessage());
                }
            }

            if (treatment != null && !treatment.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_TREATMENT)) {
                    LOGGER.info("Executing update for treatment");
                    LOGGER.info("Query: " + UPDATE_STUDENT_TREATMENT);
                    stmt.setString(1, treatment);
                    stmt.setString(2, LRN);
                    LOGGER.info("Parameters - treatment: " + treatment + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    updateSuccessful = rows > 0;
                } catch (SQLException e) {
                    LOGGER.info("SQL Exception Occurred on Treatment " + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Treatment : " + e.getMessage());
                }
            }

            LOGGER.info("Update Student Medical Record Completed for LRN: " + LRN);
            return updateSuccessful;

        } catch (SQLException e) {
            LOGGER.error("SQL Exception Occurred" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Student getStudent(String LRN) {
        Student studentMedicalRecord = null;
        LOGGER.info("Retrieving Student information");

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            LOGGER.info("Query in use" + GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            stmt.setString(1, LRN);
            LOGGER.info("data inserted: " + "LRN: " + LRN);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(resultSet.getLong("student_id"));
                LOGGER.info("Data retrieved: " + "\n"
                        + "Student ID   : " + studentMedicalRecord.getStudentId() + "\n"
                );
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return studentMedicalRecord;
    }

}



