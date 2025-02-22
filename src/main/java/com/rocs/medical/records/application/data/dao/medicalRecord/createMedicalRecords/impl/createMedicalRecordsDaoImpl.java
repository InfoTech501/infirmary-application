package com.rocs.medical.records.application.data.dao.medicalRecord.createMedicalRecords.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicalRecord.createMedicalRecords.createMedicalRecordsDao;
import com.rocs.medical.records.application.model.medicalrecord.createmedicalrecords.MedicalRecords;

import java.sql.*;
import java.time.LocalDateTime;

public class createMedicalRecordsDaoImpl implements createMedicalRecordsDao {

    @Override
    public boolean createMedicalRecord(MedicalRecords medicalRecords) {
        try (Connection con = ConnectionHelper.getConnection()) {
            int studentId = getStudentIdByName(con, medicalRecords.getFirstName(), medicalRecords.getMiddleName(), medicalRecords.getLastName());
            if (studentId == -1) {
                return false;
            }

            String sql = "INSERT INTO medical_record (student_id, symptoms, visit_datetime, temperature, treatment, nurse_in_charge_id, ailment_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setInt(1, studentId);
                stmt.setString(2, medicalRecords.getSymptoms());

                if (medicalRecords.getVisitDateTime() != null) {
                    try {
                        LocalDateTime ldt = medicalRecords.getVisitDateTime();
                        stmt.setTimestamp(3, Timestamp.valueOf(ldt));
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                } else {
                    stmt.setNull(3, Types.TIMESTAMP);
                }

                stmt.setDouble(4, medicalRecords.getTemperatureReadings());
                stmt.setString(5, medicalRecords.getTreatment());
                stmt.setInt(6, medicalRecords.getNurseInChargeId());

                Integer ailmentId = medicalRecords.getAilmentId();
                if (ailmentId == null) {
                    throw new IllegalArgumentException("Ailment ID cannot be null.");
                } else {
                    stmt.setInt(7, ailmentId);
                }

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            } catch (SQLException e) {
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
}