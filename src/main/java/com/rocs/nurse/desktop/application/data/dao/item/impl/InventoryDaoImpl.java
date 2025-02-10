package com.rocs.nurse.desktop.application.data.dao.item.impl;
import com.rocs.nurse.desktop.application.model.Inventory.Inventory;
import com.rocs.nurse.desktop.application.data.dao.item.InventoryDao;
import com.rocs.nurse.desktop.application.data.connection.ConnectionHelper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {


    @Override
    public List<Inventory> findAllItems() {
        List<Inventory> intvn = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY ");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Inventory Inventory = new Inventory();
                Inventory.setMedicineId(rs.getInt("MEDICINE_ID"));
                Inventory.setGenericName(rs.getString("MEDICINE_NAME"));
                Inventory.setMedicineBrand(rs.getString("BRAND"));
                Inventory.setDosage(rs.getString("DOSAGE"));
                Inventory.setMedDiscription((rs.getString("MED_DESCRIPTION")));
                Inventory.setAvailableQuantity(rs.getInt( "QUANTITY_AVAILABLE"));
                Inventory.setUsedQuantity(rs.getInt("QUANTITY_USED"));
                Inventory.setMedUsage(rs.getInt("MED_TOTAL_USAGE"));
                Inventory.setExpiryDate(rs.getTimestamp("EXPIRATION_DATE"));
                intvn.add(Inventory);
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }

        return intvn;
    }
}
