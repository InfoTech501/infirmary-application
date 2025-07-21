package com.rocs.infirmary.application.data.dao.utils.queryconstants.medicine.inventory;
/**
 * the {@code QueryConstants} class handles the static method for database queries
 * */
public class QueryConstants {
    /**
     * query that retrieves all medicine information
     * */
    public static String GET_ALL_MEDICINE_INVENTORY_QUERY = "SELECT i.INVENTORY_ID, i.MEDICINE_ID, i.ITEM_TYPE, i.QUANTITY,i.EXPIRATION_DATE, m.ITEM_NAME, m.DESCRIPTION  " +
            "FROM INVENTORY i " +
            "JOIN MEDICINE m ON i.MEDICINE_ID = m.MEDICINE_ID";
    /**
     * query that delete medicine by its item name
     * */
    public static String DELETE_MEDICINE_BY_ID_QUERY = "UPDATE MEDICINE SET IS_AVAILABLE = 0 WHERE medicine.medicine_Id IN ";
    /**
     * query that filters available medicine
     * */
    public static String FILTER_AVAILABLE_MEDICINE_QUERY = "SELECT * FROM MEDICINE WHERE IS_AVAILABLE = 1 AND ITEM_NAME = ?";
    /**
     * query that adds item in the medicine
     * */
    public static String ADD_MEDICINE_QUERY = "INSERT INTO MEDICINE (ITEM_NAME, DESCRIPTION, IS_AVAILABLE) VALUES (?, ?, ?)";
    /**
     * query that add item to inventory
     * */
    public static String ADD_ITEM_TO_INVENTORY_QUERY = "INSERT INTO INVENTORY (MEDICINE_ID , ITEM_TYPE, QUANTITY,EXPIRATION_DATE) VALUES (?,?,?,?)";
    /**
     * query that retrieve all available medicine from medicine_table
     * */
    public static String GET_ALL_MEDICINE_QUERY = "SELECT MEDICINE_ID ,ITEM_NAME,DESCRIPTION FROM MEDICINE WHERE IS_AVAILABLE = 1";
    /**
     * query that delete item to inventory
     * */
    public static String DELETE_INVENTORY_ITEM_QUERY = "DELETE INVENTORY WHERE INVENTORY_ID IN ";
    /**
     * query that updates item quantity
     * */
    public static String UPDATE_ITEM_QUANTITY_QUERY = "UPDATE INVENTORY SET inventory.quantity = ? where inventory.inventory_id = ?";
    /**
     * query that updated the item type
     * */
    public static String UPDATE_ITEM_TYPE_QUERY = "UPDATE INVENTORY SET inventory.item_type = ? where inventory.inventory_id = ?";
    /**
     * query that updated the medicine description
     * */
    public static String UPDATE_MEDICINE_DESCRIPTION_QUERY = "UPDATE MEDICINE SET medicine.description = ? where medicine.medicine_id = ?";
    /**
     * query that updated the item expiration date
     * */
    public static String UPDATE_MEDICINE_EXPIRATION_DATE_QUERY = "UPDATE INVENTORY SET inventory.expiration_date = ? where inventory.inventory_id = ?";
    /**
     * query that updated the medicine name
     * */
    public static String UPDATE_MEDICINE_NAME = "UPDATE MEDICINE SET medicine.item_name = ? where medicine.medicine_id = ?";

}

