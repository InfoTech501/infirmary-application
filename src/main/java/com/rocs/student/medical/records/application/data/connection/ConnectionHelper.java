package com.rocs.student.medical.records.application.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    /**
     * Database connection URL.
     */
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:rogate";

    /**
     * Oracle JDBC driver class.
     */
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * Database username.
     */
    public static final String USERNAME = "nursedesk";

    /**
     * Database password.
     */
    public static final String PASSWORD = "Changeme2024";

    /**
     * Creates and returns a connection to the database.
     *
     * @return A Connection object for the database.
     * @throws RuntimeException If there is an error connecting to the database.
     */
    public static Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            throw new RuntimeException("Error connecting to the database.", ex);
        }
    }
}

