package com.rocs.medical.records.application.data.dao.createMedicalRecords.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.createMedicalRecords.createMedicalRecordsDao;
import com.rocs.medical.records.application.model.createmedicalrecords.MedicalRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class createMedicalRecordsDaoImpl implements createMedicalRecordsDao {

    @Override
    public boolean createMedicalRecord(MedicalRecords medicalRecords) {

        try (Connection con = ConnectionHelper.getConnection()) {
            String sql = "INSERT INTO medical_record (first_name, middle_name, last_name, symptoms, visit_date_time, temperature_readings, treatment, nurse_in_charge) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, medicalRecords.getFirstName());
                stmt.setString(2, medicalRecords.getMiddleName());
                stmt.setString(3, medicalRecords.getLastName());
                stmt.setString(4, medicalRecords.getSymptoms());
                stmt.setTimestamp(5, Timestamp.valueOf(medicalRecords.getVisitDateTime()));
                stmt.setDouble(6, medicalRecords.getTemperatureReadings());
                stmt.setString(7, medicalRecords.getTreatment());
                stmt.setString(8, medicalRecords.getNurseInCharge());

                stmt.executeUpdate();

                return true;
            } catch (SQLException e) {
                System.err.println("An SQL Exception occurred: " + e.getMessage());
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean addMedicalRecord(MedicalRecords MedicalRecords) {
        return true;
    }
}