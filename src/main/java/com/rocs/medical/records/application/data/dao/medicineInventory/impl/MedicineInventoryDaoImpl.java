package com.rocs.medical.records.application.data.dao.medicineInventory.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicineInventory.MedicineInventoryDao;
import com.rocs.medical.records.application.model.medicineInventory.MedicineInventory;
import jdk.jfr.Timestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineInventoryDaoImpl implements MedicineInventoryDao {

    @Override
    public List<MedicineInventory> findAllMedicine() {
        List<MedicineInventory> medicineInventoryList = new ArrayList<>();

        String sql = "Select i.medicine_id, m.item_name, mr.expiration_date, i.quantity_available"+
                "FROM MEDICINE ma"+
                "JOIN inventory i ON i.medicine_id = ma.medicine_id" +
                "JOIN medicine m ON m.medicine_id = ma.medicine_id " +
                "WHERE m.item_name = ?"+
                "GROUP BY i.medicine_id, m.item_name, mr.expiration_date, i.quantity_available";
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicineInventory medicineInventory = new MedicineInventory();
                medicineInventory.setInventory_id(rs.getInt("inventory_id"));
                medicineInventory.setMedicine_id(rs.getString("medicine_id"));
                medicineInventory.setItem_type(rs.getString("item_type"));
                medicineInventory.setDescription(rs.getString("description"));
                medicineInventory.setId(rs.getInt("id"));
                medicineInventory.setItem_name(rs.getString("item_name"));
                medicineInventory.setQuantity_available(rs.getInt("quantity_available"));
                medicineInventory.setExpiration_date((Timestamp) rs.getTimestamp("expiration_date"));
                medicineInventoryList.add(medicineInventory);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }
        return medicineInventoryList;
    }
}
