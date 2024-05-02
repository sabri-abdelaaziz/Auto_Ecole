package auto_ecole.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChartDataDao {
    private Connection connection;

    public ChartDataDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

     public Map<String, Integer> getBarChartData() {
        Map<String, Integer> data = new HashMap<>();

        try  {
            String query = "SELECT MONTH(date_paiement) AS mois, YEAR(date_paiement) AS annee, SUM(montant) AS total_revenus " +
                           "FROM FacturePaiement " +
                           "GROUP BY MONTH(date_paiement), YEAR(date_paiement) " +
                           "ORDER BY annee ASC, mois ASC";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String mois = resultSet.getString("mois") + "/" + resultSet.getString("annee");
                    int totalRevenus = resultSet.getInt("total_revenus");
                    data.put(mois, totalRevenus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

   public Map<String, Integer> getPieChartData() {
        Map<String, Integer> data = new HashMap<>();

        try  {
            String query = "SELECT u.nom AS nom_etudiant, COUNT(r.id) AS nombre_reservations\n" +
"FROM Eleve u\n" +
"JOIN reservation r ON u.id = r.eleve_id\n" +
"GROUP BY u.nom;";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String category = resultSet.getString("nom_etudiant");
                    int value = resultSet.getInt("nombre_reservations");
                    data.put(category, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
    public Map<String, Float> getLineChartData() {
    Map<String, Float> data = new HashMap<>();

    try  {
        String query = "SELECT MONTH(date_paiement) AS mois, YEAR(date_paiement) AS annee, SUM(montant) AS total_revenus\n" +
                       "FROM FacturePaiement\n" +
                       "GROUP BY YEAR(date_paiement), MONTH(date_paiement)\n" +
                       "ORDER BY YEAR(date_paiement) ASC, MONTH(date_paiement) ASC;";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String mois = resultSet.getString("mois") + "/" + resultSet.getString("annee");
                float totalRevenus = resultSet.getFloat("total_revenus");
                data.put(mois, totalRevenus);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return data;
}

}
