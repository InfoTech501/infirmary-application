package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

import com.rocs.infirmary.application.data.model.person.student.Student;
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

    public Student findMedicalInformation(long LRN) {
        LOGGER.info("get medical record started");
        Student studentMedicalRecord = null;
        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            LOGGER.info("Query in use" + GET_ALL_MEDICAL_INFORMATION_BY_LRN);

            stmt.setLong(1, LRN);
            LOGGER.info("data inserted: " + "LRN: " + LRN);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(rs.getLong("student_id"));
                studentMedicalRecord.setLrn(rs.getLong("LRN"));
                studentMedicalRecord.setFirstName(rs.getString("first_name"));
                studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                studentMedicalRecord.setLastName(rs.getString("last_name"));
                studentMedicalRecord.setAge(rs.getInt("age"));
                studentMedicalRecord.setGender(rs.getString("gender"));
                studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                studentMedicalRecord.setTreatment(rs.getString("treatment"));

                LOGGER.info("Data retrieved: " + "\n"
                        + "Student ID: " + studentMedicalRecord.getStudentId() + "\n"
                        + "LRN  ID: " + studentMedicalRecord.getLrn() + "\n"
                        + "Name   : " + studentMedicalRecord.getFirstName() + " " + studentMedicalRecord.getLastName() + "\n"
                        + "Age    : " + studentMedicalRecord.getAge() + "\n"
                        + "Gender   : " + studentMedicalRecord.getGender() + "\n"
                        + "Symptoms : " + studentMedicalRecord.getSymptoms() + "\n"
                        + "Temperature Reading  : " + studentMedicalRecord.getTemperatureReadings() + "\n"
                        + "Visit Date  : " + studentMedicalRecord.getVisitDate() + "\n"
                        + "Treatment  : " + studentMedicalRecord.getTreatment()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return studentMedicalRecord;


    }

    @Override
    public List<Student> findAllStudentMedicalRecords() {
        LOGGER.info("Fetching all student medical records...");
        List<Student> medicalRecords = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_STUDENTS_MEDICAL_RECORDS);
             ResultSet rs = stmt.executeQuery()) {

            LOGGER.info("Executing query: " + GET_ALL_STUDENTS_MEDICAL_RECORDS);

            while (rs.next()) {
                try {
                    Student studentMedicalRecord = new Student();

                    studentMedicalRecord.setStudentId(rs.getLong("person_id"));
                    studentMedicalRecord.setLrn(rs.getLong("LRN"));
                    studentMedicalRecord.setFirstName(rs.getString("first_name"));
                    studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                    studentMedicalRecord.setLastName(rs.getString("last_name"));
                    studentMedicalRecord.setGradeLevel(rs.getString("grade_level"));
                    studentMedicalRecord.setSection(rs.getString("section"));
                    studentMedicalRecord.setAge(rs.getInt("age"));
                    studentMedicalRecord.setGender(rs.getString("gender"));
                    studentMedicalRecord.setEmail(rs.getString("email"));
                    studentMedicalRecord.setAddress(rs.getString("address"));
                    studentMedicalRecord.setContactNumber(rs.getString("contact_number"));
                    studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                    studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                    studentMedicalRecord.setBloodPressure(rs.getString("blood_pressure"));
                    studentMedicalRecord.setPulseRate(rs.getInt("pulse_rate"));
                    studentMedicalRecord.setRespiratoryRate(rs.getInt("respiratory_rate"));
                    studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                    studentMedicalRecord.setTreatment(rs.getString("treatment"));

                    String nurseFirst = rs.getString("nurse_first_name");
                    String nurseLast = rs.getString("nurse_last_name");
                    studentMedicalRecord.setNurseInCharge((nurseFirst + " " + nurseLast).trim());

                    studentMedicalRecord.setMedicineName(rs.getString("medicine_name"));
                    studentMedicalRecord.setDispensingOut(rs.getInt("medicine_quantity"));

                    LOGGER.info("Retrieved Student Record:\n"
                            + "Student ID       : " + studentMedicalRecord.getStudentId() + "\n"
                            + "LRN              : " + studentMedicalRecord.getLrn() + "\n"
                            + "Name             : " + studentMedicalRecord.getFirstName() + " " + studentMedicalRecord.getLastName() + "\n"
                            + "Grade Level      : " + studentMedicalRecord.getGradeLevel() + "\n"
                            + "Section          : " + studentMedicalRecord.getSection() + "\n"
                            + "Age              : " + studentMedicalRecord.getAge() + "\n"
                            + "Gender           : " + studentMedicalRecord.getGender() + "\n"
                            + "Email            : " + studentMedicalRecord.getEmail() + "\n"
                            + "Address          : " + studentMedicalRecord.getAddress() + "\n"
                            + "Contact Number   : " + studentMedicalRecord.getContactNumber() + "\n"
                            + "Symptoms         : " + studentMedicalRecord.getSymptoms() + "\n"
                            + "Temperature      : " + studentMedicalRecord.getTemperatureReadings() + "\n"
                            + "Blood Pressure   : " + studentMedicalRecord.getBloodPressure() + "\n"
                            + "Pulse Rate       : " + studentMedicalRecord.getPulseRate() + "\n"
                            + "Respiratory Rate : " + studentMedicalRecord.getRespiratoryRate() + "\n"
                            + "Visit Date       : " + studentMedicalRecord.getVisitDate() + "\n"
                            + "Treatment        : " + studentMedicalRecord.getTreatment() + "\n"
                            + "Nurse In-Charge  : " + studentMedicalRecord.getNurseInCharge() + "\n"
                            + "Medicine Name    : " + studentMedicalRecord.getMedicineName() + "\n"
                            + "Dispensing Out   : " + studentMedicalRecord.getDispensingOut());

                    medicalRecords.add(studentMedicalRecord);

                } catch (Exception ex) {
                    LOGGER.warn("Error mapping record. Row skipped: " + ex.getMessage());
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage(), e);
            throw new RuntimeException("Error fetching student medical records", e);
        }

        return medicalRecords;
    }

    @Override
    public List<Student> getAllNurseAccounts() {
        List<Student> nurses = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_NURSE_EMPLOYEE);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Student nurse = new Student();
                    nurse.setStudentId(rs.getLong("id"));
                    nurse.setFirstName(rs.getString("first_name"));
                    nurse.setMiddleName(rs.getString("middle_name"));
                    nurse.setLastName(rs.getString("last_name"));
                    nurses.add(nurse);
                    LOGGER.info("Mapped Nurse: {} {}", nurse.getFirstName(), nurse.getLastName());
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
    public boolean addStudentMedicalRecord(Student record) {
        try (Connection con = ConnectionHelper.getConnection()) {
            con.setAutoCommit(false);

            Long ailmentId = findAilmentIdBySymptoms(con, record.getSymptoms());
            if (ailmentId == null) {
                LOGGER.warn("No ailment_id found for symptoms: {}", record.getSymptoms());
                return false;
            }

            try (PreparedStatement medStmt = con.prepareStatement(ADD_STUDENT_MEDICAL_RECORD)) {
                medStmt.setLong(1, record.getStudentId());
                medStmt.setLong(2, ailmentId);
                medStmt.setLong(3, record.getNurseInChargeId());
                medStmt.setString(4, record.getSymptoms());
                medStmt.setString(5, record.getTemperatureReadings());
                medStmt.setString(6, record.getBloodPressure());
                medStmt.setInt(7, record.getPulseRate());
                medStmt.setInt(8, record.getRespiratoryRate());
                medStmt.setTimestamp(9, new Timestamp(record.getVisitDate().getTime()));
                medStmt.setString(10, record.getTreatment());
                medStmt.setInt(11, 1);

                int affectedRow = medStmt.executeUpdate();
                return affectedRow > 0;
            } catch (SQLException e) {
                LOGGER.error("Error saving medical record ", e);
                con.rollback();
            }

        } catch (SQLException e) {
            LOGGER.error("Connection or rollback failed ", e);
        }

        return false;
    }

    private Long findAilmentIdBySymptoms(Connection con, String symptoms) {
        try (PreparedStatement stmt = con.prepareStatement(FIND_AILMENT_ID_BY_SYMPTOMS)) {
            stmt.setString(1, "%" + symptoms.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("ailment_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("Failed to match symptoms to ailment_id ", e);
        }
        return null;
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
    public boolean deleteStudentMedicalRecord(long LRN) {
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
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, long LRN) {
        LOGGER.info("Update Student Medical Record Started for LRN: " + LRN);
        boolean updateSuccessful = false;

        try (Connection con = ConnectionHelper.getConnection()) {

            if (symptoms != null && !symptoms.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_SYMPTOMS)) {
                    LOGGER.info("Executing update for symptoms...");
                    LOGGER.info("Query: " + UPDATE_STUDENT_SYMPTOMS);
                    stmt.setString(1, symptoms);
                    stmt.setLong(2, LRN);
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
                    stmt.setLong(2, LRN);
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
                    stmt.setLong(2, LRN);
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
                    stmt.setLong(2, LRN);
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

    private static Student getStudent(long LRN) {
        Student studentMedicalRecord = null;
        LOGGER.info("Retrieving Student information");

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            LOGGER.info("Query in use" + GET_ALL_MEDICAL_INFORMATION_BY_LRN);
            stmt.setLong(1, LRN);
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



