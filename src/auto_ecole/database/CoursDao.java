package auto_ecole.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import auto_ecole.model.Cours;

public class CoursDao {
    private Connection connection;

    public CoursDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    public Cours find(int id) throws SQLException {
        String query = "SELECT * FROM cours WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    String titre = res.getString("titre");
                    Date dateDebut = res.getDate("date_debut");
                    Date dateFin = res.getDate("date_fin");
                    String heureDebut = res.getString("heure_debut");
                    String heureFin = res.getString("heure_fin");
                    int vehiculeId = res.getInt("vehicule_id");
                    return new Cours(id, titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId);
                }
            }
        }
        return null;
    }

    // Méthode pour récupérer tous les cours
    public List<Cours> getAll() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM Cours";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                String titre = res.getString("titre");
                Date dateDebut = res.getDate("date_debut");
                Date dateFin = res.getDate("date_fin");
                String heureDebut = res.getString("heure_debut");
                String heureFin = res.getString("heure_fin");
                int vehiculeId = res.getInt("vehicule_id");

                coursList.add(new Cours(id, titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId));
            }
        }
        return coursList;
    }

    // Méthode pour enregistrer un nouveau cours
    public void save(Cours cours) throws SQLException {
        String query = "INSERT INTO Cours (titre, date_debut, date_fin, heure_debut, heure_fin, vehicule_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cours.getTitre());
            statement.setDate(2, (Date) cours.getDateDebut());
            statement.setDate(3, (Date) cours.getDateFin());
            statement.setString(4, cours.getHeureDebut());
            statement.setString(5, cours.getHeureFin());
            statement.setInt(6, cours.getVehiculeId());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour un cours existant
    public void update(Cours cours) throws SQLException {
        String query = "UPDATE Cours SET titre = ?, date_debut = ?, date_fin = ?, heure_debut = ?, heure_fin = ?, vehicule_id = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, cours.getTitre());
            st.setDate(2, (Date) cours.getDateDebut());
            st.setDate(3, (Date) cours.getDateFin());
            st.setString(4, cours.getHeureDebut());
            st.setString(5, cours.getHeureFin());
            st.setInt(6, cours.getVehiculeId());
            st.setInt(7, cours.getId());
            st.executeUpdate();
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

    public int getNombreCours() {
        int nbr = 0;
        String query = "SELECT count(*) as count FROM Cours"; // Utilisation de l'alias "count" pour obtenir le résultat
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
    
    // Méthode pour récupérer un cours par son ID
    public Cours getCoursById(int coursId) throws SQLException {
        String query = "SELECT * FROM cours WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, coursId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String titre = resultSet.getString("titre");
                    Date dateDebut = resultSet.getDate("date_debut");
                    Date dateFin = resultSet.getDate("date_fin");
                    String heureDebut = resultSet.getString("heure_debut");
                    String heureFin = resultSet.getString("heure_fin");
                    int vehiculeId = resultSet.getInt("vehicule_id");
                    return new Cours(id, titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId); // Retourner un objet Cours avec les détails récupérés
                }
            }
        }
        return null; // Retourner null si aucun cours n'est trouvé avec cet ID
    }
}
