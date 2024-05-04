package auto_ecole.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {

    // Method to establish a database connection
    public static Connection connect() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/resources/config.properties"));
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            throw new SQLException("Failed to load properties file");
        }
        
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        return DriverManager.getConnection(url, username, password);
    }

    // Method to close a database connection
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
