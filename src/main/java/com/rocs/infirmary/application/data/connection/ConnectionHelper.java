package com.rocs.infirmary.application.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public static final String USERNAME = "infirmary";

    public static final String PASSWORD = "Changeme0";

    public static Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            throw new RuntimeException("Error connecting to the database.", ex);
        }
    }
}
