package auto_ecole.gui;

import auto_ecole.database.ReservationDao;
import auto_ecole.database.CoursDao;
import auto_ecole.database.ExamenDao;
import auto_ecole.database.MoniteursDao;
import auto_ecole.database.UserDao;
import auto_ecole.database.VehiculeDao;

import auto_ecole.model.Reservation;
import auto_ecole.model.Cours;
import auto_ecole.model.User;
        
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class GestionReservationsPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private ReservationDao reservationDao;
    private UserDao userDao;
    private CoursDao coursDao;
    private JComboBox<String> candidatComboBox;
    private JComboBox<String> coursComboBox;
    private JDateChooser dateChooser;
    private HeureField heureField;

    public GestionReservationsPanel() throws SQLException {
        try {
            this.reservationDao = new ReservationDao(); // Initialiser avec une instance valide de ReservationDao
            this.coursDao = new CoursDao(); 
            this.userDao = new UserDao(); 
        } catch (SQLException ex) {
            Logger.getLogger(GestionReservationsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Panel Nord
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Gestion des Réservations");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Panel Gauche: Formulaire d'ajout de réservation
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Composants du formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Date:"));
        dateChooser = new JDateChooser();
        formPanel.add(dateChooser);

        formPanel.add(new JLabel("Heure:"));
        heureField = new HeureField(); // Initialisation de HeureField
        formPanel.add(heureField);

        formPanel.add(new JLabel("Candidat:"));
        candidatComboBox = new JComboBox<>();
        formPanel.add(candidatComboBox);

        formPanel.add(new JLabel("Cours:"));
        coursComboBox = new JComboBox<>();
        formPanel.add(coursComboBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReservation();
                clearFields();
            }
        });
        formPanel.add(addButton);
        JButton clearButton = new JButton("Supprimer");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedReservation(); // Appel de la méthode pour supprimer l'examen sélectionné dans le tableau
                clearFields();
            }
            
        });
        formPanel.add(clearButton);
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Panel Droite: Tableau des réservations avec bouton Supprimer
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tableau des réservations
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Candidat");
        tableModel.addColumn("Cours");
        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les panels au panel principal
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Charger les données des candidats
        loadCandidatsData();

        // Charger les données des cours
        loadCoursData();

        // Charger les données des réservations
        loadReservationData();
    }

    private void addReservation() {
    Date date = dateChooser.getDate();
    String heure = heureField.getText();
    
    if (date != null && !heure.isEmpty()) {
        try {
            // Récupérer l'ID du candidat sélectionné dans le JComboBox
            String candidatInfo = (String) candidatComboBox.getSelectedItem();
            int candidatId = Integer.parseInt(candidatInfo.split(",")[0].trim());

            // Récupérer l'ID du cours sélectionné dans le JComboBox
            String coursInfo = (String) coursComboBox.getSelectedItem();
            int coursId = Integer.parseInt(coursInfo.split(",")[0].trim());

            // Créer un nouvel objet Reservation et l'ajouter à la base de données
            Reservation newReservation = new Reservation(0, candidatId, coursId, date, heure);
            reservationDao.save(newReservation);
            
            // Recharger les données du tableau
            loadReservationData();
            
            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, "Réservation ajoutée avec succès.", "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            handleError("Erreur lors de l'ajout de la réservation : " + ex.getMessage());
            ex.printStackTrace();
        }
    } else {
        // Afficher un message d'erreur si la date ou l'heure ne sont pas sélectionnées
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date et une heure.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}

// Méthode pour supprimer la réservation sélectionnée dans la table
private void deleteSelectedReservation() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
        int reservationId = (int) table.getValueAt(selectedRow, 0); // Récupère l'ID de la réservation dans la première colonne
        try {
            reservationDao.delete(reservationId); // Supprime la réservation de la base de données
            loadReservationData(); // Recharge les données du tableau
            JOptionPane.showMessageDialog(this, "Réservation supprimée avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            handleError("Erreur lors de la suppression de la réservation : " + ex.getMessage());
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
    }
}

    // Méthode pour charger les données des candidats dans le JComboBox
    private void loadCandidatsData() throws SQLException{
            // Remplacer "loadCandidatsData" avec le code pour charger les candidats depuis la base de données
            List<User> candidats = userDao.getAll(); // Supposons que vous avez une classe Candidat et une méthode getAll() dans votre DAO
            for (User candidat : candidats) {
                String candidatInfo = candidat.getId() + ", " + candidat.getNom() + " " + candidat.getPrenom();
                candidatComboBox.addItem(candidatInfo);
            }
       
    }

    // Méthode pour charger les données des cours dans le JComboBox
    private void loadCoursData() throws SQLException{

            // Remplacer "loadCoursData" avec le code pour charger les cours depuis la base de données
            List<Cours> coursList = coursDao.getAll(); // Supposons que vous avez une classe Cours et une méthode getAll() dans votre DAO
            for (Cours cours : coursList) {
                String coursInfo = cours.getId() + ", " + cours.getTitre();
                coursComboBox.addItem(coursInfo);
            }
       
    }

    // Méthode pour charger les données des réservations dans le tableau
    private void loadReservationData() {
        try {
            List<Reservation> reservations = reservationDao.getAll();
            updateTable(reservations);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données des réservations : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour le tableau avec les données des réservations
    private void updateTable(List<Reservation> reservations) {
        tableModel.setRowCount(0);
        for (Reservation reservation : reservations) {
            // Ajouter les informations dans le tableau
            tableModel.addRow(new Object[]{reservation.getId(), reservation.getDateReservation(), reservation.getHeureReservation(), reservation.getEleveId(), reservation.getCoursId()});
        }
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        dateChooser.setDate(null);
        heureField.resetTime(); // Réinitialise le champ d'heure
        candidatComboBox.setSelectedIndex(0);
        coursComboBox.setSelectedIndex(0);
    }

    // Méthode pour gérer les erreurs en affichant une boîte de dialogue
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public class HeureField extends JPanel {
        private JComboBox<String> hourComboBox;
        private JComboBox<String> minuteComboBox;

        public HeureField() {
            setLayout(new FlowLayout());

            hourComboBox = new JComboBox<>();
            minuteComboBox = new JComboBox<>();

            // Ajouter les heures de 00 à 23 dans le JComboBox d'heures
            for (int i = 0; i < 24; i++) {
                hourComboBox.addItem(String.format("%02d", i)); // Formatage pour avoir 2 chiffres
            }

            // Ajouter les minutes de 00 à 59 dans le JComboBox de minutes
            for (int i = 0; i < 60; i++) {
                minuteComboBox.addItem(String.format("%02d", i)); // Formatage pour avoir 2 chiffres
            }

            // Ajouter des écouteurs pour détecter les changements dans les combobox
            hourComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateTime();
                }
            });

            minuteComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateTime();
                }
            });

            add(hourComboBox);
            add(new JLabel(":"));
            add(minuteComboBox);
        }

        // Méthode pour mettre à jour le champ de texte avec l'heure sélectionnée
        private void updateTime() {
            String hour = (String) hourComboBox.getSelectedItem();
            String minute = (String) minuteComboBox.getSelectedItem();
            setText(hour + ":" + minute); // Met à jour le champ de texte avec l'heure sélectionnée
        }

        // Méthode pour définir l'heure dans les combobox
        public void setText(String time) {
            String[] parts = time.split(":");
            if (parts.length == 2) {
                String hour = parts[0];
                String minute = parts[1];
                hourComboBox.setSelectedItem(hour);
                minuteComboBox.setSelectedItem(minute);
            }
        }

        // Méthode pour obtenir l'heure sélectionnée
        public String getText() {
            String hour = (String) hourComboBox.getSelectedItem();
            String minute = (String) minuteComboBox.getSelectedItem();
            return hour + ":" + minute;
        }

        // Méthode pour réinitialiser l'heure à "00:00"
        public void resetTime() {
            hourComboBox.setSelectedIndex(-1);
            minuteComboBox.setSelectedIndex(-1);
        }
    }
}
