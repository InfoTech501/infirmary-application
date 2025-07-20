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

    /**
     * Retrieve all student health profile from the database.
     * This method queries the database to fetch all student health profile records.
     * @return A list of all {@code studentList} objects in the database.
     */
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

            while (resultSet.next()) {
                studentList.add(setStudentHealthProfile(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Sql exception occurred {}",e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.debug("Student database is empty.");
        return studentList;
    }


    /**
     * Retrieve a student by their LRN.
     * This method fetches a specific student health profile using their unique LRN.
     * @param LRN The unique learner reference number to search for.
     * @return The {@code studentListProfile} object matching the provided LRN.
     */
    @Override
    public List<Student> findStudentHealthProfileByLrn(Long LRN) {
        List<Student> studentListProfile = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {
            QueryConstants queryConstants = new QueryConstants();
            String query = queryConstants.selectStudentHealthProfileByLrn();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setLong(1,LRN);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                studentListProfile.add(setStudentHealthProfileByLrn(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LOGGER.debug("Cannot find student health profile using lrn");
        return studentListProfile;
    }

    private Student setStudentHealthProfile(ResultSet resultSet) {
        try {
            Student studentMedicalRecord = new Student();
            studentMedicalRecord.setLrn(resultSet.getLong("LRN"));
            studentMedicalRecord.setMedicalRecordStatus(resultSet.getInt("is_active"));
            studentMedicalRecord.setFirstName(resultSet.getString("first_name"));
            studentMedicalRecord.setMiddleName(resultSet.getString("middle_name"));
            studentMedicalRecord.setLastName(resultSet.getString("last_name"));
            studentMedicalRecord.setSection(resultSet.getString("section"));
            studentMedicalRecord.setGradeLevel(resultSet.getString("grade_level"));
            studentMedicalRecord.setGender(resultSet.getString("gender"));
            studentMedicalRecord.setContactNumber(resultSet.getString("contact_number"));
            studentMedicalRecord.setAddress(resultSet.getString("address"));
            studentMedicalRecord.setBirthdate(resultSet.getDate("birthdate"));
            studentMedicalRecord.setAge(resultSet.getInt("age"));
            studentMedicalRecord.setStudentAdviser(resultSet.getString("adviser_first_name"));

            LOGGER.info("Retrieved Data: IS_ACTIVE: {}\nFirst Name: LRN: {}\nFirst Name: {}\nMiddle Name: {}\n Last Name: {}\nSection: {}\n Grade Level: {}\n Gender: {}\n Contact Number: {}\n Address: {}\n Birthdate: {}\n Age: {}\n Adviser: {}",
                    studentMedicalRecord.getMedicalRecordStatus(),
                    studentMedicalRecord.getLrn(),
                    studentMedicalRecord.getFirstName(),
                    studentMedicalRecord.getMiddleName(),
                    studentMedicalRecord.getLastName(),
                    studentMedicalRecord.getSection(),
                    studentMedicalRecord.getGradeLevel(),
                    studentMedicalRecord.getGender(),
                    studentMedicalRecord.getContactNumber(),
                    studentMedicalRecord.getAddress(),
                    studentMedicalRecord.getBirthdate(),
                    studentMedicalRecord.getAge(),
                    studentMedicalRecord.getStudentAdviser()
            );

            return studentMedicalRecord;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred.{}", e.getMessage());
        }
        LOGGER.debug("set student failed");
        return setStudentHealthProfile(resultSet);
    }

    private Student setStudentHealthProfileByLrn(ResultSet resultSet) {
        try {
            Student studentMedicalRecord = new Student();
            studentMedicalRecord.setContactNumber(resultSet.getString("contact_number"));
            studentMedicalRecord.setMedicalRecordStatus(resultSet.getInt("is_active"));
            studentMedicalRecord.setEmail(resultSet.getString("email"));
            studentMedicalRecord.setAddress(resultSet.getString("address"));
            studentMedicalRecord.setFirstName(resultSet.getString("first_name"));
            studentMedicalRecord.setMiddleName(resultSet.getString("middle_name"));
            studentMedicalRecord.setLastName(resultSet.getString("last_name"));
            studentMedicalRecord.setSymptoms(resultSet.getString("symptoms"));
            studentMedicalRecord.setPulseRate(resultSet.getLong("pulse_rate"));
            studentMedicalRecord.setRespiratoryRate(resultSet.getLong("respiratory_rate"));
            studentMedicalRecord.setBloodPressure(resultSet.getString("blood_pressure"));
            studentMedicalRecord.setTemperatureReadings(resultSet.getString("temperature_readings"));
            studentMedicalRecord.setTreatment(resultSet.getString("treatment"));
            studentMedicalRecord.setVisitDate(resultSet.getTimestamp("visit_date"));
            studentMedicalRecord.setNurseInChargeFirstName(resultSet.getString("nurse_first_name"));
            studentMedicalRecord.setNurseInChargeLastName(resultSet.getString("nurse_last_name"));

            LOGGER.info("Retrieved Data: IS_ACTIVE: {}\nFirst Name: LRN: {}\nFirst Name: {}\nMiddle Name: {}\n Last Name: {}\nSection: {}\n Grade Level: {}\n Gender: {}\n Contact Number: {}\n Address: {}\n Birthdate: {}\n Age: {}\n Adviser: {}",
                    studentMedicalRecord.getMedicalRecordStatus(),
                    studentMedicalRecord.getLrn(),
                    studentMedicalRecord.getFirstName(),
                    studentMedicalRecord.getMiddleName(),
                    studentMedicalRecord.getLastName(),
                    studentMedicalRecord.getSection(),
                    studentMedicalRecord.getGradeLevel(),
                    studentMedicalRecord.getGender(),
                    studentMedicalRecord.getContactNumber(),
                    studentMedicalRecord.getAddress(),
                    studentMedicalRecord.getBirthdate(),
                    studentMedicalRecord.getAge(),
                    studentMedicalRecord.getStudentAdviser()
            );
            return studentMedicalRecord;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred while getting student health profile by lrn.{}", e.getMessage());
        }
        LOGGER.debug("set student health profile by lrn failed");
        return setStudentHealthProfile(resultSet);
    }
}
