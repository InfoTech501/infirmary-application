package com.rocs.medical.records.application.data.dao.medicineInventory.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicineInventory.UpdateMedicineInventoryDao;
import com.rocs.medical.records.application.model.inventory.UpdateMedicineInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMedicineInventoryDaoImpl implements UpdateMedicineInventoryDao {

    @Override
    public boolean updateMedicineInventory(UpdateMedicineInventory medicine) {
        String sql = "UPDATE INVENTORY SET QUANTITY_AVAILABLE = ?, DESCRIPTION = ? WHERE INVENTORY_ID = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, medicine.getNewQuantity());
            statement.setString(2, medicine.getNewDescription());
            statement.setInt(3, medicine.getNewInventoryId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error updating medicine inventory: " + e.getMessage());
            return false;
        }
    }
}
