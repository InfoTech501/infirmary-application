package com.rocs.infirmary.application.data.dao.report.dashboard.impl;
import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardDao;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.report.dashboard.QueryConstants;
import com.rocs.infirmary.application.data.model.person.Person;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockReport;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DashboardDaoImpl implements DashboardDao {


    @Override
    public List<LowStockReport> getAllLowStockMedicine() {
        List<LowStockReport> lowStockItems = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();
        String query = queryConstants.getAllLowStockMedicineQuery();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LowStockReport item = new LowStockReport(resultSet.getString("description"), resultSet.getInt("quantity_available"));
                lowStockItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockItems;
    }

    @Override
    public List<CommonAilmentsReport> getCommonAilmentReport(Date startDate, Date endDate, String gradeLevel, String section) {
        List<CommonAilmentsReport> reportList = new ArrayList<>();
        QueryConstants queryConstants = new QueryConstants();
        String sql = queryConstants.getAllCommonAilmentReportQuery();

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


    @Override
    public List<FrequentVisitReport> getFrequentVisitReports(String gradeLevel, Date startDate, Date endDate) {
        List<FrequentVisitReport> reportsList = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();

        String sql = queryConstants.getFrequentVisitReportsQuery();

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

    @Override
    public List<MedicationTrendReport> getMedicationTrendReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> reportList = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();

        String sql = queryConstants.getAllMedicationTrendReport();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                MedicationTrendReport report = new MedicationTrendReport();
                report.setUsage(rs.getInt("usage"));
                report.setMedicineName(rs.getString("item_name"));
                report.setStocks(rs.getInt("quantity_available"));

                reportList.add(report);
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception Occurred. " + e.getMessage());
        }

        return reportList;
    }
}
