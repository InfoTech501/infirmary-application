package com.rocs.Medicine.Inventory.Application.data.dao.impl;
import com.rocs.Medicine.Inventory.Application.model.Inventory.Inventory;
import com.rocs.Medicine.Inventory.Application.data.dao.InventoryDao;
import com.rocs.Medicine.Inventory.Application.data.connection.ConnectionHelper;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {


    @Override
    public List<Inventory> findAllItems() {
        List<Inventory> intvn = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setInventoryId(rs.getInt("INVENTORY_ID"));
                inventory.setMedicineId(rs.getString("MEDICINE_ID"));
                inventory.setItemType(rs.getString("ITEM_TYPE"));
                inventory.setDescription(rs.getString("DESCRIPTION"));
                inventory.setQuantityAvailable(rs.getInt("QUANTITY_AVAILABLE"));



                intvn.add(inventory);
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred: " + e.getMessage());
        }

        return intvn;
    }
}
