package com.rocs.infirmary.application.data.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHelper.class);
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    /**
     * The Oracle driver.
     */
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * The username used to connect to the database.
     */
    public static final String USERNAME = "infirmary";

    /**
     * The password used to connect to the database.
     */
    public static final String PASSWORD = "Changeme0";

    /**
     * This method gets the connection from an Oracle database instance.
     */
    public static Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            LOGGER.info("Successfully connected to the database");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Connection Failed: Oracle driver not found: "+e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.error("Connection Failed: Cant connect to database: "+e);
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            LOGGER.error("Connection Failed: cannot instantiate class: "+e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Connection Failed: Illegal class access : "+e);
            throw new RuntimeException(e);
        }
    }
}
