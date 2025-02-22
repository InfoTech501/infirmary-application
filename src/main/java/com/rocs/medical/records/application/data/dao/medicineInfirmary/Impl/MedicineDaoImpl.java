package com.rocs.medical.records.application.data.dao.medicineInfirmary.Impl;


import com.rocs.medical.records.application.data.connection.ConnectionHelper;
import com.rocs.medical.records.application.data.dao.medicineInfirmary.MedicineDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedicineDaoImpl implements MedicineDao {


    @Override
    public boolean deleteMedicineById(String medicineId) {
        try (Connection con = ConnectionHelper.getConnection()) {

            PreparedStatement deleteFromInventory = con.prepareStatement("DELETE FROM INVENTORY WHERE MEDICINE_ID = ?");
            deleteFromInventory.setString(1, medicineId);
            deleteFromInventory.executeUpdate();


            PreparedStatement deleteFromMedicine = con.prepareStatement("DELETE FROM MEDICINE WHERE MEDICINE_ID = ?");
            deleteFromMedicine.setString(1, medicineId);
            int affectedRows = deleteFromMedicine.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}



