package auto_ecole.database;

import java.sql.*;
import java.util.*;

import auto_ecole.model.User;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // Method to find a user by username
    public User findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String foundUsername = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    return new User(foundUsername, password);
                }
            }
        }
        return null; // User not found
    }

    // Method to retrieve all users
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String password = resultSet.getString("password");
                userList.add(new User(foundUsername, password));
            }
        }
        return userList;
    }

    // Method to save a new user
    public void save(User user) throws SQLException {
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        }
    }
}
