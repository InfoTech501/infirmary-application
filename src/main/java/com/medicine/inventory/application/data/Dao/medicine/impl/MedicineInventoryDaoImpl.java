package com.medicine.inventory.application.data.Dao.medicine.impl;

import com.medicine.inventory.application.data.connection.ConnectionHelper;
import com.medicine.inventory.application.model.medicine.MedicineInventory;
import com.medicine.inventory.application.data.Dao.medicine.MedicineInventoryDao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MedicineInventoryDaoImpl implements MedicineInventoryDao {
    @Override
    public MedicineInventory findItemByMedicine_Id(String medicine_Id) {
        MedicineInventory medicine = null;

        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY WHERE Med_Id = ?");
            stmt.setString(1, medicine_Id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                medicine = new MedicineInventory();
                medicine.setMedicine_Id(rs.getString("Medicine_Id"));
                medicine.setMedicine_Name(rs.getString("Medicine_Name"));
                medicine.setBrand(rs.getString("Brand"));
                medicine.setDosage(rs.getString("Dosage"));
                medicine.setMed_Description(rs.getString("Med_Description"));
                medicine.setQuantity_Available(rs.getInt("Quantity_Available"));
                medicine.setQuantity_Used(rs.getInt("Quantity_Used"));
                medicine.setMed_Total_Usage(rs.getInt("Med_Total_Usage"));
                medicine.setExpiration_Date(rs.getTimestamp("Expiration_Date"));
            }

        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }

        return medicine;
    }



    @Override
    public List<MedicineInventory> findAllMedicine() {
        List<MedicineInventory> medicineList = new ArrayList<>();
        try (Connection con =ConnectionHelper. getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM INVENTORY");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MedicineInventory medicine = new MedicineInventory();
                medicine.setMedicine_Id(rs.getString("medicine_Id"));
                medicine.setMedicine_Name(rs.getString("medicine_Name"));
                medicine.setBrand(rs.getString("brand"));
                medicine.setDosage(rs.getString("dosage"));
                medicine.setMed_Description(rs.getString("Med_Description"));
                medicine.setQuantity_Available(rs.getInt("Quantity_Available"));
                medicine.setQuantity_Used(rs.getInt("Quantity_Used"));
                medicine.setMed_Total_Usage(rs.getInt("Med_Total_Usage"));
                medicine.setExpiration_Date(rs.getTimestamp("Expiration_Date"));
                medicineList.add(medicine);
            }
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }


        return medicineList;
    }



    }



