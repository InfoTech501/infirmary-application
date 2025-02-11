package com.rocs.medical.records.application.data.dao.commonAilmentsReport.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.commonAilmentsReport.CommonAilmentsReportDAO;
import com.rocs.medical.records.application.model.person.Person;
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
        List<CommonAilmentsReport> reportList = new ArrayList<>();

        String sql = "SELECT a.description as AILMENT, COUNT(*) as occurrence_count, s.SECTION, s.GRADE_LEVEL, p.FIRST_NAME, p.LAST_NAME, p.AGE, s.STRAND " +
                "FROM MEDICAL_RECORD mr " +
                "JOIN AILMENTS a ON mr.AILMENT_ID = a.AILMENT_ID " +
                "JOIN STUDENT st ON mr.STUDENT_ID = st.ID " +
                "JOIN PERSON p ON st.PERSON_ID = p.ID " +
                "LEFT JOIN SECTION s ON st.SECTION_SECTION_ID = s.SECTION_ID " +
                "WHERE mr.VISIT_DATE BETWEEN ? AND ? " +
                "AND (s.GRADE_LEVEL = ? OR ? IS NULL) " +
                "AND (s.SECTION = ? OR ? IS NULL) " +
                "GROUP BY a.description, s.section, s.grade_level, p.FIRST_NAME, p.LAST_NAME, p.AGE, s.STRAND";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            statement.setString(3, gradeLevel);
            statement.setString(4, gradeLevel);
            statement.setString(5, section);
            statement.setString(6, section);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CommonAilmentsReport report = new CommonAilmentsReport();
                report.setAilment(resultSet.getString("AILMENT"));
                report.setOccurrences(resultSet.getInt("OCCURRENCE_COUNT"));
                report.setGradeLevel(resultSet.getString("GRADE_LEVEL"));
                report.setStrand(resultSet.getString("STRAND"));

                Person person = new Person();
                person.setFirstName(resultSet.getString("FIRST_NAME"));
                person.setLastName(resultSet.getString("LAST_NAME"));
                person.setAge(resultSet.getInt("AGE"));


                List<Person> people = new ArrayList<>();
                people.add(person);
                report.setAffectedPeople(people);

                reportList.add(report);
            }
        } catch (SQLException e) {
            System.err.println("Error generating the common ailments report: " + e.getMessage());
        }

        return reportList;
    }
}
