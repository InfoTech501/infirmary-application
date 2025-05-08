package com.rocs.infirmary.application.data.dao.medicine.inventory.impl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.medicine.inventory.QueryConstants;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class MedicineInventoryDaoImpl implements MedicineInventoryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineInventoryDaoImpl.class);
    @Override
    public List<Medicine> findAllMedicine() {
        LOGGER.info("get all medicine started");
        List<Medicine> MedicineInventoryList = new ArrayList<>();


        QueryConstants queryConstants = new QueryConstants();
        String sql= queryConstants.getLIST_ALL_MEDICINE_INVENTORY_QUERY();



        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            LOGGER.info("Query in use"+sql);

            while (rs.next()) {

                Medicine medicine = new Medicine();

                medicine.setInventoryId(rs.getInt("INVENTORY_ID"));
                medicine.setMedicineId(rs.getString("MEDICINE_ID"));
                medicine.setItemType(rs.getString("ITEM_TYPE"));
                medicine.setQuantityAvailable(rs.getInt("QUANTITY"));
                medicine.setItemName(rs.getString("ITEM_NAME"));
                medicine.setDescription(rs.getString("DESCRIPTION"));
                medicine.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));

                LOGGER.info("Data retrieved: "+"\n"
                        +"Inventory ID: "+medicine.getInventoryId()+"\n"
                        +"Medicine  ID: "+medicine.getMedicineId()+"\n"
                        +"Item type   : "+medicine.getItemType()+"\n"
                        +"Quantity    : "+medicine.getQuantity()+"\n"
                        +"Item Name   : "+medicine.getItemName()+"\n"
                        +"Description : "+medicine.getDescription()+"\n"
                        +"Expiration  : "+medicine.getExpirationDate()
                );

                MedicineInventoryList.add(medicine);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            System.out.println("An SQL Exception occurred: " + e.getMessage());
        }
        LOGGER.info("Data retrieved successfully");
        return  MedicineInventoryList;
    }

    @Override
    public boolean deleteMedicine(String itemName) {
        LOGGER.info("Delete medicine started");
        try (Connection con = ConnectionHelper.getConnection()) {
            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.getDeleteMedicineQuery();
            PreparedStatement stmt = con.prepareStatement(sql);
            LOGGER.info("Query in use"+sql);
            LOGGER.info("data inserted: "+"Item Name: "+itemName);
            if(isAvailable(itemName)) {

                stmt.setString(1,itemName);

                int affectedRows = stmt.executeUpdate();
                LOGGER.info(itemName+" successfully deleted");
                return affectedRows > 0;

            } else {
                LOGGER.info(itemName+" Failed to delete");
                return false;
            }


        }catch (SQLException e) {
            LOGGER.error("SqlException Occurred: "+e.getMessage());
            throw new RuntimeException();
        }

    }

    @Override
    public boolean isAvailable(String itemName) {
        LOGGER.info("availability check started");
        try(Connection con = ConnectionHelper.getConnection()){
            QueryConstants queryConstants = new QueryConstants();

            String sql = queryConstants.filterDeletedMedicine();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1,itemName);

            ResultSet rs =  stmt.executeQuery();
            return rs.next();

        }catch (SQLException e ) {
            LOGGER.error("SqlException Occurred: "+e.getMessage());
            System.out.println("SQL error " + e.getMessage());
        }
        LOGGER.info("availability check ended successfully");
        return false;
    }
}


