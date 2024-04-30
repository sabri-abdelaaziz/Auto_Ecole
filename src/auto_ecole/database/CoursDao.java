package auto_ecole.database;

import java.sql.*;
import java.util.*;
import auto_ecole.model.Cours;

public class CoursDao {
    private Connection connection;

    public CoursDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Méthode pour récupérer tous les cours
    public List<Cours> getAll() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM Cours";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                String dateDebut = res.getString("date_debut");
                String dateFin = res.getString("date_fin");
                String titre=res.getString("titre");
                String heureDebut = res.getString("heure_debut");
                String heureFin = res.getString("heure_fin");
                int vehiculeId = res.getInt("vehicule_id");
                coursList.add(new Cours(id,titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId));
            }
        }
        return coursList;
    }

    // Méthode pour enregistrer un nouveau cours
    public void save(Cours cours) throws SQLException {
        String query = "INSERT INTO Cours (date_debut, date_fin, heure_debut, heure_fin, vehicule_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cours.getDateDebut());
            statement.setString(2, cours.getDateFin());
            statement.setString(3, cours.getHeureDebut());
            statement.setString(4, cours.getHeureFin());
            statement.setInt(5, cours.getVehiculeId());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour un cours existant
    public void update(Cours cours) throws SQLException {
        String query = "UPDATE Cours SET date_debut = ?, date_fin = ?, heure_debut = ?, heure_fin = ?, vehicule_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cours.getDateDebut());
            statement.setString(2, cours.getDateFin());
            statement.setString(3, cours.getHeureDebut());
            statement.setString(4, cours.getHeureFin());
            statement.setInt(5, cours.getVehiculeId());
            statement.setInt(6, cours.getId());
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer un cours par ID
    public void delete(int coursId) throws SQLException {
        String query = "DELETE FROM Cours WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, coursId);
            statement.executeUpdate();
        }
    }
}
