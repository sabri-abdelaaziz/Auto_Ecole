package auto_ecole.database;

import auto_ecole.model.Paiement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaiementDao {
    private Connection connection;

    public PaiementDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Méthode pour récupérer tous les paiements
    public List<Paiement> getAll() throws SQLException {
        List<Paiement> paiements = new ArrayList<>();
        String query = "SELECT * FROM FacturePaiement";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int eleveId = resultSet.getInt("eleve_id");
                double montant = resultSet.getDouble("montant");
                Date datePaiement = resultSet.getDate("date_paiement");
                String modePaiement = resultSet.getString("mode_paiement");

                Paiement paiement = new Paiement(eleveId, montant, datePaiement, modePaiement);
                paiements.add(paiement);
            }
        }
        return paiements;
    }

    // Méthode pour enregistrer un nouveau paiement
    public void save(Paiement paiement) throws SQLException {
        String query = "INSERT INTO FacturePaiement (eleve_id,montant, date_paiement, mode_paiement) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paiement.getEleveId());
            statement.setDouble(2, paiement.getMontant());
            statement.setDate(3, new java.sql.Date(paiement.getDatePaiement().getTime()));
            statement.setString(4, paiement.getModePaiement());
            statement.executeUpdate();
        }
    }

    // Méthode pour supprimer un paiement par ID
    public void delete(int paiementId) throws SQLException {
        String query = "DELETE FROM FacturePaiement WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paiementId);
            statement.executeUpdate();
        }
    }
    
    public double totalRevenues() {
       double nbr = 0;
        String query = "SELECT sum(montant) as count FROM FacturePaiement"; // Utilisation de l'alias "count" pour obtenir le résultat
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet res = statement.executeQuery()) {
            if (res.next()) {
                nbr = res.getDouble( "count"); // Récupération du résultat à partir de l'alias "count"
            }
        } catch (SQLException ex) {
            // Gérer l'exception (affichage d'un message d'erreur, journalisation, etc.)
            ex.printStackTrace();
        }
        return nbr;
    }
}
