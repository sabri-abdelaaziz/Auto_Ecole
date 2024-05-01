package auto_ecole.database;

import java.sql.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/auto_ecole";
<<<<<<< HEAD
    private static final String USERNAME = "adia";
    private static final String PASSWORD = "adia";
=======
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)

    // Method to establish a database connection
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
