package com.rocs.infirmary.application.data.dao.report.dashboard.impl;
import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.report.dashboard.QueryConstants;
import com.rocs.infirmary.application.data.model.person.Person;
import com.rocs.infirmary.application.data.model.report.ailment.CommonAilmentsReport;
import com.rocs.infirmary.application.data.model.report.lowstock.LowStockItems;
import com.rocs.infirmary.application.data.model.report.visit.FrequentVisitReport;
import com.rocs.infirmary.application.data.model.report.medication.MedicationTrendReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The DashboardReportsImpl class implements the DashboardReports interface
 * It includes methods for notification of stock level of medicine and reports on common ailments, frequent visit, and medication trend.
 */
public class DashboardReportsImpl implements DashboardReports {
    private static Logger LOGGER = LoggerFactory.getLogger(DashboardReportsImpl.class);
    @Override
    public List<LowStockItems> findAllLowStockMedicine() {
        LOGGER.info("Check low stock medicine started");
        List<LowStockItems> lowStockItems = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();
        String query = queryConstants.getAllLowStockMedicineQuery();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            LOGGER.info("Query in use: "+query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LowStockItems item = new LowStockItems(resultSet.getString("item_name"), resultSet.getInt("quantity"));
                LOGGER.info("retrieved data: "+"\n"
                        +"Item Name: "+resultSet.getString("item_name")+"\n"
                        +"Quantity : "+resultSet.getString("quantity"));
                lowStockItems.add(item);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLExeption Occured: "+e.getMessage());
            e.printStackTrace();
        }
        return lowStockItems;
    }

    @Override
    public List<CommonAilmentsReport> findCommonAilmentReport(Date startDate, Date endDate, String gradeLevel, String section) {
        LOGGER.info("Common ailment report started");
        List<CommonAilmentsReport> reportList = new ArrayList<>();
        QueryConstants queryConstants = new QueryConstants();
        String sql = queryConstants.getAllCommonAilmentReportQuery();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            LOGGER.info("Query in use: "+sql);

            statement.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            statement.setString(3, gradeLevel);
            statement.setString(4, gradeLevel);
            statement.setString(5, section);
            statement.setString(6, section);
            LOGGER.info("data inserted:\n"+"Start Date: "+startDate+"\n"+"End Date: "+endDate+"\n"+"Grade level: "+gradeLevel+"\n"+"Section: "+section+"\n");
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

                LOGGER.info("retrieved data:\n"
                        +"Ailment: "+report.getAilment()+"\n"
                        +"Occurrence: "+report.getOccurrences()+"\n"
                        +"Grade level: "+report.getGradeLevel()+"\n"
                        +"Strand: "+report.getStrand()+"\n"
                        +"Name: "+person.getFirstName() + " " +person.getLastName()+"\n"
                        +"Age: "+person.getAge()+"\n"
                );


                List<Person> people = new ArrayList<>();
                people.add(person);
                report.setAffectedPeople(people);

                reportList.add(report);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLExeption Occured: "+e.getMessage());
            System.err.println("Error generating the common ailments report: " + e.getMessage());
        }
        return reportList;
    }


    @Override
    public List<FrequentVisitReport> findFrequentVisit(String gradeLevel, Date startDate, Date endDate) {
        LOGGER.info("Frequent Visit Report started");
        List<FrequentVisitReport> reportsList = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();

        String sql = queryConstants.getFrequentVisitReportsQuery();

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            LOGGER.info("Query in use: "+sql);
            stmt.setString(1, gradeLevel);
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
            LOGGER.info("data inserted:\n"+"Start Date: "+startDate+"\n"+"End Date: "+endDate+"\n"+"Grade level: "+gradeLevel);
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
                LOGGER.info("retrieved data:\n"
                        +"Student ID: "+rs.getInt("student_id")+"\n"
                        +"Name: "+report.getFirstName()+" "+report.getLastName()+"\n"
                        +"Grade level: "+report.getGradeLevel()+"\n"
                        +"Visit Date: "+report.getVisitDate()+"\n"
                        +"Symptoms: "+report.getSymptoms()+"\n"
                        +"Visit Count "+report.getVisitCount()+"\n"
                );
                reportsList.add(report);
            }
        } catch (SQLException e) {
            LOGGER.error("SqlException Occurred "+e.getMessage());
            System.out.println("An SQL Exception Occurred. " + e.getMessage());
        }
        return reportsList;
    }

    @Override
    public List<MedicationTrendReport> findMedicationTrend(Date startDate, Date endDate) {
        LOGGER.info("Medication Trend Report started");
        List<MedicationTrendReport> reportList = new ArrayList<>();

        QueryConstants queryConstants = new QueryConstants();

        String sql = queryConstants.getAllMedicationTrendReport();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){
            LOGGER.info("Query in use: "+sql);
            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            LOGGER.info("data inserted:\n"+"Start Date: "+startDate+"\n"+"End Date: "+endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                MedicationTrendReport report = new MedicationTrendReport();
                report.setUsage(rs.getInt("usage"));
                report.setMedicineName(rs.getString("item_name"));
                report.setStocks(rs.getInt("quantity"));
                LOGGER.info("retrieved data:\n"
                        +"Usage: "+report.getUsage()+"\n"
                        +"Medication Name: "+report.getMedicineName()+"\n"
                        +"Stocks: "+report.getStocks()+"\n"
                );
                reportList.add(report);
            }

        } catch (SQLException e) {
            LOGGER.error("SqlException Occurred "+e.getMessage());
            System.out.println("An SQL Exception Occurred. " + e.getMessage());
        }

        return reportList;
    }
}
