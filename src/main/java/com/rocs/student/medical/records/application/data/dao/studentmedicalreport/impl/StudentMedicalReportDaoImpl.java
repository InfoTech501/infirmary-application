package com.rocs.student.medical.records.application.data.dao.studentmedicalreport.impl;

import com.rocs.student.medical.records.application.data.dao.studentmedicalreport.StudentMedicalReportDao;
import com.rocs.student.medical.records.application.model.studentmedicalreport.StudentMedicalReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentMedicalReportDaoImpl implements StudentMedicalReportDao {

    static String URL = "jdbc:oracle:thin:@localhost:1521:rogate";
    static String USER = "system";
    static String PASSWORD = "Changeme0";

    @Override
    public StudentMedicalReport getPatientById(int patientId) {

        String query = "SELECT first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, " +
                "time_in, time_out, medications_administered FROM PATIENT WHERE patient_id = ?";
        StudentMedicalReport student = null;

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                student = new StudentMedicalReport(
                        patientId,
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getString("symptoms"),
                        rs.getString("added_remarks"),
                        rs.getString("temperature_readings"),
                        rs.getString("time_in"),
                        rs.getString("time_out"),
                        rs.getString("medications_administered")
                );
            }

            stmt.close();
            con.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error connecting to the database.", ex);
        }

        return student;
    }
}
