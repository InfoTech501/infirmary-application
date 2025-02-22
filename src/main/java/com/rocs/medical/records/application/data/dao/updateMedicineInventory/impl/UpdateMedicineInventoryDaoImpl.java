package com.rocs.medical.records.application.data.dao.updateMedicineInventory.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.updateMedicineInventory.UpdateMedicineInventoryDao;
import com.rocs.medical.records.application.model.inventory.UpdateMedicineInventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMedicineInventoryDaoImpl implements UpdateMedicineInventoryDao {

    @Override
    public boolean updateMedicineInventory(UpdateMedicineInventory medicine) {
        String sql = "UPDATE MEDICINE_INVENTORY SET QUANTITY_AVAILABLE = ?, UNIT = ?, EXPIRATION_DATE = ? WHERE MEDICINE_NAME = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, medicine.getNewQuantity());
            statement.setString(2, medicine.getNewUnit());
            statement.setString(3, medicine.getNewExpiry());
            statement.setString(4, medicine.getNewName());


            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error updating medicine inventory: " + e.getMessage());
            return false;
        }
    }
}
