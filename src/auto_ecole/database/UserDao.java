package auto_ecole.database;

import java.sql.*;
import java.util.*;

import auto_ecole.model.User;

public class UserDao {
    private Connection connection;

    public UserDao() throws SQLException {
        this.connection  = DatabaseConnector.connect();
    }
public int calculCandidats() {
    int nbr = 0;
    String query = "SELECT count(*) as count FROM Eleve"; // Utilisation de l'alias "count" pour obtenir le résultat
    try (PreparedStatement statement = connection.prepareStatement(query);
         ResultSet res = statement.executeQuery()) {
        if (res.next()) {
            nbr = res.getInt("count"); // Récupération du résultat à partir de l'alias "count"
        }
    } catch (SQLException ex) {
        // Gérer l'exception (affichage d'un message d'erreur, journalisation, etc.)
        ex.printStackTrace();
    }
    return nbr; // Retourne le nombre de moniteurs
}
    // Method to find a user by username
    public User find(int userId) throws SQLException {
        String query = "SELECT * FROM Eleve WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
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
    
    // Method to find a user by username
    public User getUserByName(String name) throws SQLException {
        String query = "SELECT * FROM Eleve WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
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
    // Method to update an existing user
public void update(User user) throws SQLException {
    String query = "UPDATE Eleve SET nom = ?, prenom = ?, adresse = ?, telephone = ? WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, user.getNom());
        statement.setString(2, user.getPrenom());
        statement.setString(3, user.getAdresse());
        statement.setString(4, user.getTelephone());
        statement.setInt(5, user.getId());

        statement.executeUpdate();
    }
}

// Method to delete a user by ID
public void delete(int userId) throws SQLException {
    String query = "DELETE FROM Eleve WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, userId);
        statement.executeUpdate();
    }
}
     public int getNombreUser() {
        int nbr = 0;
        String query = "SELECT count(*) as count FROM Eleve"; // Utilisation de l'alias "count" pour obtenir le résultat
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet res = statement.executeQuery()) {
            if (res.next()) {
                nbr = res.getInt("count"); // Récupération du résultat à partir de l'alias "count"
            }
        } catch (SQLException ex) {
            // Gérer l'exception (affichage d'un message d'erreur, journalisation, etc.)
            ex.printStackTrace();
        }
        return nbr;
    }

}
