package com.rocs.frequent.visit.function.data.FreqVisitDao.impl;

import com.rocs.frequent.visit.function.data.FreqVisitDao.FrequentVisitDao;
import com.rocs.frequent.visit.function.data.connection.ConnectionHelper;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FrequentVisitDaoImpl implements FrequentVisitDao {

    @Override
    public List<Patients> getFrequentVisitors(String gradeLevel, String startDate, String endDate) {
        List<Patients> patients = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT p.id, p.student_number, p.last_name, p.grade_level, p.symptoms, p.visit_date FROM infirmary.patient p WHERE p.grade_level = ? AND p.visit_date BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF')");
            stmt.setString(1, gradeLevel);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
//SELECT COUNT(*) FROM infirmary.patient p WHERE p.visit_date BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF');
//SELECT DISTINCT p.id, p.student_number, p.last_name, p.grade_level, p.symptoms, p.visit_date FROM infirmary.patient p WHERE p.grade_level = ? AND p.visit_date BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS:FF')"
            while (rs.next()) {
                int id = rs.getInt("id");
                String studentNumber = rs.getString("student_number");
                String name = rs.getString("last_name");
                String symptoms = rs.getString("symptoms");
                Timestamp visit_date = rs.getTimestamp("visit_date");
                patients.add(new Patients(id, studentNumber, name, gradeLevel, symptoms, visit_date));
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception Occurred. " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }
}