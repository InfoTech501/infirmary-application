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
import java.util.List;


/**
 * The StudentMedicalRecordDaoImpl class implements the StudentMedicalRecordDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving, adding, updating, and deleting student medical records.
 */
public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    public Student getMedicalInformationByLRN(long LRN) {
        LOGGER.info("get medical record started");

       Student studentMedicalRecord = null;
        try (Connection con = ConnectionHelper.getConnection()) {

            QueryConstants queryConstants  = new QueryConstants();

            String sql = queryConstants.getAllMedicalInformationByLRN();

            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use {}",sql);


            stmt.setLong(1, LRN);
            LOGGER.info("data inserted: \nLRN: {}",LRN);
            ResultSet rs = stmt.executeQuery();


            if(rs.next()) {
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
                LOGGER.info("Data retrieved: \nStudent ID: {}\nLRN  ID: {}\nName   : {} {}\nAge    : {}\nGender   : {}\nSymptoms : {}\nTemperature Reading  : {}\nVisit Date  : {}\nTreatment  : {}", studentMedicalRecord.getStudentId(), studentMedicalRecord.getLrn(), studentMedicalRecord.getFirstName(), studentMedicalRecord.getLastName(), studentMedicalRecord.getAge(), studentMedicalRecord.getGender(), studentMedicalRecord.getSymptoms(), studentMedicalRecord.getTemperatureReadings(), studentMedicalRecord.getVisitDate(), studentMedicalRecord.getTreatment());
            }
        }catch (SQLException e) {
            LOGGER.error("SQLException Occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return  studentMedicalRecord;


    }
    @Override
    public List<Student> getAllStudentMedicalRecords() {
        LOGGER.info("get all medical records started");
        List<Student> medicalRecords = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {

            QueryConstants queryConstants  = new QueryConstants();

            String sql = queryConstants.getAllStudentMedicalRecords();

            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use{}", sql);
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
                LOGGER.info("Data retrieved: \nName   : {} {}\nAge    : {}\nGender   : {}\nSymptoms : {}\nTemperature Reading  : {}\nVisit Date  : {}\nTreatment  : {}", studentMedicalRecord.getFirstName(), studentMedicalRecord.getLastName(), studentMedicalRecord.getAge(), studentMedicalRecord.getGender(), studentMedicalRecord.getSymptoms(), studentMedicalRecord.getTemperatureReadings(), studentMedicalRecord.getVisitDate(), studentMedicalRecord.getTreatment());
                medicalRecords.add(studentMedicalRecord);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: {}", e.getMessage());
            throw new RuntimeException("Error fetching student medical records", e);
        }

        return medicalRecords;
    }
}



