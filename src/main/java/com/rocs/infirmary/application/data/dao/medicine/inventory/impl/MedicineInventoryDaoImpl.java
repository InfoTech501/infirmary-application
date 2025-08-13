package com.rocs.infirmary.application.data.dao.medicine.inventory.impl;
import com.rocs.infirmary.application.data.dao.medicine.inventory.MedicineInventoryDao;
import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import com.rocs.infirmary.application.data.dao.utils.queryconstants.medicine.inventory.QueryConstants;
import com.rocs.infirmary.application.data.model.inventory.medicine.Medicine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.rocs.infirmary.application.data.dao.utils.queryconstants.medicine.inventory.QueryConstants.*;

/**
 * The MedicineInventoryDaoImpl class is an implementation of the Medicine Inventory Dao Interface.
 * It provides methods that handles the business logics of create, update and delete functionality.
 */
public class MedicineInventoryDaoImpl implements MedicineInventoryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineInventoryDaoImpl.class);
    @Override
    public List<Medicine> findAllMedicine() {
        LOGGER.info("get all medicine started");
        List<Medicine> MedicineInventoryList = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICINE_INVENTORY_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            LOGGER.debug("Query in use"+ GET_ALL_MEDICINE_INVENTORY_QUERY);

            while (rs.next()) {

                Medicine medicine = new Medicine();

                medicine.setInventoryId(rs.getLong("INVENTORY_ID"));
                medicine.setMedicineId(rs.getLong("MEDICINE_ID"));
                medicine.setItemType(rs.getString("ITEM_TYPE"));
                medicine.setQuantityAvailable(rs.getInt("QUANTITY"));
                medicine.setItemName(rs.getString("ITEM_NAME"));
                medicine.setExpirationDate(rs.getTimestamp("EXPIRATION_DATE"));
                medicine.setDescription(rs.getString("DESCRIPTION"));

                LOGGER.debug("Data retrieved: "+"\n"
                        +"Inventory ID   : "+medicine.getInventoryId()+"\n"
                        +"Medicine  ID   : "+medicine.getMedicineId()+"\n"
                        +"Item type      : "+medicine.getItemType()+"\n"
                        +"Quantity       : "+medicine.getQuantity()+"\n"
                        +"Item Name      : "+medicine.getItemName()+"\n"
                        +"Expiration Date: "+medicine.getExpirationDate()+"\n"
                        +"Description    : "+medicine.getDescription()
                );

                MedicineInventoryList.add(medicine);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            System.out.println("An SQL Exception occurred: " + e.getMessage());
        }
        LOGGER.info("Data retrieved successfully");
        LOGGER.info("Retrieved Date :   " + new Date());
        return  MedicineInventoryList;
    }

    @Override
    public boolean deleteMedicine(List<Medicine> medicines) {
        LOGGER.info("Delete medicine started");
        int affectedRows;

        if (medicines == null || medicines.isEmpty()) {
            LOGGER.debug("medicine list is empty");
            return false;
        }
        List<Long> collectedId = medicines.stream().map(Medicine::getMedicineId).collect(Collectors.toList());
        String placeholder = collectedId.stream().map(id -> "?").collect(Collectors.joining(", "));

        try (Connection con = ConnectionHelper.getConnection()) {
            String query = DELETE_MEDICINE_BY_ID_QUERY+"("+placeholder+")";
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < collectedId.size(); i++) {
                stmt.setLong(i + 1, collectedId.get(i));
            }
            affectedRows = stmt.executeUpdate();
            LOGGER.debug("Query : " + query);
            LOGGER.info("Deleted Date : " + new Date());
        }catch (SQLException e) {
            LOGGER.error("SqlException Occurred: "+e.getMessage());
            throw new RuntimeException();
        }
        return affectedRows > 0;
    }

    @Override
    public boolean isAvailable(String itemName) {
        LOGGER.info("availability check started");
        try(Connection con = ConnectionHelper.getConnection()){
            PreparedStatement stmt = con.prepareStatement(FILTER_AVAILABLE_MEDICINE_QUERY);
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

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(ADD_MEDICINE_QUERY)) {
            stmt.setString(1, medicine.getItemName());
            stmt.setString(2, medicine.getDescription());
            stmt.setInt(3, 1);
            int affectedRow = stmt.executeUpdate();

            return affectedRow > 0;

        } catch (SQLException e) {
            System.out.println("Medicine ID already exist");
        }
        LOGGER.info("Added Date :   " + new Date());
        return false;
    }

    @Override
    public boolean updateInventory(Long inventoryId, Long medicineId, int quantity, String itemType, Date expirationDate) {
        LOGGER.info("update medicine started");
        boolean isUpdated = false;

        try(Connection connection = ConnectionHelper.getConnection()){
            if(quantity != 0){
                try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_QUANTITY_QUERY)) {
                    preparedStatement.setInt(1,quantity);
                    preparedStatement.setLong(2, inventoryId);
                    int affectedRows = preparedStatement.executeUpdate();
                    isUpdated = affectedRows > 0;
                    LOGGER.info("Data inserted:\n" +
                                "Medicine ID : {}\n" +
                                "Quantity    : {}", inventoryId,quantity);
                    LOGGER.info("Quantity Updated Successfully");
                }catch (SQLException e){
                    LOGGER.error("Error during update quantity"+e);
                }
            }
            if (itemType != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_TYPE_QUERY)) {
                    preparedStatement.setString(1, itemType);
                    preparedStatement.setLong(2, inventoryId);
                    int affectedRows = preparedStatement.executeUpdate();
                    isUpdated = affectedRows > 0;
                    LOGGER.info("Data inserted:\n" +
                            "Medicine ID : {}\n" +
                            "Description : {}", medicineId, itemType);
                    LOGGER.info("Description Updated Successfully");
                } catch (SQLException e) {
                    LOGGER.error("Error during update description " + e);
                }
            }
            if(expirationDate != null){

                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEDICINE_EXPIRATION_DATE_QUERY)){
                    preparedStatement.setTimestamp(1, new Timestamp(expirationDate.getTime()));
                    preparedStatement.setLong(2, inventoryId);
                    int affectedRows = preparedStatement.executeUpdate();
                    isUpdated = affectedRows > 0;
                    LOGGER.info("Data inserted:\n" +
                            "Medicine ID     : {}\n" +
                            "Expiration Date : {}", medicineId,expirationDate);
                    LOGGER.info("Date Updated Successfully");
                }catch (SQLException e){
                    LOGGER.error("Error during update expirationdate"+e);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
        }
        LOGGER.info("Updated Date :   " + new Date());
        return isUpdated;
    }

    @Override
    public boolean updateMedicine(Long medicineId, String medicineName, String description) {
        boolean isUpdated = false;
        try(Connection connection = ConnectionHelper.getConnection()) {
            if (description != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEDICINE_DESCRIPTION_QUERY)) {
                    preparedStatement.setString(1, description);
                    preparedStatement.setLong(2, medicineId);
                    int affectedRows = preparedStatement.executeUpdate();
                    isUpdated = affectedRows > 0;
                    LOGGER.info("Data inserted:\n" +
                            "Medicine ID : {}\n" +
                            "Description : {}", medicineId, description);
                    LOGGER.info("Description Updated Successfully");
                } catch (SQLException e) {
                    LOGGER.error("Error during update description " + e);
                }
            }
            if(medicineName!= null){
                try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MEDICINE_NAME)) {
                    preparedStatement.setString(1,medicineName);
                    preparedStatement.setLong(2,medicineId);
                    int affectedRows = preparedStatement.executeUpdate();
                    isUpdated = affectedRows > 0;
                    LOGGER.info("Data inserted:\n" +
                            "Medicine ID   : {}\n" +
                            "Medicine name : {}", medicineId, medicineName);
                    LOGGER.info("Medicine Name Updated Successfully");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error during update medicine name " + e);
        }
        return isUpdated;
    }

    @Override
    public boolean addInventory(Long medicineId, String itemType, int quantity, Date expirationDate) {
        LOGGER.info("Accessing Add Inventory DAO");
        QueryConstants queryConstants = new QueryConstants();

        try {
            Connection con = ConnectionHelper.getConnection();
            PreparedStatement stmt = con.prepareStatement(ADD_ITEM_TO_INVENTORY_QUERY);
            LOGGER.info("query in use : {}", ADD_ITEM_TO_INVENTORY_QUERY);

            stmt.setLong(1, medicineId);
            stmt.setString(2, itemType);
            stmt.setInt(3, quantity);
            stmt.setTimestamp(4, new Timestamp(expirationDate.getTime()));
            int affectedRows = stmt.executeUpdate();
            LOGGER.info("Retrieved Data : " + " \n"
                    + "Medicine ID : " + medicineId + "\n"
                    + "ItemType   : " + itemType + "\n"
                    + "Quantity  : " + quantity
            );
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.error("SQL Exception Occurred {}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteInventory(List<Medicine> medicines) {
        LOGGER.info("Accessing Delete Inventory on DAO ");
        int affectedRows;

        if (medicines == null || medicines.isEmpty()) {
            LOGGER.debug("medicine list is empty");
            return false;
        }
        List<Long> collectedId = medicines.stream().map(Medicine::getInventoryId).collect(Collectors.toList());
        String placeholder = collectedId.stream().map(id -> "?").collect(Collectors.joining(", "));

        try (Connection con = ConnectionHelper.getConnection()) {
            String query = DELETE_INVENTORY_ITEM_QUERY + "(" + placeholder + ")";
            PreparedStatement stmt = con.prepareStatement(query);
            for (int i = 0; i < collectedId.size(); i++) {
                stmt.setLong(i + 1, collectedId.get(i));
            }
            affectedRows = stmt.executeUpdate();
            LOGGER.debug("Query : " + query);
            LOGGER.info("Deleted Date : " + new Date());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return affectedRows > 0;
    }

    @Override
    public List<Medicine> findAll() {
        LOGGER.info("get all medicine started");
        List<Medicine> MedicineInventoryList = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_MEDICINE_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            LOGGER.info("Query in use"+GET_ALL_MEDICINE_QUERY);

            while (rs.next()) {

                Medicine medicine = new Medicine();

                medicine.setMedicineId(rs.getLong("MEDICINE_ID"));
                medicine.setItemName(rs.getString("ITEM_NAME"));
                medicine.setDescription(rs.getString("DESCRIPTION"));

                LOGGER.info("Data retrieved: "+"\n"
                        +"Medicine  ID: "+medicine.getMedicineId()+"\n"
                        +"Item Name   : "+medicine.getItemName()+"\n"
                        +"Description : "+medicine.getDescription()
                );

                MedicineInventoryList.add(medicine);
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: " + e.getMessage());
            System.out.println("An SQL Exception occurred: " + e.getMessage());
        }
        LOGGER.info("Data retrieved successfully");
        LOGGER.info("Retrieved Date :   " + new Date());
        return  MedicineInventoryList;
    }

}


