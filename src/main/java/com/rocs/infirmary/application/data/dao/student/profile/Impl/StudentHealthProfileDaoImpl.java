package com.rocs.infirmary.application.data.dao.student.profile.Impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants;
import com.rocs.infirmary.application.data.model.person.student.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentHealthProfileDaoImpl implements StudentHealthProfileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileDaoImpl.class);
    @Override
    public List<Student> findAllStudentHealthProfile() {
        List<Student> studentList = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            LOGGER.info("Student Health Profile Dao started");
            QueryConstants queryConstants = new QueryConstants();
            String query = queryConstants.selectStudentHealthProfile();
            LOGGER.info("used query:{}",query);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Student studentMedicalRecord = new Student();
                studentMedicalRecord.setLrn(resultSet.getLong("LRN"));
                studentMedicalRecord.setFirstName(resultSet.getString("first_name"));
                studentMedicalRecord.setMiddleName(resultSet.getString("middle_name"));
                studentMedicalRecord.setLastName(resultSet.getString("last_name"));
                studentMedicalRecord.setSection(resultSet.getString("section"));
                studentMedicalRecord.setGradeLevel(resultSet.getString("grade_level"));
                studentMedicalRecord.setStudentAdviser(resultSet.getString("adviser_first_name"));

                LOGGER.info("Retrieved Data: LRN: {}\nFirst Name: {}\nMiddle Name: {}\n Last Name: {}\nSection: {}\n Grade Level: {}\n Adviser: {}",
                        studentMedicalRecord.getLrn(),
                        studentMedicalRecord.getFirstName(),
                        studentMedicalRecord.getMiddleName(),
                        studentMedicalRecord.getLastName(),
                        studentMedicalRecord.getSection(),
                        studentMedicalRecord.getGradeLevel(),
                        studentMedicalRecord.getStudentAdviser()
                );

                studentList.add(studentMedicalRecord);
            }
            LOGGER.info("successfully retrieved profiles: {}",studentList.size());
        } catch (SQLException e) {
            LOGGER.error("Sql exception occurred {}",e.getMessage());
            throw new RuntimeException(e);
        }

        return studentList;
    }

    @Override
    public List<Student> findStudentHealthProfileByLrn(Long LRN) {
        List<Student> studentListProfile = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {
            QueryConstants queryConstants = new QueryConstants();
            String query = queryConstants.selectStudentHealthProfileByLrn();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setLong(1,LRN);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Student studentMedicalRecord = new Student();
                studentMedicalRecord.setContactNumber(resultSet.getInt("contact_number"));
                studentMedicalRecord.setEmail(resultSet.getString("email"));
                studentMedicalRecord.setAddress(resultSet.getString("address"));
                studentMedicalRecord.setFirstName(resultSet.getString("first_name"));
                studentMedicalRecord.setMiddleName(resultSet.getString("middle_name"));
                studentMedicalRecord.setLastName(resultSet.getString("last_name"));
                studentMedicalRecord.setSymptoms(resultSet.getString("symptoms"));
                studentMedicalRecord.setTemperatureReadings(resultSet.getString("temperature_readings"));
                studentMedicalRecord.setTreatment(resultSet.getString("treatment"));
                studentMedicalRecord.setVisitDate(resultSet.getTimestamp("visit_date"));
                studentMedicalRecord.setNurseInCharge(resultSet.getString("nurse_in_charge"));
                studentListProfile.add(studentMedicalRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentListProfile;
    }
}
