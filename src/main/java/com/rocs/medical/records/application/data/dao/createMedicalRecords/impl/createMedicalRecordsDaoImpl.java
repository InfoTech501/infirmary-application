package com.rocs.medical.records.application.data.dao.createMedicalRecords.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.createMedicalRecordsDao;
import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

import java.sql.*;
import java.time.LocalDateTime;

public class createMedicalRecordsDaoImpl implements createMedicalRecordsDao {

    @Override
    public boolean createMedicalRecord(MedicalRecords medicalRecords) {
        try (Connection con = ConnectionHelper.getConnection()) {
            int studentId = getStudentIdByName(con, medicalRecords.getFirstName(), medicalRecords.getMiddleName(), medicalRecords.getLastName());
            if (studentId == -1) {
                System.err.println("Student not found.");
                return false;
            }

            int nurseId = getNurseIdByName(con, medicalRecords.getNurseInCharge());
            if (nurseId == -1) {
                System.err.println("Nurse not found.");
                return false;
            }

            String sql = "INSERT INTO medical_record (student_id, nurse_id, symptoms, visit_datetime, temperature, treatment, ailment_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setInt(1, studentId);
                stmt.setInt(2, nurseId);
                stmt.setString(3, medicalRecords.getSymptoms());

                if (medicalRecords.getVisitDateTime() != null) {
                    try {
                        LocalDateTime ldt = medicalRecords.getVisitDateTime();
                        stmt.setTimestamp(4, Timestamp.valueOf(ldt));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Date/Time parsing error: " + e.getMessage());
                        return false;
                    }
                } else {
                    stmt.setNull(4, Types.TIMESTAMP);
                }

                stmt.setDouble(5, medicalRecords.getTemperatureReadings());
                stmt.setString(6, medicalRecords.getTreatment());
                stmt.setNull(7, Types.INTEGER);

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            } catch (SQLException e) {
                System.err.println("An SQL Exception occurred: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getStudentIdByName(Connection con, String firstName, String middleName, String lastName) throws SQLException {
        String sql = "SELECT student_id FROM student WHERE first_name = ? AND middle_name = ? AND last_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, middleName);
            pstmt.setString(3, lastName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("student_id");
                } else {
                    return -1;
                }
            }
        }
    }

    private int getNurseIdByName(Connection con, String nurseInChargeName) throws SQLException {
        String sql = "SELECT nurse_id FROM nurse WHERE nurse_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nurseInChargeName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("nurse_id");
                } else {
                    return -1;
                }
            }
        }
    }

    @Override
    public MedicalRecords findMedicalInformationByLRN(long LRN) {
        MedicalRecords medicalRecords = null;

        try (Connection con = ConnectionHelper.getConnection()) {
            String sql = "SELECT " +
                    "s.id AS student_id, " +
                    "p.first_name, " +
                    "p.middle_name, " +
                    "p.last_name, " +
                    "p.age, " +
                    "p.gender, " +
                    "mr.symptoms, " +
                    "mr.visit_datetime, " +
                    "mr.temperature, " +
                    "mr.treatment, " +
                    "n.nurse_name " +
                    "FROM student s " +
                    "JOIN person p ON s.person_id = p.id " +
                    "LEFT JOIN medical_record mr ON s.id = mr.student_id " +
                    "LEFT JOIN nurse n ON mr.nurse_id = n.nurse_id " +
                    "WHERE s.LRN = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setLong(1, LRN);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        medicalRecords = new MedicalRecords();
                        medicalRecords.setId(rs.getInt("student_id"));
                        medicalRecords.setFirstName(rs.getString("first_name"));
                        medicalRecords.setMiddleName(rs.getString("middle_name"));
                        medicalRecords.setLastName(rs.getString("last_name"));
                        medicalRecords.setAge(rs.getInt("age"));
                        medicalRecords.setGender(rs.getString("gender"));
                        medicalRecords.setSymptoms(rs.getString("symptoms"));
                        medicalRecords.setVisitDateTime(rs.getTimestamp("visit_datetime") != null ? rs.getTimestamp("visit_datetime").toLocalDateTime() : null);
                        medicalRecords.setTemperatureReadings(rs.getDouble("temperature"));
                        medicalRecords.setTreatment(rs.getString("treatment"));
                        medicalRecords.setNurseInCharge(rs.getString("nurse_name"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicalRecords;
    }

}