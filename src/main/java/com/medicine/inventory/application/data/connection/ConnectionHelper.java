package com.medicine.inventory.application.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:ger";
    private static final String USER = "infirmary";
    private static final String PASSWORD = "Changeme0";
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";



    public static Connection getConnection() {

        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (Exception ex){
            throw new RuntimeException("Error connection to the Database. ", ex);
    }

    }
}

