package com.rocs.infirmary.application.data.dao.utils.queryconstants.employee;

/**
 * the {@code QueryConstants} class handles the static method for database queries.
 */
public class QueryConstants {
    /**
     * query that retrieves basic nurse identity info from person and employee tables.
     */
    public static String GET_ALL_NURSE_EMPLOYEE = "SELECT p.id, p.first_name, p.middle_name, p.last_name " +
            "FROM person p " +
            "JOIN employee e ON p.id = e.id ";
}
