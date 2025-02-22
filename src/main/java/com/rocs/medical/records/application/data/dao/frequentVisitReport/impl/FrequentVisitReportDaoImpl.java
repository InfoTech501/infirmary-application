package com.rocs.medical.records.application.data.dao.frequentVisitReport.impl;

import com.rocs.medical.records.application.data.dao.frequentVisitReport.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrequentVisitReportDaoImpl implements FrequentVisitReportDao {
    @Override
    public List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate) {
        List<FrequentVisitReport> reportsList = new ArrayList<>();

        String sql = "SELECT mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms, COUNT(*) AS visit_count\n" +
                "FROM medical_record mr\n" +
                "JOIN student st ON mr.student_id = st.id\n" +
                "JOIN section s ON st.section_section_id = s.section_id\n" +
                "JOIN person p ON st.person_id = p.id\n" +
                "WHERE s.grade_level = ? " +
                "AND mr.visit_date BETWEEN ? AND ?\n" +
                "GROUP BY mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gradeLevel);
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FrequentVisitReport report = new FrequentVisitReport();
                report.setStudentId(rs.getInt("student_id"));
                report.setFirstName(rs.getString("first_name"));
                report.setLastName(rs.getString("last_name"));
                report.setGradeLevel(rs.getString("grade_level"));
                report.setVisitDate(rs.getDate("visit_date"));
                report.setSymptoms(rs.getString("symptoms"));
                report.setVisitCount(rs.getInt("visit_count"));

                reportsList.add(report);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception Occurred. " + e.getMessage());
        }
        return reportsList;
    }
}
