package com.rocs.medical.records.application.data.dao.commonAilmentsReport.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.commonAilmentsReport.CommonAilmentsReportDAO;
import com.rocs.medical.records.application.model.reportAffectedStudents.ReportAffectedStudents;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonAilmentsReportDaoImpl implements CommonAilmentsReportDAO {

    @Override
    public List<CommonAilmentsReport> getGeneratedReport(Date startDate, Date endDate, String gradeLevel, String section) {
        List<CommonAilmentsReport> reports = new ArrayList<>();

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement ailmentStatement = createAilmentStatement(connection, startDate, endDate, gradeLevel, section)) {
                ResultSet ailmentSet = ailmentStatement.executeQuery();

                while (ailmentSet.next()) {
                    CommonAilmentsReport report = new CommonAilmentsReport();
                    String ailment = ailmentSet.getString("ailment");
                    report.setAilment(ailment);
                    report.setOccurrences(ailmentSet.getInt("occurrences"));

                    try (PreparedStatement studentStatement = createStudentStatement(connection, startDate, endDate, gradeLevel, section, ailment)) {
                        ResultSet studentSet = studentStatement.executeQuery();
                        List<ReportAffectedStudents> affectedStudents = new ArrayList<>();

                        while (studentSet.next()) {
                            ReportAffectedStudents affectedStudent = new ReportAffectedStudents();
                            affectedStudent.setStudentNumber(studentSet.getString("student_number"));
                            affectedStudent.setFirstName(studentSet.getString("first_name"));
                            affectedStudent.setLastName(studentSet.getString("last_name"));
                            affectedStudent.setGradeLevel(studentSet.getString("grade_level"));
                            affectedStudent.setSection(studentSet.getString("section"));
                            affectedStudent.setAge(studentSet.getInt("age"));
                            affectedStudents.add(affectedStudent);
                        }

                        report.setReportAffectedStudents(affectedStudents);
                    }

                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating common ailments report: " + e.getMessage());
            e.printStackTrace();
        }

        return reports;
    }

    private PreparedStatement createAilmentStatement(Connection connection, Date startDate, Date endDate,
                                                     String gradeLevel, String section) throws SQLException {
        String sql = "SELECT p.symptoms AS ailment, COUNT(*) AS occurrences "
                + "FROM patient p "
                + "JOIN student s ON p.student_number = s.student_number "
                + "WHERE p.visit_date BETWEEN ? AND ? "
                + "AND (s.grade_level = ? OR ? IS NULL) "
                + "AND (s.section = ? OR ? IS NULL) "
                + "GROUP BY p.symptoms "
                + "ORDER BY COUNT(*) DESC";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
        statement.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
        statement.setString(3, gradeLevel);
        statement.setString(4, gradeLevel);
        statement.setString(5, section);
        statement.setString(6, section);

        return statement;
    }

    private PreparedStatement createStudentStatement(Connection connection, Date startDate, Date endDate,
                                                     String gradeLevel, String section, String ailment) throws SQLException {
        String sql = "SELECT DISTINCT s.student_number, s.first_name, s.last_name, "
                + "s.grade_level, s.section, s.age "
                + "FROM patient p "
                + "JOIN student s ON p.student_number = s.student_number "
                + "WHERE p.visit_date BETWEEN ? AND ? "
                + "AND p.symptoms = ? "
                + "AND (s.grade_level = ? OR ? IS NULL) "
                + "AND (s.section = ? OR ? IS NULL)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
        statement.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
        statement.setString(3, ailment);
        statement.setString(4, gradeLevel);
        statement.setString(5, gradeLevel);
        statement.setString(6, section);
        statement.setString(7, section);

        return statement;
    }
}