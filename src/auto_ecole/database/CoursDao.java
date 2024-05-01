package auto_ecole.database;

import java.sql.*;
<<<<<<< HEAD
import java.util.Date;
import java.util.List;
import auto_ecole.model.Cours;
import java.util.ArrayList;
=======
import java.util.*;
import auto_ecole.model.Cours;
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)

public class CoursDao {
    private Connection connection;

    public CoursDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }
<<<<<<< HEAD
    
    public Cours find(int id) throws SQLException{
     String query = "SELECT * FROM Cours WHERE id = ?";
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
=======
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)

    // Méthode pour récupérer tous les cours
    public List<Cours> getAll() throws SQLException {
        List<Cours> coursList = new ArrayList<>();
<<<<<<< HEAD
            String query = "SELECT * FROM Cours";
=======
        String query = "SELECT * FROM Cours";
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
<<<<<<< HEAD
=======
                String dateDebut = res.getString("date_debut");
                String dateFin = res.getString("date_fin");
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
                String titre=res.getString("titre");
                String heureDebut = res.getString("heure_debut");
                String heureFin = res.getString("heure_fin");
                int vehiculeId = res.getInt("vehicule_id");
<<<<<<< HEAD
                coursList.add(new Cours(id,titre, res.getDate("date_debut"), res.getDate("date_fin"), heureDebut, heureFin, vehiculeId));
=======
                coursList.add(new Cours(id,titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId));
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
            }
        }
        return coursList;
    }

    // Méthode pour enregistrer un nouveau cours
    public void save(Cours cours) throws SQLException {
<<<<<<< HEAD
        String query = "INSERT INTO Cours (titre, date_debut, date_fin, heure_debut, heure_fin, vehicule_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, cours.getTitre());
            st.setDate(2,(java.sql.Date) cours.getDateDebut());
            st.setDate(3, (java.sql.Date) cours.getDateFin());
            st.setString(4, cours.getHeureDebut());
            st.setString(5, cours.getHeureFin());
            st.setInt(6, cours.getVehiculeId());
            st.executeUpdate();
=======
        String query = "INSERT INTO Cours (date_debut, date_fin, heure_debut, heure_fin, vehicule_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cours.getDateDebut());
            statement.setString(2, cours.getDateFin());
            statement.setString(3, cours.getHeureDebut());
            statement.setString(4, cours.getHeureFin());
            statement.setInt(5, cours.getVehiculeId());
            statement.executeUpdate();
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        }
    }

    // Méthode pour mettre à jour un cours existant
    public void update(Cours cours) throws SQLException {
<<<<<<< HEAD
        String query = "UPDATE Cours SET titre = ?, date_debut = ?, date_fin = ?, heure_debut = ?, heure_fin = ?, vehicule_id = ? WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, cours.getTitre());
            st.setDate(2, (java.sql.Date) cours.getDateDebut());
            st.setDate(3, (java.sql.Date) cours.getDateFin());
            st.setString(4, cours.getHeureDebut());
            st.setString(5, cours.getHeureFin());
            st.setInt(6, cours.getVehiculeId());
            st.setInt(7, cours.getId());
            st.executeUpdate();
=======
        String query = "UPDATE Cours SET date_debut = ?, date_fin = ?, heure_debut = ?, heure_fin = ?, vehicule_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cours.getDateDebut());
            statement.setString(2, cours.getDateFin());
            statement.setString(3, cours.getHeureDebut());
            statement.setString(4, cours.getHeureFin());
            statement.setInt(5, cours.getVehiculeId());
            statement.setInt(6, cours.getId());
            statement.executeUpdate();
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
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
<<<<<<< HEAD

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
=======
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
}
