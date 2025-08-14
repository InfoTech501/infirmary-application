package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
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

    public List<MedicalRecord> findMedicalInformation(String LRN) {
        LOGGER.info("Starting medical record retrieval for LRN: {}", LRN);
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN)) {

            LOGGER.info("Preparing query: {}", GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            stmt.setString(1, LRN);
            LOGGER.info("Executing query with LRN: {}", LRN);

            try (ResultSet rs = stmt.executeQuery()) {
                LOGGER.info("Query executed successfully");

                while (rs.next()) {
                    MedicalRecord medicalRecord = new MedicalRecord();
                    medicalRecord.setStudentId(rs.getLong("student_id"));
                    medicalRecord.setLrn(rs.getString("LRN"));
                    medicalRecord.setFirstName(rs.getString("first_name"));
                    medicalRecord.setMiddleName(rs.getString("middle_name"));
                    medicalRecord.setLastName(rs.getString("last_name"));
                    medicalRecord.setAge(rs.getInt("age"));
                    medicalRecord.setGradeLevel(rs.getString("grade_level"));
                    medicalRecord.setSection(rs.getString("section"));
                    medicalRecord.setGender(rs.getString("gender"));
                    medicalRecord.setSymptoms(rs.getString("symptoms"));
                    medicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                    medicalRecord.setVisitDate(rs.getDate("visit_date"));
                    medicalRecord.setTreatment(rs.getString("treatment"));
                    medicalRecord.setMedicineName(rs.getString("medicine_name"));
                    medicalRecord.setDispensingOut(rs.getInt("dispensing_out"));

                    LOGGER.info("Data retrieved:" + "\n"
                            + "Student ID        : " + medicalRecord.getStudentId() + "\n"
                            + "LRN               : " + medicalRecord.getLrn() + "\n"
                            + "Name              : " + medicalRecord.getFirstName() + " " + medicalRecord.getMiddleName()  + " " + medicalRecord.getLastName() + "\n"
                            + "Age               : " + medicalRecord.getAge() + "\n"
                            + "Grade Level       : " + medicalRecord.getGradeLevel() + " " + medicalRecord.getSection() + "\n"
                            + "Gender            : " + medicalRecord.getGender() + "\n"
                            + "Symptoms          : " + medicalRecord.getSymptoms() + "\n"
                            + "Temperature Readings : " + medicalRecord.getTemperatureReadings() + "\n"
                            + "Visit Date        : " + medicalRecord.getVisitDate() + "\n"
                            + "Treatment         : " + medicalRecord.getTreatment() + "\n"
                            + "Medicine Name     : " + medicalRecord.getMedicineName() + "\n"
                            + "Dispensing Out    : " + medicalRecord.getDispensingOut()
                    );
                    medicalRecords.add(medicalRecord);
                }
                if (medicalRecords.isEmpty()) {
                    LOGGER.warn("No medical records found for LRN: {}", LRN);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred while retrieving medical information: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return medicalRecords;
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
    public boolean deleteStudentMedicalRecord(String LRN, Long medicalRecordId) {
        LOGGER.info("Delete medical records started");
        Student studentMedicalRecord = getStudent(LRN);

        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement preparedStatement = con.prepareStatement(DELETE_STUDENT_MEDICAL_RECORD);
            LOGGER.info("Query in use" + DELETE_STUDENT_MEDICAL_RECORD);
            preparedStatement.setLong(1, medicalRecordId);
            preparedStatement.setLong(2, studentMedicalRecord.getStudentId());
            LOGGER.info("data inserted: " + "LRN: " + LRN + "Medical record id:" + medicalRecordId);
            int affectedRow = preparedStatement.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, String LRN, Long medicalRecordId) {
        LOGGER.info("Update student medical record started for LRN: {} and medical record ID: {}", LRN, medicalRecordId);
        boolean updateSuccessful = false;

        if (medicalRecordId == null) {
            LOGGER.error("Medical record ID cannot be null");
            return false;
        }

        try (Connection con = ConnectionHelper.getConnection()) {
            con.setAutoCommit(false);
            try {
                if (symptoms != null && !symptoms.trim().isEmpty()) {
                    String query = UPDATE_STUDENT_SYMPTOMS;
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        LOGGER.info("Executing update for symptoms.");
                        stmt.setString(1, symptoms);
                        stmt.setLong(2, medicalRecordId);
                        LOGGER.info("Symptoms: {}, Medical record id: {}", symptoms, medicalRecordId);
                        int rows = stmt.executeUpdate();
                        LOGGER.info("Symptoms updated. Rows affected: {}", rows);
                        updateSuccessful = rows > 0;
                    }
                }

                if (temperatureReadings != null && !temperatureReadings.trim().isEmpty()) {
                    String query = UPDATE_STUDENT_TEMPERATURE_READINGS;
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        LOGGER.info("Executing update for temperature readings.");
                        stmt.setString(1, temperatureReadings);
                        stmt.setLong(2, medicalRecordId);
                        LOGGER.info("TemperatureReadings: {}, Medical record id: {}", temperatureReadings, medicalRecordId);
                        int rows = stmt.executeUpdate();
                        LOGGER.info("Temperature readings updated. Rows affected: {}", rows);
                        updateSuccessful = updateSuccessful || rows > 0;
                    }
                }

                if (visitDate != null) {
                    String query = UPDATE_STUDENT_VISIT_DATE;
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        LOGGER.info("Executing update for visit date.");
                        stmt.setTimestamp(1, new java.sql.Timestamp(visitDate.getTime()));
                        stmt.setLong(2, medicalRecordId);
                        LOGGER.info("Parameters - visitDate: {}, Medical record id: {}", visitDate, medicalRecordId);
                        int rows = stmt.executeUpdate();
                        LOGGER.info("Visit date updated. Rows affected: {}", rows);
                        updateSuccessful = updateSuccessful || rows > 0;
                    }
                }

                if (treatment != null && !treatment.trim().isEmpty()) {
                    String query = UPDATE_STUDENT_TREATMENT;
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        LOGGER.info("Executing update for treatment");
                        stmt.setString(1, treatment);
                        stmt.setLong(2, medicalRecordId);
                        LOGGER.info("Parameters - treatment: {}, Medical Record ID: {}", treatment, medicalRecordId);
                        int rows = stmt.executeUpdate();
                        LOGGER.info("Treatment updated, Rows affected: {}", rows);
                        updateSuccessful = updateSuccessful || rows > 0;
                    }
                }

                if (updateSuccessful) {
                    con.commit();
                    LOGGER.info("All updates committed successfully on medical record id: {}", medicalRecordId);
                } else {
                    con.rollback();
                    LOGGER.warn("No updates made the transaction rolled back");
                }

            } catch (SQLException e) {
                con.rollback();
                LOGGER.error("Error on update the transaction rolled back: {}", e.getMessage());
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

            LOGGER.info("Update Student medical record completed for LRN: {} and Medical record id: {}", LRN, medicalRecordId);
            return updateSuccessful;

        } catch (SQLException e) {
            LOGGER.error("SQL Exception Occurred: {}", e.getMessage());
            throw new RuntimeException("Failed to update medical record", e);
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



