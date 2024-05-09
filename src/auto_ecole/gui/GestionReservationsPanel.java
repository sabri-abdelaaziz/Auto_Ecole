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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        
        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 100));
        northPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        // Title Label with icon
        JLabel label = new JLabel("Gestion Résérvations");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(Color.pink);
        //new Color(0, 191, 255)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon


        ImageIcon icon = new ImageIcon("./src/reservation.png");

        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(resizedIcon);

        // Adding icon and title label to the title panel
        titlePanel.add(iconLabel);
        titlePanel.add(label);
        northPanel.add(titlePanel, BorderLayout.CENTER);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        // Left Panel: Add User Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
        leftPanel.setBackground(Color.WHITE);

        // Add User Form Components
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.white);
        formPanel.setBorder(new CompoundBorder(new LineBorder(new Color(178, 34, 34)), new EmptyBorder(10, 10, 10, 10)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel addUserLabel = new JLabel("Ajouter un cours :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(Color.red);
        formPanel.add(addUserLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        // Add Course Form Components

        gbc.gridy++;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridy++;
        dateChooser = new JDateChooser();
        JLabel labelForWidth = new JLabel();
        dateChooser.setPreferredSize(new Dimension(13 * labelForWidth.getFontMetrics(labelForWidth.getFont()).charWidth('W'), dateChooser.getPreferredSize().height));
        dateChooser.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateChooser, gbc);


        gbc.gridy++;
        formPanel.add(new JLabel("Heure Début:"), gbc);
        gbc.gridy++;
        heureField = new GestionReservationsPanel.HeureField(); // Initialisation de HeureField
        heureField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Candidats ID:"), gbc);
        gbc.gridy++;
        candidatComboBox = new JComboBox<>();
        candidatComboBox.setPreferredSize(new Dimension(13 * heureField.getFontMetrics(heureField.getFont()).charWidth('W'), candidatComboBox.getPreferredSize().height));
        candidatComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        candidatComboBox.setBackground(Color.WHITE);
        formPanel.add(candidatComboBox, gbc);
        
        gbc.gridy++;
        formPanel.add(new JLabel("Cours ID:"), gbc);
        gbc.gridy++;
        coursComboBox = new JComboBox<>();
        coursComboBox.setPreferredSize(new Dimension(13 * heureField.getFontMetrics(heureField.getFont()).charWidth('W'), coursComboBox.getPreferredSize().height));
        coursComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        coursComboBox.setBackground(Color.WHITE);
        formPanel.add(coursComboBox, gbc);

      // RoundedBorder
        formPanel.setBorder(new GestionReservationsPanel.RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.red); // Rouge clair
        addButton.setBorder(new GestionReservationsPanel.RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReservation();
                clearFields();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(Color.red); // Rouge clair
        clearButton.setBorder(new GestionReservationsPanel.RoundedBorder(10)); // Bordure arrondie
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Add buttons to form panel with GridBagLayout
        gbc.gridy++;
        gbc.gridx++;
        gbc.insets = new Insets(5, 10, 5, 10); // Ajout d'un espace horizontal entre les boutons
        formPanel.add(addButton, gbc);

        // Add horizontal space between buttons
        gbc.gridx++;
        gbc.insets = new Insets(5, 10, 5, 10); // Ajout d'un espace horizontal entre les boutons
        formPanel.add(clearButton, gbc);

        leftPanel.add(formPanel, BorderLayout.NORTH);


        // Right Panel: User Table with Modify and Delete Buttons

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
        rightPanel.setBackground(Color.WHITE);

        // Tableau des réservations
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Candidat");
        tableModel.addColumn("Cours");
        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedReservation();
            }
        });
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.green); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionReservationsPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.green); // Rouge clair
        deleteButton.setBorder(new GestionReservationsPanel.RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedReservation();
                clearFields();
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.green); // Rouge clair
        modifyButton.setBorder(new GestionReservationsPanel.RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyReservation();
                clearFields();
            }
        });

        // Ajouter les boutons "Supprimer" et "Modifier" au rightPanel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        GridBagConstraints leftPanelConstraints = new GridBagConstraints();
        leftPanelConstraints.gridx = 0;
        leftPanelConstraints.gridy = 0;
        leftPanelConstraints.weightx = 0.5;
        leftPanelConstraints.fill = GridBagConstraints.BOTH;
        leftPanelConstraints.insets = new Insets(10, 10, 10, 5);

        mainPanel.add(leftPanel, leftPanelConstraints);

        GridBagConstraints rightPanelConstraints = new GridBagConstraints();
        rightPanelConstraints.gridx = 1;
        rightPanelConstraints.gridy = 0;
        rightPanelConstraints.weightx = 0.5;
        rightPanelConstraints.fill = GridBagConstraints.BOTH;
        rightPanelConstraints.insets = new Insets(10, 10, 10, 5);

        mainPanel.add(rightPanel, rightPanelConstraints);

        add(northPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

       

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

    public class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
    
    public class HeureField extends JPanel {

        private JComboBox<String> hourComboBox;
        private JComboBox<String> minuteComboBox;

        public HeureField(){
            setLayout(new FlowLayout());
            setBackground(Color.WHITE);
            
            hourComboBox = new JComboBox<>();
            minuteComboBox = new JComboBox<>();
            hourComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            hourComboBox.setBackground(Color.WHITE);
            minuteComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            minuteComboBox.setBackground(Color.WHITE);
            
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
//            setText(hour + ":" + minute); // Met à jour le champ de texte avec l'heure sélectionnée
        }

        // Méthode pour définir l'heure dans les combobox
        public void setText(String time) {
            String[] parts = time.split(":");
            if (parts.length == 2) {
                String hour = parts[0];
                String minute = parts[1];
                hourComboBox.setSelectedItem((String) hour);
                minuteComboBox.setSelectedItem((String) minute);
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
            hourComboBox.setSelectedIndex(-1); // Désélectionne toute sélection d'heure
            minuteComboBox.setSelectedIndex(-1); // Désélectionne toute sélection de minute

        }
    }
    
   private void modifyReservation() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
        int reservationId = (int) table.getValueAt(selectedRow, 0);
        try {
            // Récupérer les nouvelles valeurs des champs
            Date newDate = dateChooser.getDate();
            String newHeure = heureField.getText();
            String candidatInfo = (String) candidatComboBox.getSelectedItem();
            int newCandidatId = Integer.parseInt(candidatInfo.split(",")[0].trim());
            String coursInfo = (String) coursComboBox.getSelectedItem();
            int newCoursId = Integer.parseInt(coursInfo.split(",")[0].trim());

            // Créer un objet Reservation avec les nouvelles valeurs
            Reservation updatedReservation = new Reservation(reservationId, newCandidatId, newCoursId, newDate, newHeure);

            // Mettre à jour la réservation dans la base de données
            reservationDao.update(updatedReservation);

            // Recharger les données du tableau
            loadReservationData();

            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, "Réservation modifiée avec succès.", "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | SQLException ex) {
            handleError("Erreur lors de la modification de la réservation : " + ex.getMessage());
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
    }
}



    
    // Méthode pour remplir les champs avec les données de la réservation sélectionnée
private void populateFieldsFromSelectedReservation() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
        int reservationId = (int) table.getValueAt(selectedRow, 0); // Récupère l'ID de la réservation dans la première colonne
        try {
            // Récupérer les détails de la réservation à partir de son ID
            Reservation selectedReservation = reservationDao.getReservationById(reservationId);
            // Remplir les champs avec les données de la réservation sélectionnée
            dateChooser.setDate(selectedReservation.getDateReservation());
            heureField.setText(selectedReservation.getHeureReservation());

            // Sélectionner le candidat correspondant dans le JComboBox
            User candidat = userDao.getUserById(selectedReservation.getEleveId());
            candidatComboBox.setSelectedItem(selectedReservation.getEleveId() + ", " + candidat.getNom() + " " + candidat.getPrenom());

            // Sélectionner le cours correspondant dans le JComboBox
            Cours cours = coursDao.getCoursById(selectedReservation.getCoursId());
            coursComboBox.setSelectedItem(selectedReservation.getCoursId() + ", " + cours.getTitre());

        } catch (SQLException ex) {
            handleError("Erreur lors de la récupération des données de la réservation sélectionnée : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}





}
