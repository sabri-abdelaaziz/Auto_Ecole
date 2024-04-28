package auto_ecole.database;

import java.sql.*;
import java.util.*;

import auto_ecole.model.Moniteur;

public class MoniteursDao {
    private Connection connection;

    public MoniteursDao() throws SQLException {
        this.connection  = DatabaseConnector.connect();
    }

    // Method to find a Moniteur by Moniteurname
    public Moniteur findByMoniteurname(String Moniteurname) throws SQLException {
        String query = "SELECT * FROM Instructeur WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Moniteurname);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                int id = res.getByte("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String telephone = res.getString("telephone");
                return new Moniteur(id,nom,prenom,adresse,telephone);
                }
            }
        }
        return null; // Moniteur not found
    }

    // Method to retrieve all Moniteurs
    public List<Moniteur> getAll() throws SQLException {
        List<Moniteur> MoniteurList = new ArrayList<>();
        String query = "SELECT * FROM Instructeur";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getByte("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String telephone = res.getString("telephone");
                MoniteurList.add(new Moniteur(id,nom,prenom,adresse,telephone));
            }
        }
        return MoniteurList;
    }

    // Method to save a new Moniteur
    public void save(Moniteur Moniteur) throws SQLException {
    String query = "INSERT INTO Instructeur ( nom, prenom, adresse, telephone) VALUES ( ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
       
        statement.setString(1, Moniteur.getNom());
        statement.setString(2, Moniteur.getPrenom());
        statement.setString(3, Moniteur.getAdresse());
        statement.setString(4, Moniteur.getTelephone());

        statement.executeUpdate();
    }
}
    public int calculMoniteurs() {
    int nbr = 0;
    String query = "SELECT count(*) as count FROM Instructeur"; // Utilisation de l'alias "count" pour obtenir le résultat
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
    
   public void deleteById(int id) throws SQLException {
    // Préparez la requête SQL pour supprimer l'enregistrement avec l'ID spécifié
    String query = "DELETE FROM Instructeur WHERE id = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        // Remplacez le paramètre '?' par l'ID spécifié
        statement.setInt(1, id);
        
        // Exécutez la requête de suppression
        statement.executeUpdate();
    }
}


}
