package com.rocs.infirmary.application.data.dao.student.record.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants;
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

            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.getAllMedicalInformationByLRN();

            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use"+sql);

            stmt.setLong(1, LRN);
            LOGGER.info("data inserted: "+"LRN: "+LRN);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(rs.getInt("student_id"));
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

                LOGGER.info("Data retrieved: "+"\n"
                        +"Student ID: "+studentMedicalRecord.getStudentId()+"\n"
                        +"LRN  ID: "+studentMedicalRecord.getLrn()+"\n"
                        +"Name   : "+studentMedicalRecord.getFirstName()+" "+studentMedicalRecord.getLastName()+"\n"
                        +"Age    : "+studentMedicalRecord.getAge()+"\n"
                        +"Gender   : "+studentMedicalRecord.getGender()+"\n"
                        +"Symptoms : "+studentMedicalRecord.getSymptoms()+"\n"
                        +"Temperature Reading  : "+studentMedicalRecord.getTemperatureReadings()+"\n"
                        +"Visit Date  : "+studentMedicalRecord.getVisitDate()+"\n"
                        +"Treatment  : "+studentMedicalRecord.getTreatment()
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
        LOGGER.info("get all medical records started");
        List<Student> medicalRecords = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {

            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.getAllStudentMedicalRecords();

            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use"+sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student studentMedicalRecord = new Student();

                studentMedicalRecord.setFirstName(rs.getString("first_name"));
                studentMedicalRecord.setMiddleName(rs.getString("middle_name"));
                studentMedicalRecord.setLastName(rs.getString("last_name"));
                studentMedicalRecord.setAge(rs.getInt("age"));
                studentMedicalRecord.setGender(rs.getString("gender"));
                studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                studentMedicalRecord.setVisitDate(rs.getDate("visit_date"));
                studentMedicalRecord.setTreatment(rs.getString("treatment"));

                LOGGER.info("Data retrieved: "+"\n"
                        +"Name   : "+studentMedicalRecord.getFirstName()+" "+studentMedicalRecord.getLastName()+"\n"
                        +"Age    : "+studentMedicalRecord.getAge()+"\n"
                        +"Gender   : "+studentMedicalRecord.getGender()+"\n"
                        +"Symptoms : "+studentMedicalRecord.getSymptoms()+"\n"
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
    public boolean deleteStudentMedicalRecord(long LRN) {
        LOGGER.info("Delete medical records started");
        Student studentMedicalRecord = getStudent(LRN);

        try (Connection con = ConnectionHelper.getConnection()) {

            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.deleteStudentMedicalRecord();

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            LOGGER.info("Query in use"+sql);
            preparedStatement.setInt(1,studentMedicalRecord.getStudentId());
            LOGGER.info("data inserted: "+"LRN: "+LRN);
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
        QueryConstants queryConstants = new QueryConstants();
        boolean updateSuccessful = false;

        try (Connection con = ConnectionHelper.getConnection()) {


            if (symptoms != null && !symptoms.trim().isEmpty()) {
                String updateSymptomQuery = queryConstants.updateStudentSymptoms();
                try (PreparedStatement stmt = con.prepareStatement(updateSymptomQuery)) {
                    LOGGER.info("Executing update for symptoms...");
                    LOGGER.info("Query: " + updateSymptomQuery);
                    stmt.setString(1, symptoms);
                    stmt.setLong(2, LRN);
                    LOGGER.info("Symptoms: " + symptoms + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Symptoms updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Symptoms " + symptoms );
                    System.out.println("SQL Exception Occurred when updating Symptom : " + e.getMessage());
                }
            }

            if (temperatureReadings != null && !temperatureReadings.trim().isEmpty()) {
                String updateTemperatureReadingsQuery = queryConstants.updateStudentTemperatureReadings();
                try (PreparedStatement stmt = con.prepareStatement(updateTemperatureReadingsQuery)) {
                    LOGGER.info("Executing update for temperature readings...");
                    LOGGER.info("Query: " + updateTemperatureReadingsQuery);
                    stmt.setString(1, temperatureReadings);
                    stmt.setLong(2, LRN);
                    LOGGER.info("TemperatureReadings: " + temperatureReadings + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Temperature readings updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Temperature Readings" + e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Temperature Readings : " + e.getMessage());
                }
            }

            if (visitDate != null) {
                String updateVisitDateQuery = queryConstants.updateStudentVisitDate();
                try (PreparedStatement stmt = con.prepareStatement(updateVisitDateQuery)) {
                    LOGGER.info("Executing update for visit date...");
                    LOGGER.info("Query: " + updateVisitDateQuery);
                    stmt.setTimestamp(1, new java.sql.Timestamp(visitDate.getTime()));
                    stmt.setLong(2, LRN);
                    LOGGER.info("Parameters - visitDate: " + visitDate + ", LRN: " + LRN);
                    int rows = stmt.executeUpdate();
                    LOGGER.info("Visit date updated. Rows affected: " + rows);
                    updateSuccessful = rows > 0;
                }catch (SQLException e ) {
                    LOGGER.info("SQL Exception Occurred on Visit Date "+ e.getMessage());
                    System.out.println("SQL Exception Occurred when Updating Visit Date : " + e.getMessage());}
            }

            if (treatment != null && !treatment.trim().isEmpty()) {
                String updateTreatmentQuery = queryConstants.updateStudentTreatment();
                try (PreparedStatement stmt = con.prepareStatement(updateTreatmentQuery)) {
                    LOGGER.info("Executing update for treatment");
                    LOGGER.info("Query: " + updateTreatmentQuery);
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

        } catch ( SQLException e) {
            LOGGER.error("SQL Exception Occurred" + e.getMessage());
            throw new RuntimeException(e);
        }
    }



    private static Student getStudent(long LRN) {
        Student studentMedicalRecord = null;
        LOGGER.info("Retrieving Student information");
        try (Connection con = ConnectionHelper.getConnection()) {

            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.getAllMedicalInformationByLRN();

            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use"+sql);
            stmt.setLong(1, LRN);
            LOGGER.info("data inserted: "+"LRN: "+LRN);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                studentMedicalRecord = new Student();
                studentMedicalRecord.setStudentId(resultSet.getInt("student_id"));
                LOGGER.info("Data retrieved: "+"\n"
                        +"Student ID   : "+studentMedicalRecord.getStudentId()+"\n"
                );
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return studentMedicalRecord;
    }

}



