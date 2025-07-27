package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

import com.rocs.infirmary.application.data.model.person.student.Student;
//import com.rocs.infirmary.application.data.model.person.student.Patient;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Date;

/**
 * The StudentMedicalRecordDaoImpl class implements the StudentMedicalRecordDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving, adding, updating, and deleting student medical records.
 */
public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    public MedicalRecord findMedicalInformation(String LRN) {
        LOGGER.info("Starting medical record retrieval for LRN: {}", LRN);
        MedicalRecord MedicalRecord = null;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN)) {

            LOGGER.info("Preparing query: {}", GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            stmt.setString(1, LRN);
            LOGGER.info("Executing query with LRN: {}", LRN);

            try (ResultSet rs = stmt.executeQuery()) {
                LOGGER.info("Query executed successfully");

                if (rs.next()) {
                    MedicalRecord = new MedicalRecord();
                    MedicalRecord.setStudentId(rs.getLong("student_id"));
                    MedicalRecord.setStudentLrn(rs.getString("LRN"));
                    MedicalRecord.setFirstName(rs.getString("first_name"));
                    MedicalRecord.setMiddleName(rs.getString("middle_name"));
                    MedicalRecord.setLastName(rs.getString("last_name"));
                    MedicalRecord.setAge(rs.getInt("age"));
                    MedicalRecord.setGradeLevel(rs.getString("grade_level"));
                    MedicalRecord.setSection(rs.getString("section"));
                    MedicalRecord.setGender(rs.getString("gender"));
                    MedicalRecord.setSymptoms(rs.getString("symptoms"));
                    MedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                    MedicalRecord.setVisitDate(rs.getDate("visit_date"));
                    MedicalRecord.setTreatment(rs.getString("treatment"));
                    MedicalRecord.setMedicineName(rs.getString("medicine_name"));
                    MedicalRecord.setDispensingOut(rs.getInt("dispensing_out"));

                    LOGGER.info("Data retrieved:" + "\n"
                            + "Student ID        : " + MedicalRecord.getStudentId() + "\n"
                            + "LRN               : " + MedicalRecord.getStudentLrn() + "\n"
                            + "Name              : " + MedicalRecord.getFirstName() + " " + MedicalRecord.getMiddleName()  + " " + MedicalRecord.getLastName() + "\n"
                            + "Age               : " + MedicalRecord.getAge() + "\n"
                            + "Grade Level       : " + MedicalRecord.getGradeLevel() + " " + MedicalRecord.getSection() + "\n"
                            + "Gender            : " + MedicalRecord.getGender() + "\n"
                            + "Symptoms          : " + MedicalRecord.getSymptoms() + "\n"
                            + "Temperature Readings : " + MedicalRecord.getTemperatureReadings() + "\n"
                            + "Visit Date        : " + MedicalRecord.getVisitDate() + "\n"
                            + "Treatment         : " + MedicalRecord.getTreatment() + "\n"
                            + "Medicine Name     : " + MedicalRecord.getMedicineName() + "\n"
                            + "Dispensing Out    : " + MedicalRecord.getDispensingOut()
                    );
                } else {
                    LOGGER.warn("No medical record found for LRN: {}", LRN);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException occurred while retrieving medical information: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return MedicalRecord;
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



