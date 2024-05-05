package auto_ecole.database;

import auto_ecole.model.Vehicule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDao {


    private Connection connection;

    public VehiculeDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Method to find a vehicle by ID
    public Vehicule find(int id) throws SQLException {
        Vehicule vehicule = null;
        String query = "SELECT * FROM Vehicule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a Vehicule object from the retrieved data
                    vehicule = new Vehicule(
                            resultSet.getInt("id"),
                            resultSet.getString("marque"),
                            resultSet.getString("modele"),
                            resultSet.getInt("annee_fabrication")
                    );
                }
            }
        }
        return vehicule;

    }

    public void save(Vehicule vehicule) throws SQLException {
        String query = "INSERT INTO vehicule (marque, modele, annee_fabrication) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicule.getMarque());
            statement.setString(2, vehicule.getModele());
            statement.setInt(3, vehicule.getAnneeFabrication());

            statement.executeUpdate();
        }
    }
    
    
    public List<Vehicule> getAll() throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String query = "SELECT * FROM vehicule";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String marque = resultSet.getString("marque");
                String modele = resultSet.getString("modele");
                int anneeFabrication = resultSet.getInt("annee_fabrication");
                Vehicule vehicule = new Vehicule(id, marque, modele, anneeFabrication);
                vehicules.add(vehicule);
            }
        }
        return vehicules;
    }

    
    // Méthode pour récupérer tous les ID des véhicules depuis la base de données
    public List<String> getAllVehicleIds() throws SQLException {
        List<String> vehicleIds = new ArrayList<>();
        String query = "SELECT id FROM vehicule";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String vehicleId = resultSet.getString("id");
                    vehicleIds.add(vehicleId);
                }
            }
        }

        return vehicleIds;
    }
    public void delete(int vehiculeId) throws SQLException {
        String query = "DELETE FROM vehicule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehiculeId);
            statement.executeUpdate();
        }
    }
    // Vous pouvez implémenter d'autres méthodes pour la mise à jour et la suppression des véhicules si nécessaire

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public List<Integer> getAllVehiculeIds() throws SQLException {
        List<Integer> vehicleIds = new ArrayList<>();
        String query = "SELECT id FROM Vehicule";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                vehicleIds.add(id);
            }
        }
        return vehicleIds;
    }

    // Méthode pour récupérer un véhicule par son ID
    public Vehicule getById(int id) throws SQLException {
        Vehicule vehicule = null;
        String query = "SELECT * FROM vehicule WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Construire l'objet Vehicule à partir des données récupérées
                    vehicule = new Vehicule();
                    vehicule.setId(resultSet.getInt("id"));
                    vehicule.setMarque(resultSet.getString("marque"));
                    vehicule.setModele(resultSet.getString("modele"));
                    
                }
            }
        }
        return vehicule;
    }
    
    
    
    
        public int getNombreVehicule() {
        int nbr = 0;
        String query = "SELECT count(*) as count FROM Vehicule"; // Utilisation de l'alias "count" pour obtenir le résultat
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

    public void update(Vehicule vehicule) throws SQLException {
    String query = "UPDATE vehicule SET marque = ?, modele = ?, annee_fabrication = ? WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, vehicule.getMarque());
        statement.setString(2, vehicule.getModele());
        statement.setInt(3, vehicule.getAnneeFabrication());
        statement.setInt(4, vehicule.getId());
        statement.executeUpdate();
    }
}
}
