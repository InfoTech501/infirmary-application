package com.rocs.infirmary.application.data.dao.lowStockMedicine.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.lowStockMedicine.LowStockMedicineDao;
import com.rocs.infirmary.application.model.lowStockMedicine.LowStockMedicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LowStockMedicineDaoImpl implements LowStockMedicineDao {

    public List<LowStockMedicine> getLowStockItems() {
        List<LowStockMedicine> lowStockItems = new ArrayList<>();
        String query = "SELECT description, quantity_available FROM inventory WHERE quantity_available < ?"; 


        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 20);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LowStockMedicine item = new LowStockMedicine(resultSet.getString("description"), resultSet.getInt("quantity_available"));
                lowStockItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lowStockItems;
    }

    @Override
    public void checkLowStockAndNotify() {
        List<LowStockMedicine> lowStockItems = getLowStockItems();
        for (LowStockMedicine item : lowStockItems) {
            sendNotification(item.getDescription(), item.getQuantityAvailable());
        }
    }

    private void sendNotification(String description, int quantityAvailable) {
        System.out.println("Medicine Name: " + description);
        System.out.println("Stock Level: " + quantityAvailable);
        System.out.println("Notification : The stock level of " + description + " is low. Current stock level: " + quantityAvailable + ". Please reorder supplies.");
    }
}
