package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.dao.student.record.StudentMedicalRecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

/**
 * The StudentMedicalRecordDaoImpl class implements the StudentMedicalRecordDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving, adding, updating, and deleting student medical records.
 */
public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    @Override
    public List<Student> findMedicalInformation(String LRN) {
        LOGGER.info("get medical record started");
        List<Student> studentMedicalRecords = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(GET_ALL_ACTIVE_MEDICAL_INFORMATION_BY_LRN);
            LOGGER.info("Query in use"+ stmt);
            stmt.setString(1, LRN);
            LOGGER.info("data inserted: "+"LRN: "+LRN);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Student studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(rs.getInt("student_id"));
                studentMedicalRecord.setMedicalRecordId(rs.getLong("medical_record_id"));
                studentMedicalRecord.setLrn(rs.getString("LRN"));
                studentMedicalRecord.setMedicalRecordStatus(rs.getInt("is_active"));
                studentMedicalRecord.setFirstName(rs.getString("first_name"));
                studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                studentMedicalRecord.setLastName(rs.getString("last_name"));
                studentMedicalRecord.setAge(rs.getInt("age"));
                studentMedicalRecord.setGender(rs.getString("gender"));
                studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                studentMedicalRecord.setPulseRate(rs.getLong("pulse_rate"));
                studentMedicalRecord.setRespiratoryRate(rs.getLong("respiratory_rate"));
                studentMedicalRecord.setBloodPressure(rs.getString("blood_pressure"));
                studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                studentMedicalRecord.setTreatment(rs.getString("treatment"));
                studentMedicalRecords.add(studentMedicalRecord);

                LOGGER.info("Data retrieved: "+"\n"
                        +"Student ID: "+studentMedicalRecord.getStudentId()+"\n"
                        +"Medical Record ID: "+studentMedicalRecord.getMedicalRecordId()+"\n"
                        +"is active  : "+studentMedicalRecord.getMedicalRecordStatus()+"\n"
                        +"LRN  ID: "+studentMedicalRecord.getLrn()+"\n"
                        +"Name   : "+studentMedicalRecord.getFirstName()+" "+studentMedicalRecord.getLastName()+"\n"
                        +"Age    : "+studentMedicalRecord.getAge()+"\n"
                        +"Gender   : "+studentMedicalRecord.getGender()+"\n"
                        +"Symptoms : "+studentMedicalRecord.getSymptoms()+"\n"
                        +"Pulse Rate  : "+studentMedicalRecord.getPulseRate()+"\n"
                        +"Respiratory Rate  : "+studentMedicalRecord.getRespiratoryRate()+"\n"
                        +"Blood Pressure  : "+studentMedicalRecord.getBloodPressure()+"\n"
                        +"Temperature Reading  : "+studentMedicalRecord.getTemperatureReadings()+"\n"
                        +"Visit Date  : "+studentMedicalRecord.getVisitDate()+"\n"
                        +"Treatment  : "+studentMedicalRecord.getTreatment()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return studentMedicalRecords;


    }

    @Override
    public List<Student> findMedicalInformationById(Long medicalRecordId) {
        LOGGER.info("get medical record by ID started");
        List<Student> studentMedicalRecords = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(GET_MEDICAL_INFORMATION_BY_ID);
            LOGGER.info("Query in use" + stmt);
            stmt.setLong(1, medicalRecordId);
            LOGGER.info("data inserted: medicalRecordId: " + medicalRecordId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(rs.getInt("student_id"));
                studentMedicalRecord.setMedicalRecordId(rs.getLong("medical_record_id"));
                studentMedicalRecord.setLrn(rs.getString("LRN"));
                studentMedicalRecord.setMedicalRecordStatus(rs.getInt("is_active"));
                studentMedicalRecord.setFirstName(rs.getString("first_name"));
                studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                studentMedicalRecord.setLastName(rs.getString("last_name"));
                studentMedicalRecord.setAge(rs.getInt("age"));
                studentMedicalRecord.setGender(rs.getString("gender"));
                studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                studentMedicalRecord.setPulseRate(rs.getLong("pulse_rate"));
                studentMedicalRecord.setRespiratoryRate(rs.getLong("respiratory_rate"));
                studentMedicalRecord.setBloodPressure(rs.getString("blood_pressure"));
                studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                studentMedicalRecord.setTreatment(rs.getString("treatment"));
                studentMedicalRecords.add(studentMedicalRecord);

                LOGGER.info("Data retrieved: " + "\n"
                        + "Student ID: " + studentMedicalRecord.getStudentId() + "\n"
                        + "Medical Record ID: " + studentMedicalRecord.getMedicalRecordId() + "\n"
                        + "is active  : " + studentMedicalRecord.getMedicalRecordStatus() + "\n"
                        + "LRN  ID: " + studentMedicalRecord.getLrn() + "\n"
                        + "Name   : " + studentMedicalRecord.getFirstName() + " " + studentMedicalRecord.getLastName() + "\n"
                        + "Age    : " + studentMedicalRecord.getAge() + "\n"
                        + "Gender   : " + studentMedicalRecord.getGender() + "\n"
                        + "Symptoms : " + studentMedicalRecord.getSymptoms() + "\n"
                        + "Pulse Rate  : " + studentMedicalRecord.getPulseRate() + "\n"
                        + "Respiratory Rate  : " + studentMedicalRecord.getRespiratoryRate() + "\n"
                        + "Blood Pressure  : " + studentMedicalRecord.getBloodPressure() + "\n"
                        + "Temperature Reading  : " + studentMedicalRecord.getTemperatureReadings() + "\n"
                        + "Visit Date  : " + studentMedicalRecord.getVisitDate() + "\n"
                        + "Treatment  : " + studentMedicalRecord.getTreatment()
                );
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return studentMedicalRecords;
    }

    @Override
    public List<Student> findAllStudentMedicalRecords() {
        LOGGER.info("get all medical records started");
        List<Student> medicalRecords = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement(GET_ALL_STUDENTS_MEDICAL_RECORDS);
            ResultSet rs = stmt.executeQuery();
            LOGGER.info("Query in use"+ stmt);

            while (rs.next()) {
                Student studentMedicalRecord = new Student();

                studentMedicalRecord.setFirstName(rs.getString("first_name"));
                studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                studentMedicalRecord.setLastName(rs.getString("last_name"));
                studentMedicalRecord.setAge(rs.getInt("age"));
                studentMedicalRecord.setGender(rs.getString("gender"));
                studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                studentMedicalRecord.setPulseRate(rs.getLong("pulse_rate"));
                studentMedicalRecord.setRespiratoryRate(rs.getLong("respiratory_rate"));
                studentMedicalRecord.setBloodPressure(rs.getString("blood_pressure"));
                studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                studentMedicalRecord.setTreatment(rs.getString("treatment"));

                LOGGER.info("Data retrieved: "+"\n"
                        +"Name   : "+studentMedicalRecord.getFirstName()+" "+studentMedicalRecord.getLastName()+"\n"
                        +"Age    : "+studentMedicalRecord.getAge()+"\n"
                        +"Gender   : "+studentMedicalRecord.getGender()+"\n"
                        +"Symptoms : "+studentMedicalRecord.getSymptoms()+"\n"
                        +"Pulse Rate  : "+studentMedicalRecord.getPulseRate()+"\n"
                        +"Respiratory Rate  : "+studentMedicalRecord.getRespiratoryRate()+"\n"
                        +"Blood Pressure  : "+studentMedicalRecord.getBloodPressure()+"\n"
                        +"Temperature Reading  : "+studentMedicalRecord.getTemperatureReadings()+"\n"
                        +"Visit Date  : "+studentMedicalRecord.getVisitDate()+"\n"
                        +"Treatment  : "+studentMedicalRecord.getTreatment()
                );
                medicalRecords.add(studentMedicalRecord);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException("Error fetching student medical records", e);
        }

        return medicalRecords;
    }


    /**
     * Deactivates a student's medical record based on their LRN (Learner Reference Number).
     * Instead of completely removing the data, it likely updates the status
     * of the medical record in the database to indicate it's no longer active.
     *
     * A status value of 0 means the record is no longer active (deleted),
     * while a status of 1 means the record is still active and present in the system.
     */
    @Override
    public boolean deleteStudentMedicalRecord(Long medicalRecordId) {
        LOGGER.info("Delete medical records started");
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_STUDENT_MEDICAL_RECORD);
            LOGGER.info("Query in use"+preparedStatement);
            preparedStatement.setLong(1,medicalRecordId);
            LOGGER.info("data inserted: "+"Medical Record ID: "+ medicalRecordId);
            int affectedRow = preparedStatement.executeUpdate();
            return affectedRow > 0;
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }



    @Override
    public boolean updateStudentMedicalRecord(String symptoms, String temperatureReadings, Date visitDate, String treatment, Long medicalRecordId) {
        LOGGER.info("Update Student Medical Record Started with medical record id: " + medicalRecordId);
        boolean updateSuccessful = false;

        try (Connection con = ConnectionHelper.getConnection()) {
            if (symptoms != null && !symptoms.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_SYMPTOMS)) {
                    LOGGER.info("Executing update for symptoms...");
                    LOGGER.info("Query: " + stmt);
                    stmt.setString(1, symptoms);
                    stmt.setLong(2, medicalRecordId);
                    LOGGER.info("Symptoms: " + symptoms + ", medical record id: " + medicalRecordId);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Symptoms updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Symptoms " + symptoms );
                    System.out.println("SQL Exception Occurred when updating Symptom : " + e.getMessage());
                }
            }

            if (temperatureReadings != null && !temperatureReadings.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_TEMPERATURE_READINGS )) {
                    LOGGER.info("Executing update for temperature readings...");
                    LOGGER.info("Query: " + stmt);
                    stmt.setString(1, temperatureReadings);
                    stmt.setLong(2, medicalRecordId);
                    LOGGER.info("TemperatureReadings: " + temperatureReadings + ", medical record id: " + medicalRecordId);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Temperature readings updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Temperature Readings" + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Temperature Readings : " + e.getMessage());
                }
            }

            if (visitDate != null) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_VISIT_DATE)) {
                    LOGGER.info("Executing update for visit date...");
                    LOGGER.info("Query: " + stmt);
                    stmt.setTimestamp(1, new java.sql.Timestamp(visitDate.getTime()));
                    stmt.setLong(2, medicalRecordId);
                    LOGGER.info("Parameters - visitDate: " + visitDate + ", medical record id: " + medicalRecordId);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Visit date updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Visit Date "+ e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Visit Date : " + e.getMessage());}
            }

            if (treatment != null && !treatment.trim().isEmpty()) {
                try (PreparedStatement stmt = con.prepareStatement(UPDATE_STUDENT_TREATMENT)) {
                    LOGGER.info("Executing update for treatment");
                    LOGGER.info("Query: " + stmt);
                    stmt.setString(1, treatment);
                    stmt.setLong(2, medicalRecordId);
                    LOGGER.info("Parameters - treatment: " + treatment + ", medical record id: " + medicalRecordId);
                    int rows = stmt.executeUpdate();
                    updateSuccessful = rows > 0;
                } catch (SQLException e) {
                    LOGGER.info("SQL Exception Occurred on Treatment " + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Treatment : " + e.getMessage());
                }
            }

            LOGGER.info("Update Student Medical Record Completed for medical record id: " + medicalRecordId);
            return updateSuccessful;

        } catch ( SQLException e) {
            LOGGER.error("SQL Exception Occurred" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}



