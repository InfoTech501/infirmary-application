package com.rocs.frequent.visit.function.data.FreqVisitDao.impl;

import com.rocs.frequent.visit.function.data.FreqVisitDao.FrequentVisitDao;
import com.rocs.frequent.visit.function.data.connection.ConnectionHelper;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FrequentVisitDaoImpl implements FrequentVisitDao {
    @Override
    public List<Patients> getFrequentVisitors(String gradeLevel, String startDate, String endDate) {
        List<Patients> patients = new ArrayList<>();
        String sql = "SELECT id, last_name, grade_level, COUNT (*) OVER () AS Total_Visit FROM infirmary.patient WHERE grade_level = ? AND visit_date BETWEEN ? AND ? ORDER BY visit_date";

        //"SELECT id, last_name, grade_level FROM infirmary.patient WHERE grade_level = ? AND visit_date BETWEEN ? AND ? ORDER BY visit_date"

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gradeLevel);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(startDate + " 00:00:00.000"));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(endDate + " 23:59:59.999"));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String last_name = rs.getString("last_name");
                    String grade_level = rs.getString("grade_level");
                    patients.add(new Patients(id, last_name, grade_level));
                }
            }
        } catch (SQLException e) {
            System.out.println("No Data is Available. " + e.getMessage());
            e.printStackTrace();
        }

        return patients;
    }
}