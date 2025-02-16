package com.rocs.medical.records.application.data.dao.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.MedicalRecordsDao;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MedicalRecordsDaoImpl implements MedicalRecordsDao {

    @Override
    public boolean patient(MedicalRecords medicalRecords) {

        try (Connection con = ConnectionHelper.getConnection()) {
            String sql = "INSERT INTO medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, visit_date, treatment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setInt(1, medicalRecords.getStudentId());
                stmt.setInt(2, medicalRecords.getAilmentId());
                stmt.setInt(3, medicalRecords.getMedHistoryId());
                stmt.setInt(4, medicalRecords.getNurseInChargeId());
                stmt.setString(5, medicalRecords.getSymptoms());
                stmt.setString(6, medicalRecords.getTemperatureReadings());
                stmt.setTimestamp(7, Timestamp.valueOf(medicalRecords.getVisitDate()));
                stmt.setString(8, medicalRecords.getTreatment());

                stmt.executeUpdate();

                return true;
            } catch (SQLException e) {
                System.err.println("An SQL Exception occurred: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean saveOrUpdateMedicalRecord(MedicalRecords MedicalRecords) {
        return false;
    }
}