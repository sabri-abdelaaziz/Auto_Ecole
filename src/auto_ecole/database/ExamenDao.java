/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.database;

import java.util.*;
import auto_ecole.model.Examen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamenDao {
    private Connection connection;

    public ExamenDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Méthode pour récupérer tous les examens
    public List<Examen> getAll() throws SQLException {
        List<Examen> examensList = new ArrayList<>();
        String query = "SELECT * FROM examen";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                java.sql.Date dateExamenSQL = res.getDate("date_examen");
                Date dateExamen = new Date(dateExamenSQL.getTime()); // Conversion de java.sql.Date en java.util.Date
                String heureExamen = res.getString("heure_examen");
                int vehiculeId = res.getInt("vehicule_id");
                int instructeurId = res.getInt("instructeur_id");

                examensList.add(new Examen(id, dateExamen, heureExamen, vehiculeId, instructeurId));
            }

        }
        return examensList;
    }

    // Méthode pour enregistrer un nouvel examen
    public void save(Examen examen) throws SQLException {
        String query = "INSERT INTO examen (date_examen, heure_examen, vehicule_id, instructeur_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(examen.getDateExamen().getTime()));
            statement.setString(2, examen.getHeureExamen());
            statement.setInt(3, examen.getVehiculeId());
            statement.setInt(4, examen.getInstructeurId());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour un examen existant
    public void update(Examen examen) throws SQLException {
        String query = "UPDATE examen SET date_examen = ?, heure_examen = ?, vehicule_id = ?, instructeur_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(examen.getDateExamen().getTime()));
            statement.setString(2, examen.getHeureExamen());
            statement.setInt(3, examen.getVehiculeId());
            statement.setInt(4, examen.getInstructeurId());
            statement.setInt(5, examen.getId());
            statement.executeUpdate();
        }
    }


    // Méthode pour supprimer un examen par ID
    public void delete(int examenId) throws SQLException {
        String query = "DELETE FROM examen WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, examenId);
            statement.executeUpdate();
        }
    }

    
    // Méthode pour calculer le nombre d'examens
    public int calculExamens() throws SQLException {
        int nbrExamens = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnector.connect();
            String query = "SELECT COUNT(*) FROM examen";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nbrExamens = resultSet.getInt(1);
            }
        } finally {
            // Fermer les ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return nbrExamens;
    }


    // Méthode pour trouver un examen par son ID
    public Examen find(int id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Examen exam = null;

        try {
            connection = DatabaseConnector.connect();
            String query = "SELECT * FROM examen WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exam = new Examen();
                exam.setId(resultSet.getInt("id"));
                exam.setDateExamen(resultSet.getDate("date_Examen"));
                exam.setHeureExamen(resultSet.getString("heure_Examen"));
                exam.setVehiculeId(resultSet.getInt("vehicule_id"));
                exam.setInstructeurId(resultSet.getInt("instructeur_id"));
            }
        } finally {
            // Fermer les ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return exam;
    }
}

