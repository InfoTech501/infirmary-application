package com.rocs.medical.records.application.data.dao.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.MedicalRecordsDao;
import com.rocs.medical.records.application.model.MedicalRecords.MedicalRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class medicalrecordsDaoImpl implements MedicalRecordsDao {

    @Override
    public boolean patient(MedicalRecords medicalRecords) {

        try (Connection con = ConnectionHelper.getConnection()) {
            String sql = "INSERT INTO patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, String.valueOf(medicalRecords.getPatientId()));
                stmt.setString(2, medicalRecords.getFirstName());
                stmt.setString(3, medicalRecords.getMiddleName());
                stmt.setString(4, medicalRecords.getLastName());
                stmt.setString(5, medicalRecords.getSymptoms());
                stmt.setString(6, medicalRecords.getRemarks());
                stmt.setDouble(7, medicalRecords.getTemperature());
                stmt.setDate(8, java.sql.Date.valueOf(medicalRecords.getVisitDate()));
                stmt.setTime(9, java.sql.Time.valueOf(medicalRecords.getTimeIn()));
                stmt.setTime(10, java.sql.Time.valueOf(medicalRecords.getTimeOut()));
                stmt.setString(11, medicalRecords.getMedicationAdministered());
                stmt.setString(12, medicalRecords.getNurseInCharge());

                stmt.executeUpdate();

                return true;
            } catch (SQLException e) {
                System.out.println("An SQL Exception occurred." + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
