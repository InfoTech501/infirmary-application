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
<<<<<<< HEAD
        String sql = "SELECT mr.student_id, s.grade_level, mr.visit_date, mr.symptoms,\n" +
                "    COUNT(*) AS visit_count\n" +
                "FROM medical_record mr\n" +
                "JOIN student st ON mr.student_id = st.id\n" +
                "JOIN section s ON st.section_section_id = s.section_id\n" +
                "WHERE mr.visit_date BETWEEN ? AND ?\n" +
                "AND s.grade_level = ?\n" +
                "GROUP BY mr.student_id, s.grade_level, mr.visit_date, mr.symptoms;";
=======
        String sql = "SELECT mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms, COUNT(*) AS visit_count\n" +
                "FROM medical_record mr\n" +
                "JOIN student st ON mr.student_id = st.id\n" +
                "JOIN section s ON st.section_section_id = s.section_id\n" +
                "JOIN person p ON st.person_id = p.id\n" +
                "WHERE s.grade_level = ? " +
                "AND mr.visit_date BETWEEN ? AND ?\n" +
                "GROUP BY mr.student_id, p.first_name, p.last_name, s.grade_level, mr.visit_date, mr.symptoms";
>>>>>>> cec7215 (frequent visit update)

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gradeLevel);
<<<<<<< HEAD
            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));

            //stmt.setString(2, startDate + " 00:00:00.00");
            //stmt.setString(3, endDate + " 23:59:59.99");
=======
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
>>>>>>> cec7215 (frequent visit update)

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FrequentVisitReport report = new FrequentVisitReport();
                report.setStudentId(rs.getInt("student_id"));
<<<<<<< HEAD
=======
                report.setFirstName(rs.getString("first_name"));
                report.setLastName(rs.getString("last_name"));
>>>>>>> cec7215 (frequent visit update)
                report.setGradeLevel(rs.getString("grade_level"));
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
<<<<<<< HEAD

=======
>>>>>>> cec7215 (frequent visit update)
