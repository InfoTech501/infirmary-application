package main.com.rocs.infirimary.deskstop.application.data.dbConnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectorHelper {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String Oracle_Driver = "oracle.jdbc.driver.OracleDriver";
    public static final String USERNAME = "infirmary";
    public static final String PASSWORD = "Changeme0";


    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "infirmary", "Changeme0");
        } catch (Exception ex) {
            throw new RuntimeException("Error connecting to database ", ex);
        }
    }
}
