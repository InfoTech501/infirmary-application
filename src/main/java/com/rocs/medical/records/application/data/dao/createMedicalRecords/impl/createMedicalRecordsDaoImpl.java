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

            String sql = "INSERT INTO medical_record (student_id, symptoms, visit_datetime, temperature, treatment, nurse_in_charge) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setInt(1, studentId);
                stmt.setString(2, medicalRecords.getSymptoms());

                if (medicalRecords.getVisitDateTime() != null) {
                    try {
                        LocalDateTime ldt = medicalRecords.getVisitDateTime();
                        stmt.setTimestamp(3, Timestamp.valueOf(ldt));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Date/Time parsing error: " + e.getMessage());
                        return false;
                    }
                } else {
                    stmt.setNull(3, Types.TIMESTAMP);
                }

                stmt.setDouble(4, medicalRecords.getTemperatureReadings());
                stmt.setString(5, medicalRecords.getTreatment());
                stmt.setString(6, medicalRecords.getNurseInCharge());

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
        String sql = "SELECT id FROM person WHERE first_name = ? AND middle_name = ? AND last_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, middleName);
            pstmt.setString(3, lastName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
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
                    "mr.symptoms, " +
                    "mr.visit_datetime, " +
                    "mr.temperature, " +
                    "mr.treatment, " +
                    "p.nurse_in_charge " +
                    "FROM student s " +
                    "JOIN person p ON s.person_id = p.id " +
                    "LEFT JOIN medical_record mr ON s.id = mr.student_id " +
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
                        medicalRecords.setSymptoms(rs.getString("symptoms"));
                        medicalRecords.setVisitDateTime(rs.getTimestamp("visit_datetime") != null ? rs.getTimestamp("visit_datetime").toLocalDateTime() : null);
                        medicalRecords.setTemperatureReadings(rs.getDouble("temperature"));
                        medicalRecords.setTreatment(rs.getString("treatment"));
                        medicalRecords.setNurseInCharge(rs.getString("nurse_in_charge"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicalRecords;
    }
}