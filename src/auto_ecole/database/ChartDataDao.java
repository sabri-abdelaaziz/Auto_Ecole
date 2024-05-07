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
            String query = "SELECT modele, COUNT(*) AS nombre_modele\n" +
"FROM Vehicule\n" +
"GROUP BY modele;";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String mois = resultSet.getString("modele");
                    int totalRevenus = resultSet.getInt("nombre_modele");
                    data.put(mois, totalRevenus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

   public Map<String, Double> getPieChartData() {
        Map<String, Double> data = new HashMap<>();

        try  {
            String query = "SELECT mode_paiement, SUM(montant) AS total_montant\n" +
"FROM FacturePaiement\n" +
"GROUP BY mode_paiement;";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String category = resultSet.getString("mode_paiement");
                    double value = resultSet.getDouble("total_montant");
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

        try {
            String query = "SELECT MONTH(date_paiement) AS mois, YEAR(date_paiement) AS annee, SUM(montant) AS total_revenus\n" +
                    "FROM FacturePaiement\n" +
                    "GROUP BY YEAR(date_paiement), MONTH(date_paiement)\n" +
                    "ORDER BY YEAR(date_paiement) ASC, MONTH(date_paiement) ASC;";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                int moisPrecedent = 0;
                int anneePrecedente = 0;
                float sommeCumulative = 0;
                while (resultSet.next()) {
                    int mois = resultSet.getInt("mois");
                    int annee = resultSet.getInt("annee");
                    float totalRevenus = resultSet.getFloat("total_revenus");
                    String date = mois + "/" + annee;
                    if (moisPrecedent != 0 && anneePrecedente != 0) {
                        // Calcul de la croissance exponentielle
                        float croissance = (totalRevenus - sommeCumulative) / sommeCumulative;
                        float prevData = data.get(moisPrecedent + "/" + anneePrecedente);
                        float newData = prevData * (1 + croissance);
                        data.put(date, newData);
                    } else {
                        data.put(date, totalRevenus);
                    }
                    moisPrecedent = mois;
                    anneePrecedente = annee;
                    sommeCumulative = totalRevenus;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
