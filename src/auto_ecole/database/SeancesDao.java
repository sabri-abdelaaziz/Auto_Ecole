package auto_ecole.database;

import java.util.*;
import auto_ecole.model.Seance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeancesDao {
    private Connection connection;

    public SeancesDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Méthode pour récupérer toutes les séances
    public List<Seance> getAll() throws SQLException {
        List<Seance> seancesList = new ArrayList<>();
        String query = "SELECT * FROM PlanningSeances";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                int coursId = res.getInt("cours_id");
              
                String heureDebut = res.getString("heure_debut");
                String heureFin = res.getString("heure_fin");
                seancesList.add(new Seance(id, coursId, res.getDate("date_seance"), heureDebut, heureFin));
            }
        }
        return seancesList;
    }

    // Méthode pour enregistrer une nouvelle séance
    public void save(Seance seance) throws SQLException {
        String query = "INSERT INTO PlanningSeances (cours_id, date_seance, heure_debut, heure_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seance.getCoursId());
            statement.setDate(2, (java.sql.Date) seance.getDateSeance());
            statement.setString(3, seance.getHeureDebut());
            statement.setString(4, seance.getHeureFin());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour une séance existante
    public void update(Seance seance) throws SQLException {
        String query = "UPDATE PlanningSeances SET cours_id = ?, date_seance = ?, heure_debut = ?, heure_fin = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seance.getCoursId());
              statement.setDate(2, (java.sql.Date)  seance.getDateSeance());
          statement.setString(3, seance.getHeureDebut());
            statement.setString(4, seance.getHeureFin());
            statement.setInt(5, seance.getId());
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer une séance par ID
    public void delete(int seanceId) throws SQLException {
        String query = "DELETE FROM PlanningSeances WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seanceId);
            statement.executeUpdate();
        }
    }
    
    
        public int getNombreSeances() {
        int nbr = 0;
        String query = "SELECT count(*) as count FROM PlanningSeances"; // Utilisation de l'alias "count" pour obtenir le résultat
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

