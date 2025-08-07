package com.rocs.infirmary.application.data.dao.student.profile.Impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.student.profile.StudentHealthProfileDao;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants;
import com.rocs.infirmary.application.data.model.person.student.Student;
import com.rocs.infirmary.application.data.model.medicalrecord.MedicalRecord;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.rocs.infirmary.application.data.dao.utils.queryconstants.student.QueryConstants.*;

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
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENT_HEALTH_PROFILE_QUERY);
            LOGGER.info("used query:{}",stmt);
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
    public List<MedicalRecord> findStudentHealthProfileByLrn(String LRN) {
        List<MedicalRecord> studentListProfile = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENT_HEALTH_PROFILE_BY_LRN);
            stmt.setString(1,LRN);
            LOGGER.info("findStudentHealthProfileByLrn used query:{}",stmt);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                MedicalRecord medicalRecord = new MedicalRecord();

                medicalRecord.setMedicalRecordId(resultSet.getLong("id"));
                medicalRecord.setFirstName(resultSet.getString("first_name"));
                medicalRecord.setMiddleName(resultSet.getString("middle_name"));
                medicalRecord.setLastName(resultSet.getString("last_name"));
                medicalRecord.setContactNumber(resultSet.getString("contact_number"));
                medicalRecord.setEmail(resultSet.getString("email"));
                medicalRecord.setAddress(resultSet.getString("address"));
                medicalRecord.setSymptoms(resultSet.getString("symptoms"));
                medicalRecord.setTemperatureReadings(resultSet.getString("temperature_readings"));
                medicalRecord.setBloodPressure(resultSet.getString("blood_pressure"));
                medicalRecord.setPulseRate(resultSet.getInt("pulse_rate"));
                medicalRecord.setTreatment(resultSet.getString("treatment"));
                medicalRecord.setRespiratoryRate(resultSet.getInt("respiratory_rate"));
                medicalRecord.setVisitDate(resultSet.getTimestamp("visit_date"));
                medicalRecord.setNurseInCharge(resultSet.getString("nurse_first_name"));
                studentListProfile.add(medicalRecord);

                LOGGER.info("Retrieved medical record: {}", medicalRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentListProfile;
    }

    private MedicalRecord setStudentHealthProfile(ResultSet resultSet) {
        try {
            MedicalRecord medicalRecord = new MedicalRecord();
            Employee employee = new Employee();
            medicalRecord.setLrn(resultSet.getString("LRN"));
            medicalRecord.setFirstName(resultSet.getString("first_name"));
            medicalRecord.setMiddleName(resultSet.getString("middle_name"));
            medicalRecord.setLastName(resultSet.getString("last_name"));
            medicalRecord.setSection(resultSet.getString("section"));
            medicalRecord.setGradeLevel(resultSet.getString("grade_level"));
            medicalRecord.setGender(resultSet.getString("gender"));
            medicalRecord.setContactNumber(resultSet.getString("contact_number"));
            medicalRecord.setAddress(resultSet.getString("address"));
            medicalRecord.setBirthdate(resultSet.getDate("birthdate"));
            medicalRecord.setAge(resultSet.getInt("age"));
            employee.setAdviser(resultSet.getString("adviser_last_name"));

            LOGGER.info(
                    "Retrieved Data: \n" +
                            "LRN: {}\n" +
                            "First Name: {}\n" +
                            "Middle Name: {}\n" +
                            "Last Name: {}\n" +
                            "Section: {}\n" +
                            "Grade Level: {}\n" +
                            "Gender: {}\n" +
                            "Contact Number: {}\n" +
                            "Address: {}\n" +
                            "Birthdate: {}\n" +
                            "Age: {}\n" +
                            "Adviser: {}",
                    medicalRecord.getLrn(),
                    medicalRecord.getFirstName(),
                    medicalRecord.getMiddleName(),
                    medicalRecord.getLastName(),
                    medicalRecord.getSection(),
                    medicalRecord.getGradeLevel(),
                    medicalRecord.getGender(),
                    medicalRecord.getContactNumber(),
                    medicalRecord.getAddress(),
                    medicalRecord.getBirthdate(),
                    medicalRecord.getAge(),
                    employee.getAdviser()
            );
            return medicalRecord;
        } catch (Exception e) {
            LOGGER.error("An SQL Exception occurred.{}", e.getMessage());
        }
        LOGGER.debug("set student failed");
        return setStudentHealthProfile(resultSet);
    }

}
