package com.rocs.infirmary.application.data.dao.reportMedicationTrend.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.reportMedicationTrend.ReportMedicationTrendDao;
import com.rocs.infirmary.application.model.report.MedicationTrendReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportMedicationTrendDaoImpl implements ReportMedicationTrendDao {

    @Override
    public List<MedicationTrendReport> getGenerateReport(Date startDate, Date endDate) {
        List<MedicationTrendReport> report = new ArrayList<>();

        try (Connection connection = ConnectionHelper.getConnection()){
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(medications_administered) AS usage, medications_administered\n" +
                    "FROM patient p\n" +
                    "JOIN inventory i ON i.medicine_name = p.medications_administered\n" +
                    "WHERE p.visit_date BETWEEN TO_TIMESTAMP('2025-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff') AND TO_TIMESTAMP('2025-12-31 23:59:59.99', 'yyyy-mm-dd hh24:mi:ss.ff')\n" +
                    "group by medications_administered\n" +
                    "ORDER BY COUNT(medications_administered)");
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                MedicationTrendReport medication = new MedicationTrendReport();
//                medication.setStartDate(rs.getDate("visit_date"));
                medication.setQuantityUsed(rs.getInt("quantity_used"));
                medication.setMedName(rs.getString("medicine_name"));
                medication.setQuantityAvailable(rs.getInt("quantity_available"));
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception Occurred." + e.getMessage());;
        }

        return report;
    }

}
