
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

        // Panel Nord
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Gestion des Examens");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Panel Gauche: Formulaire d'ajout d'examen
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

        formPanel.add(new JLabel("Véhicule:"));
        vehiculeComboBox = new JComboBox<>();
        formPanel.add(vehiculeComboBox);

        formPanel.add(new JLabel("Instructeur:"));
        instructeurComboBox = new JComboBox<>();
        formPanel.add(instructeurComboBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExamen();
                clearFields();
            }
        });
        formPanel.add(addButton);

        JButton clearButton = new JButton("Supprimer");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedExamen(); // Appel de la méthode pour supprimer l'examen sélectionné dans le tableau
                clearFields();
            }
            
        });
        formPanel.add(clearButton);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Panel Droite: Tableau des examens avec boutons Modifier et Supprimer
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tableau des examens
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Date");
        tableModel.addColumn("Heure");
        tableModel.addColumn("Véhicule");
        tableModel.addColumn("Instructeur");
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table); // Ajout du JScrollPane
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les panels au panel principal
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

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
            String nomVehicule = vehicule.getId() +", " +vehicule.getMarque() + ", " + vehicule.getModele();
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
            String nomPrenom = moniteur.getId() + ", " +moniteur.getNom() + " " + moniteur.getPrenom();
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
            String vehiculeInfo = vehicule.getId()+ ", " + vehicule.getMarque() + ", " + vehicule.getModele();

            // Récupérer les informations du moniteur à partir de son ID
            Moniteur moniteur = moniteurDao.getById(examen.getInstructeurId());
            String moniteurInfo =moniteur.getId() + ", " + moniteur.getNom() + " " + moniteur.getPrenom();

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
       hourComboBox.setSelectedIndex(-1); // Désélectionne toute sélection d'heure
        minuteComboBox.setSelectedIndex(-1); // Désélectionne toute sélection de minute

    }
}

}