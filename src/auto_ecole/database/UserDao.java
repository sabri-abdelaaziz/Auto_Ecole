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
    String query = "INSERT INTO Eleve (nom, prenom, adresse, telephone) VALUES (?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, user.getNom());
        statement.setString(2, user.getPrenom());
        statement.setString(3, user.getAdresse());
        statement.setString(4, user.getTelephone());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("L'insertion de l'utilisateur a échoué, aucune ligne affectée.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                user.setId(userId); // Mettre à jour l'ID de l'utilisateur avec celui généré
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour l'utilisateur ajouté.");
            }
        }
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
     
     // Méthode pour récupérer un utilisateur par son ID
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM eleve WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String adresse = resultSet.getString("adresse");
                    String telephone = resultSet.getString("telephone");
                    return new User(id, nom, prenom, adresse, telephone); // Retourner un objet User avec les détails récupérés
                }
            }
        }
        return null; // Retourner null si aucun utilisateur n'est trouvé avec cet ID
    }
    
    public int getLastInsertedId() throws SQLException {
    int lastInsertedId = -1;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        String query = "SELECT LAST_INSERT_ID() AS last_id"; // MySQL
        statement = connection.prepareStatement(query); // Utilisez l'instance de connexion existante
        
        resultSet = statement.executeQuery();

        if (resultSet.next()) {
            lastInsertedId = resultSet.getInt("last_id");
        }
    } finally {
        // Fermez les ressources de manière sécurisée
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        // Ne fermez pas la connexion ici, car elle doit être gérée de manière externe
    }

    return lastInsertedId;
}

}
