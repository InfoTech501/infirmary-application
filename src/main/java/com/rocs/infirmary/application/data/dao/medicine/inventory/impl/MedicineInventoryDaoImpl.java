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

    @Override
    public boolean addMedicine(Medicine medicine) {
        QueryConstants queryConstants = new QueryConstants();
        String sql = queryConstants.addMedicine();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, medicine.getMedicineId());
            stmt.setString(2, medicine.getItemName());
            stmt.setString(3, medicine.getDescription());
            stmt.setTimestamp(4, new java.sql.Timestamp(medicine.getExpirationDate().getTime()));
            stmt.setInt(5, 1);
            int affectedRow = stmt.executeUpdate();

            return affectedRow > 0;

        } catch (SQLException e) {
            System.out.println("Medicine ID already exist");
        }

        return false;
    }

    @Override
    public boolean addInventory(String medicineId, String itemType, int quantity) {
        LOGGER.info("Accessing Add Inventory DAO");
        QueryConstants queryConstants = new QueryConstants();

        try {
            Connection con = ConnectionHelper.getConnection();
            String sql = queryConstants.addMedicineToInventory();
            LOGGER.info("query in use : {}", sql);

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, medicineId);
            stmt.setString(2, itemType);
            stmt.setInt(3, quantity);

            LOGGER.info("Retrieved Data : " + " \n"
                    + "Medicine ID : " + medicineId + "\n"
                    + "ItemType   : " + itemType + "\n"
                    + "Quantity  : " + quantity
            );

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.error("SQL Exception Occurred {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteInventory(int inventoryID) {
        LOGGER.info("Accessing Delete Inventory on DAO ");
        try (Connection con = ConnectionHelper.getConnection()) {
            QueryConstants queryConstants = new QueryConstants();
            String sql = queryConstants.deleteInventory();
            LOGGER.info("Query : " + sql);

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1,inventoryID);
            int affectRows = stmt.executeUpdate();

            LOGGER.info("Deleted Date :   " + inventoryID);

            return affectRows > 0 ;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


