package com.rocs.infirmary.application.data.dao.utils.queryconstants.medicine.inventory;

public class QueryConstants {

    private final  String LIST_ALL_MEDICINE_INVENTORY_QUERY  = "SELECT i.INVENTORY_ID, i.MEDICINE_ID, i.ITEM_TYPE, i.QUANTITY, m.ITEM_NAME, m.DESCRIPTION, m.EXPIRATION_DATE " +
            "FROM INVENTORY i " +
            "JOIN MEDICINE m ON i.MEDICINE_ID = m.MEDICINE_ID";


    private final String DELETE_MEDICINE_BY_ITEM_NAME_QUERY = "UPDATE MEDICINE SET IS_AVAILABLE = 0 WHERE ITEM_NAME = ?";

    private final String FILTER_MEDICINE_DELETED_QUERY = "SELECT * FROM MEDICINE WHERE IS_AVAILABLE = 1 AND ITEM_NAME = ?";

    private final String  ADD_MEDICINE_TO_INVENTORY_QUERY  = "INSERT INTO MEDICINE (MEDICINE_ID, ITEM_NAME, DESCRIPTION, EXPIRATION_DATE, IS_AVAILABLE) VALUES (?, ?, ?, ?, ?)";

    private final String ADD_MEDICINE_TO_MAIN_INVENTORY_QUERY = "INSERT INTO INVENTORY (MEDICINE_ID , ITEM_TYPE, QUANTITY) VALUES (?,?,?)";

    private final String GET_ALL_MEDICINE_QUERY = "SELECT MEDICINE_ID ,ITEM_NAME FROM MEDICINE";

    private String DELETE_INVENTORY_QUERY = "DELETE INVENTORY WHERE INVENTORY_ID = ? ";


    private String UPDATE_MEDICINE_QUANTITY_QUERY = "UPDATE INVENTORY SET inventory.quantity = ? where inventory.medicine_id = ?";
    private String UPDATE_MEDICINE_DESCRIPTION_QUERY = "UPDATE MEDICINE SET medicine.description = ? where medicine.medicine_id = ?";
    private String UPDATE_MEDICINE_EXPIRATIONDATE_QUERY = "UPDATE MEDICINE SET medicine.expiration_date = ? where medicine.medicine_id = ?";

    public String UPDATE_MEDICINE_EXPIRATIONDATE_QUERY() {
        return UPDATE_MEDICINE_EXPIRATIONDATE_QUERY;
    }

    public String UPDATE_MEDICINE_DESCRIPTION_QUERY() {
        return UPDATE_MEDICINE_DESCRIPTION_QUERY;
    }

    public String UPDATE_MEDICINE_QUANTITY_QUERY() {
        return UPDATE_MEDICINE_QUANTITY_QUERY;
    }
    public String getDeleteMedicineQuery() {return DELETE_MEDICINE_BY_ITEM_NAME_QUERY;}

    public String addMedicineToInventory() {return ADD_MEDICINE_TO_MAIN_INVENTORY_QUERY;}

    public String getLIST_ALL_MEDICINE_INVENTORY_QUERY(){return  LIST_ALL_MEDICINE_INVENTORY_QUERY;}

    public String filterDeletedMedicine() {return FILTER_MEDICINE_DELETED_QUERY;}

    public String addMedicine() {return  ADD_MEDICINE_TO_INVENTORY_QUERY ;}

    public String retrieveAllMedicine() {return GET_ALL_MEDICINE_QUERY;}

    public String deleteInventory() {return DELETE_INVENTORY_QUERY;}
}

