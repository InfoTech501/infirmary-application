package com.rocs.medical.records.application.data.dao.medicineInventory.impl;
import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicineInventory.InventoryDao;
import com.rocs.medical.records.application.model.medicineInventory.Inventory;




import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {
    @Override
    public List<Inventory> getInventoryItems() {
        List<Inventory> inventoryList = new ArrayList<>();


        String sql = "SELECT i.INVENTORY_ID, i.MEDICINE_ID, i.ITEM_TYPE, i.DESCRIPTION, i.QUANTITY_AVAILABLE, m.EXPIRATION_DATE " +
                "FROM INVENTORY i " +
                "JOIN MEDICINE m ON i.MEDICINE_ID = m.MEDICINE_ID";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setInventoryId(rs.getInt("INVENTORY_ID"));
                inventory.setMedicineId(rs.getString("MEDICINE_ID"));
                inventory.setItemType(rs.getString("ITEM_TYPE"));
                inventory.setDescription(rs.getString("DESCRIPTION"));
                inventory.setQuantityAvailable(rs.getInt("QUANTITY_AVAILABLE"));
                inventory.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));


                inventoryList.add(inventory);
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred: " + e.getMessage());
        }

        return inventoryList;
    }
}
