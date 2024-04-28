package auto_ecole.database;

import java.sql.*;
import java.util.*;

import auto_ecole.model.User;

public class UserDao {
    private Connection connection;

    public UserDao() throws SQLException {
        this.connection  = DatabaseConnector.connect();
    }

    // Method to find a user by username
    public User findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Eleve WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                int id = res.getByte("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String telephone = res.getString("telephone");
                return new User(id,nom,prenom,adresse,telephone);
                }
            }
        }
        return null; // User not found
    }

    // Method to retrieve all users
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM Eleve";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getByte("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String telephone = res.getString("telephone");
                userList.add(new User(id,nom,prenom,adresse,telephone));
            }
        }
        return userList;
    }

    // Method to save a new user
    public void save(User user) throws SQLException {
    String query = "INSERT INTO Eleve (id, nom, prenom, adresse, telephone) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, user.getId());
        statement.setString(2, user.getNom());
        statement.setString(3, user.getPrenom());
        statement.setString(4, user.getAdresse());
        statement.setString(5, user.getTelephone());

        statement.executeUpdate();
    }
}

}
