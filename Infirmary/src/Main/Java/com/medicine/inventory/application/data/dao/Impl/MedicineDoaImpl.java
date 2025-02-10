package com.medicine.inventory.application.data.dao.Impl;

import com.medicine.inventory.application.data.connection.ConnectionHelper;
import com.medicine.inventory.application.data.dao.MedicicineDaoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicineDoaImpl implements MedicicineDaoo {
    @Override
    public boolean deleteById(int id) {

        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement stmt = con.prepareStatement("DELETE FROM INVENTORY WHERE MEDICINE_ID = ?");
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred.");
            return false;
        }


    }
}

