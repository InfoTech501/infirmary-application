package com.rocs.medical.records.application.data.dao.reportMedicationTrend.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.reportMedicationTrend.ReportMedicationTrendDao;
import com.rocs.medical.records.application.model.reports.MedicationTrendReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportMedicationTrendDaoImpl implements ReportMedicationTrendDao {

    @Override
    public List<MedicationTrendReport> getGeneratedReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> reportList = new ArrayList<>();

        String sql = "SELECT i.medicine_id, COUNT (*) AS usage, m.item_name, i.quantity_available " +
                "FROM medicine_administered ma " +
                "JOIN medicine m ON m.medicine_id = ma.medicine_id " +
                "JOIN inventory i ON i.medicine_id = ma.medicine_id " +
                "JOIN medical_record mr ON mr.id = ma.med_record_id " +
                "WHERE mr.visit_date BETWEEN ? AND ? " +
                "GROUP BY i.medicine_id, m.item_name, i.quantity_available";

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
