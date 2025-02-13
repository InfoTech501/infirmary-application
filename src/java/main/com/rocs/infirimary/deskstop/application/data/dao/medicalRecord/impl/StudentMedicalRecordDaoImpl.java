
package main.com.rocs.infirimary.deskstop.application.data.dao.medicalRecord.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.com.rocs.infirimary.deskstop.application.data.dao.medicalRecord.StudentMedicalRecordDao;
import main.com.rocs.infirimary.deskstop.application.data.dbConnector.ConnectorHelper;
import main.com.rocs.infirimary.deskstop.application.model.medicalrecord.StudentMedicalRecord;
import main.com.rocs.infirimary.deskstop.application.model.studentdetails.StudentDetails;


public class StudentMedicalRecordDaoImpl implements StudentMedicalRecordDao {


    public StudentMedicalRecord SearchMedInfoUsingLRN(long LRN) {

       StudentMedicalRecord studentMedicalRecord = null;

        try {
            try (Connection con = ConnectorHelper.getConnection()) {

                String sql = "SELECT " +
                        "s.id AS student_id, " +
                        "s.LRN, " +
                        "p.first_name, " +
                        "p.middle_name, " +
                        "p.last_name, " +
                        "p.age, " +
                        "p.gender, " +
                        "p.email, " +
                        "p.address, " +
                        "p.contact_number, " +
                        "mr.id AS medical_record_id, " +
                        "mr.ailment_id, " +
                        "mr.med_history_id, " +
                        "mr.nurse_in_charge_id, " +
                        "mr.symptoms, " +
                        "mr.temperature_readings, " +
                        "TO_CHAR(mr.visit_date, 'YYYY-MM-DD HH24:MI:SS') AS visit_date, " +
                        "mr.treatment " +
                        "FROM student s " +
                        "JOIN person p ON s.person_id = p.id " +
                        "LEFT JOIN medical_record mr ON s.id = mr.student_id " +
                        "WHERE s.LRN = ?";



                PreparedStatement stmt = con.prepareStatement(sql);


                stmt.setLong(1, LRN);
                ResultSet rs = stmt.executeQuery();


                while (rs.next()) {

                    studentMedicalRecord = new StudentMedicalRecord();
                    StudentDetails studentDetails = new StudentDetails();

                    studentMedicalRecord.setStudentID(rs.getInt("student_id"));
                    studentMedicalRecord.setLRN(rs.getLong("LRN"));
                    studentDetails.setFirstName(rs.getString("first_name"));
                    studentDetails.setMiddleName(rs.getString("middle_name"));
                    studentDetails.setLastName(rs.getString("last_name"));
                    studentDetails.setAge(rs.getInt("age"));
                    studentDetails.setGender(rs.getString("gender"));
                    studentDetails.setEmail(rs.getString("email"));
                    studentDetails.setAddress(rs.getString("address"));
                    studentDetails.setContactNumber(rs.getString("contact_number"));


                    studentMedicalRecord.setID(rs.getInt("medical_record_id"));
                    studentMedicalRecord.setAilmentID(rs.getInt("ailment_id"));
                    studentMedicalRecord.setMedHistoryID(rs.getInt("med_history_id"));
                    studentMedicalRecord.setNurseInChargeID(rs.getInt("nurse_in_charge_id"));
                    studentMedicalRecord.setSymptoms(rs.getString("symptoms"));
                    studentMedicalRecord.setTemperatureReadings(rs.getString("temperature_readings"));
                    studentMedicalRecord.setVisitDate(rs.getString("visit_date"));
                    studentMedicalRecord.setTreatment(rs.getString("treatment"));

                    studentMedicalRecord.setStudentDetails(studentDetails);

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
      return  studentMedicalRecord;
    }
}



