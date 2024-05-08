package auto_ecole.database;

import auto_ecole.model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDao {
    private Connection connection;

    public ReservationDao() throws SQLException {
        this.connection = DatabaseConnector.connect();
    }

    // Méthode pour récupérer toutes les réservations
    public List<Reservation> getAll() throws SQLException {
        List<Reservation> reservationsList = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                int eleveId = res.getInt("eleve_id");
                int coursId = res.getInt("cours_id");
                Date dateReservation = res.getDate("date_reservation");
                String heureReservation = res.getString("heure_reservation");
                reservationsList.add(new Reservation(id, eleveId, coursId, dateReservation, heureReservation));
            }
        }
        return reservationsList;
    }

    // Méthode pour enregistrer une nouvelle réservation
    public void save(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation (eleve_id, cours_id, date_reservation, heure_reservation) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getEleveId());
            statement.setInt(2, reservation.getCoursId());
            statement.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime())); // Convertir java.util.Date en java.sql.Date
            statement.setString(4, reservation.getHeureReservation());
            statement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour une réservation existante
    public void update(Reservation reservation) throws SQLException {
        String query = "UPDATE reservation SET eleve_id = ?, cours_id = ?, date_reservation = ?, heure_reservation = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getEleveId());
            statement.setInt(2, reservation.getCoursId());
            statement.setDate(3, (java.sql.Date) reservation.getDateReservation());
            statement.setString(4, reservation.getHeureReservation());
            statement.setInt(5, reservation.getId());
            statement.executeUpdate();
        }
    }

     // Méthode pour récupérer une réservation par son ID
    public Reservation getReservationById(int reservationId) throws SQLException {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int eleveId = resultSet.getInt("eleve_id");
                    int coursId = resultSet.getInt("cours_id");
                    java.util.Date dateReservation = resultSet.getDate("date_reservation");
                    String heureReservation = resultSet.getString("heure_reservation");
                    return new Reservation(id, eleveId, coursId, dateReservation, heureReservation); // Retourner un objet Reservation avec les détails récupérés
                }
            }
        }
        return null; // Retourner null si aucune réservation n'est trouvée avec cet ID
    }
    
    // Méthode pour supprimer une réservation par ID
    public void delete(int reservationId) throws SQLException {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            statement.executeUpdate();
        }
    }
}
