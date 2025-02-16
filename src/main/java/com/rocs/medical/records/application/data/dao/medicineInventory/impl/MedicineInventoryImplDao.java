package com.rocs.medical.records.application.data.dao.medicineInventory.impl;

import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicineInventory.MedicineInventoryDao;
import com.rocs.medical.records.application.model.inventory.Inventory;
import com.rocs.medical.records.application.model.medicine.Medicine;
import jdk.jfr.Timestamp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineInventoryImplDao implements MedicineInventoryDao {

    @Override
    public Inventory findItemByInventory_Id(int inventory_id) {
        Inventory medicineInventory = null;
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY WHERE inventory_id = ?");
            stmt.setInt(1, inventory_id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                medicineInventory = new Inventory();
                medicineInventory.setInventory_id(rs.getInt("inventory_id"));
                medicineInventory.setMedicine_id(rs.getString("Medicine_Name"));
                medicineInventory.setItem_type(rs.getString("item_type"));
                medicineInventory.setDescription(rs.getString("description"));
                medicineInventory.setQuantity_Available(rs.getInt("Quantity_Available"));
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }
        return medicineInventory;
    }

    @Override
    public List<Inventory> findAllMedicine(String Medicine_Name) {
        List<Inventory> medicineInventoryList = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY Where Medicine_Name = s?");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inventory medicineInventory = new Inventory();
                medicineInventory.setInventory_id(rs.getInt("inventory_id"));
                medicineInventory.setMedicine_id(rs.getString("medicine_Name"));
                medicineInventory.setItem_type(rs.getString("item_type"));
                medicineInventory.setDescription(rs.getString("description"));
                medicineInventory.setQuantity_Available(rs.getInt("quantity_available"));
                medicineInventoryList.add(medicineInventory);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }

        return medicineInventoryList;
    }

    @Override
    public Medicine findMedicineByMedicine_Id(String Medicine_Id) {
        Medicine medicineInventory = null;
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Medicine WHERE medicine_id = ?");
            stmt.setString(1, Medicine_Id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                medicineInventory = new Medicine();
                medicineInventory.setId(rs.getInt("id"));
                medicineInventory.setMedicine_id(rs.getString("Medicine_Id"));
                medicineInventory.setItem_name(rs.getString("Item_Name"));
                medicineInventory.setExpiration_Date((Timestamp) rs.getTimestamp("Expiration_Date"));
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }
        return medicineInventory;
    }

    @Override
    public List<Medicine> findAllItem(String Item_Name) {
        List<Medicine> medicineInventoryList = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Medicine Where item_name = s?");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medicine medicineInventory = new Medicine();
                medicineInventory.setId(rs.getInt("id"));
                medicineInventory.setMedicine_id(rs.getString("Medicine_Id"));
                medicineInventory.setItem_name(rs.getString("Item_Name"));
                medicineInventory.setExpiration_Date((Timestamp) rs.getTimestamp("Expiration_Date"));

                medicineInventoryList.add(medicineInventory);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }

        return medicineInventoryList;
    }
}




