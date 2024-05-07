
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.gui;

/**
 *
 * @author Abdellatif
 */
import javax.swing.*;
import java.awt.*;

import auto_ecole.database.ExamenDao;
import auto_ecole.database.MoniteursDao;
import auto_ecole.database.VehiculeDao;
import auto_ecole.model.Examen;
import auto_ecole.model.Moniteur;
import auto_ecole.model.Vehicule;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class GestionExamensPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private ExamenDao examenDao;
    private MoniteursDao moniteurDao;
    private VehiculeDao vehiculeDao;
    private JDateChooser dateChooser;
    private HeureField heureField; // Utilisation de la classe HeureField personnalisée
    private JComboBox<String> vehiculeComboBox;
    private JComboBox<String> instructeurComboBox;

    public GestionExamensPanel() throws SQLException {
        try {
            this.examenDao = new ExamenDao(); // Initialisez avec une instance valide de ExamenDao
            this.moniteurDao = new MoniteursDao(); // Initialisez avec une instance valide de MoniteursDao
            this.vehiculeDao = new VehiculeDao(); // Initialisez avec une instance valide de VehiculeDao
        } catch (SQLException ex) {
            Logger.getLogger(GestionExamensPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 100));
        northPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        // Title Label with icon
        JLabel label = new JLabel("Gestion des Examens");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 191, 255));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon
        ImageIcon icon = new ImageIcon("./src/candidat.png");
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(resizedIcon);

        // Adding icon and title label to the title panel
        titlePanel.add(iconLabel);
        titlePanel.add(label);
        northPanel.add(titlePanel, BorderLayout.CENTER);

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

        JLabel addUserLabel = new JLabel("Ajouter un candidat :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(new Color(178, 34, 34));
        formPanel.add(addUserLabel, gbc);

        // Composants du formulaire
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche

        gbc.gridy++;
        formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridy++;
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        dateChooser.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateChooser, gbc);
        formPanel.add(dateChooser, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Heure:"), gbc);
        gbc.gridy++;
        heureField = new HeureField(); // Initialisation de HeureField
        heureField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Véhicule:"), gbc);
        gbc.gridy++;
        vehiculeComboBox = new JComboBox<>();
        vehiculeComboBox.setPreferredSize(new Dimension(150, vehiculeComboBox.getPreferredSize().height));
        vehiculeComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        vehiculeComboBox.setBackground(Color.WHITE);
        formPanel.add(vehiculeComboBox, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Instructeur:"), gbc);
        gbc.gridy++;
        instructeurComboBox = new JComboBox<>();
        instructeurComboBox.setPreferredSize(new Dimension(150, instructeurComboBox.getPreferredSize().height));
        instructeurComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        instructeurComboBox.setBackground(Color.WHITE);
        formPanel.add(instructeurComboBox, gbc);

        // RoundedBorder
        formPanel.setBorder(new GestionExamensPanel.RoundedBorder(20));

        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        addButton.setBorder(new GestionExamensPanel.RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExamen();
                clearFields();
            }
        });

        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        clearButton.setBorder(new GestionExamensPanel.RoundedBorder(10)); // Bordure arrondie
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

        // Tableau des examens
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        tableModel.addColumn("Véhicule");
        tableModel.addColumn("Instructeur");
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedExeman();
            }
        });
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.pink); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionExamensPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.pink); // Rouge clair
        deleteButton.setBorder(new GestionExamensPanel.RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedExamen(); // Appel de la méthode pour supprimer l'examen sélectionné dans le tableau
                clearFields();
            }

        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.pink); // Rouge clair
        modifyButton.setBorder(new GestionExamensPanel.RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyExamen();
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

        // Charger les données des examens, des véhicules et des moniteurs
        loadExamenData();
        loadVehiculesData();
        loadMoniteursData();
    }

    private void addExamen() {
        // Récupérer la date et l'heure
        Date date = dateChooser.getDate();
        String heure = heureField.getText();

        if (date != null && !heure.isEmpty()) {
            try {
                // Récupérer le véhicule sélectionné dans le JComboBox
                String vehiculeInfo = (String) vehiculeComboBox.getSelectedItem();
                // Diviser le nom du véhicule pour extraire l'ID
                int vehiculeId = Integer.parseInt(vehiculeInfo.split(",")[0].trim());

                // Récupérer le moniteur sélectionné dans le JComboBox
                String moniteurInfo = (String) instructeurComboBox.getSelectedItem();
                // Diviser le nom du moniteur pour extraire l'ID
                int instructeurId = Integer.parseInt(moniteurInfo.split(",")[0].trim());

                // Créer un nouvel objet Examen et l'ajouter à la base de données
                Examen newExamen = new Examen(0, date, heure, vehiculeId, instructeurId);
                examenDao.save(newExamen);
                // Recharger les données du tableau
                loadExamenData();
                // Afficher un message de succès
                JOptionPane.showMessageDialog(this, "Examen ajouté avec succès.", "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException | SQLException ex) {
                handleError("Erreur lors de l'ajout de l'examen : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur si les champs requis ne sont pas remplis
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour supprimer l'examen sélectionné dans la table
    private void deleteSelectedExamen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
            int examenId = (int) table.getValueAt(selectedRow, 0); // Récupère l'ID de l'examen dans la première colonne
            try {
                examenDao = new ExamenDao();
                examenDao.delete(examenId); // Supprime l'examen de la base de données
                loadExamenData(); // Recharge les données du tableau
                JOptionPane.showMessageDialog(this, "Examen supprimé avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression de l'examen : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un examen à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Méthode pour charger les données des véhicules dans le JComboBox
    private void loadVehiculesData() {
        try {
            List<Vehicule> vehicules = vehiculeDao.getAll();
            for (Vehicule vehicule : vehicules) {
                String nomVehicule = vehicule.getId() + ", " + vehicule.getMarque() + ", " + vehicule.getModele();
                vehiculeComboBox.addItem(nomVehicule);
            }
        } catch (SQLException ex) {
            handleError("Erreur lors du chargement des véhicules : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Méthode pour charger les données des moniteurs dans le JComboBox
    private void loadMoniteursData() throws SQLException {
        List<Moniteur> moniteurs = moniteurDao.getAll();
        for (Moniteur moniteur : moniteurs) {
            String nomPrenom = moniteur.getId() + ", " + moniteur.getNom() + " " + moniteur.getPrenom();
            instructeurComboBox.addItem(nomPrenom);
        }
    }

    // Méthode pour charger les données des examens dans le tableau
    private void loadExamenData() {
        try {
            List<Examen> examens = examenDao.getAll();
            updateTable(examens);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données des examens : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour le tableau avec les données des examens
    private void updateTable(List<Examen> examens) {
        tableModel.setRowCount(0);
        for (Examen examen : examens) {
            try {
                // Récupérer les informations du véhicule à partir de son ID
                Vehicule vehicule = vehiculeDao.getById(examen.getVehiculeId());
                String vehiculeInfo = vehicule.getId() + ", " + vehicule.getMarque() + ", " + vehicule.getModele();

                // Récupérer les informations du moniteur à partir de son ID
                Moniteur moniteur = moniteurDao.getById(examen.getInstructeurId());
                String moniteurInfo = moniteur.getId() + ", " + moniteur.getNom() + " " + moniteur.getPrenom();

                // Ajouter les informations dans le tableau
                tableModel.addRow(new Object[]{examen.getId(), examen.getDateExamen(), examen.getHeureExamen(), vehiculeInfo, moniteurInfo});
            } catch (SQLException ex) {
                handleError("Erreur lors de la récupération des données du véhicule ou du moniteur : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        dateChooser.setDate(null);
        heureField.resetTime(); // Réinitialise le champ d'heure
        vehiculeComboBox.setSelectedIndex(0); // Sélectionne le premier élément dans le JComboBox de véhicules
        instructeurComboBox.setSelectedIndex(0); // Sélectionne le premier élément dans le JComboBox de moniteurs
        table.clearSelection();

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

    // Méthode pour remplir les champs avec les données de l'examen sélectionné
    private void populateFieldsFromSelectedExeman() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
            int examenId = (int) table.getValueAt(selectedRow, 0); // Récupère l'ID de l'examen dans la première colonne
            try {
                // Récupérer les détails de l'examen à partir de son ID
                Examen selectedExamen = examenDao.find(examenId);
                // Remplir les champs avec les données de l'examen sélectionné
                dateChooser.setDate(selectedExamen.getDateExamen());
                heureField.setText(selectedExamen.getHeureExamen());

                // Sélectionner le véhicule correspondant dans le JComboBox
                Vehicule vehicule = vehiculeDao.getById(selectedExamen.getVehiculeId());
                vehiculeComboBox.setSelectedItem(selectedExamen.getVehiculeId() + ", " + vehicule.getMarque() + ", " + vehicule.getModele());

                // Sélectionner l'instructeur correspondant dans le JComboBox
                Moniteur moniteur = moniteurDao.getById(selectedExamen.getInstructeurId());
                instructeurComboBox.setSelectedItem(selectedExamen.getInstructeurId() + ", " + moniteur.getNom() + " " + moniteur.getPrenom());
                

            } catch (SQLException ex) {
                handleError("Erreur lors de la récupération des données de l'examen sélectionné : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

// Méthode pour modifier l'examen sélectionné
    private void modifyExamen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // Vérifie si une ligne est sélectionnée
            int examenId = (int) table.getValueAt(selectedRow, 0); // Récupère l'ID de l'examen dans la première colonne
            try {
                // Récupérer les données des champs
                Date date = dateChooser.getDate();
                String heure = heureField.getText();

                // Récupérer le véhicule sélectionné dans le JComboBox
                String vehiculeInfo = (String) vehiculeComboBox.getSelectedItem();
                int vehiculeId = Integer.parseInt(vehiculeInfo.split(",")[0].trim());

                // Récupérer l'instructeur sélectionné dans le JComboBox
                String instructeurInfo = (String) instructeurComboBox.getSelectedItem();
                int instructeurId = Integer.parseInt(instructeurInfo.split(",")[0].trim());

                // Créer un objet Examen avec les données modifiées
                Examen modifiedExamen = new Examen(examenId, date, heure, vehiculeId, instructeurId);
                // Mettre à jour l'examen dans la base de données
                examenDao = new ExamenDao();
                examenDao.update(modifiedExamen);
                // Recharger les données du tableau
                loadExamenData();
                // Afficher un message de succès
                JOptionPane.showMessageDialog(this, "Examen modifié avec succès.", "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException | SQLException ex) {
                handleError("Erreur lors de la modification de l'examen : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un examen à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
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

}
