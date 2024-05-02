package auto_ecole.gui;
import auto_ecole.database.CoursDao;
import auto_ecole.database.SeancesDao;
import auto_ecole.model.Cours;
import auto_ecole.model.Seance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
    import com.toedter.calendar.JDateChooser; // Import du composant JDateChooser

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GestionSeancesPanel extends JPanel {
   private DefaultTableModel tableModel;
    private JTable table;
    private SeancesDao seancesDao;
    private CoursDao coursDao;
    private JDateChooser dateChooser; // Utilisation de JDateChooser pour la sélection de la date
    private JTextField heureDebutField;
    private JTextField heureFinField;
    private JComboBox<String> coursComboBox;

    public GestionSeancesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Panel Nord
        JPanel northPanel=new JPanel(new BorderLayout());
        JLabel label = new JLabel("Gestion des Séances");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Panel Gauche: Formulaire d'ajout de séance
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Composants du formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Date:"));

        // Utilisation de JDateChooser pour la sélection de la date
        dateChooser = new JDateChooser();
        formPanel.add(dateChooser);

        formPanel.add(new JLabel("Heure de début:"));
        heureDebutField = new JTextField();
        formPanel.add(heureDebutField);
        formPanel.add(new JLabel("Heure de fin:"));
        heureFinField = new JTextField();
        formPanel.add(heureFinField);
        formPanel.add(new JLabel("Cours:"));
        coursComboBox = new JComboBox<>();
        formPanel.add(coursComboBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSeance();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(addButton);

        JButton clearButton = new JButton("Effacer");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedSeance();
            }
        });
        formPanel.add(clearButton);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Panel Droite: Tableau des séances avec boutons Modifier et Supprimer
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tableau des séances
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Date");
        tableModel.addColumn("Heure de début");
        tableModel.addColumn("Heure de fin");
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                populateFieldsFromSelectedSeance();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les panels au panel principal
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Charger les données des séances
        loadSeanceData();
        loadCoursData();
    }

    // Inside the addSeance() method
private void addSeance() {
    Date selectedDate = dateChooser.getDate();
    java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime()); // Convert java.util.Date to java.sql.Date
    String heureDebut = heureDebutField.getText();
    String heureFin = heureFinField.getText();

    if (sqlDate != null && !heureDebut.isEmpty() && !heureFin.isEmpty()) {
        try {
            // Récupérer l'ID du cours à partir de la sélection dans la liste déroulante
            String selectedCours = (String) coursComboBox.getSelectedItem();
            int coursId = Integer.parseInt(selectedCours.split(" - ")[0]);

            Seance newSeance = new Seance(coursId, sqlDate, heureDebut, heureFin);
            seancesDao.save(newSeance);
            loadSeanceData();
            try {
    // Utilisation du look and feel par défaut du système
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    // Définition des couleurs pour la boîte de dialogue JOptionPane
    UIManager.put("OptionPane.background", Color.WHITE); // Couleur de fond
    UIManager.put("Panel.background", Color.WHITE); // Couleur de fond du panel
    UIManager.put("OptionPane.messageForeground", Color.BLUE); // Couleur du texte

} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
    e.printStackTrace();
}
            
            JOptionPane.showMessageDialog(this, "Séance ajoutée avec succès.", "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            handleError("Erreur lors de l'ajout de la séance : " + ex.getMessage());
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}


    // Méthode pour charger les données des séances dans le tableau
    private void loadSeanceData() {
        try {
            seancesDao = new SeancesDao();
            List<Seance> seances = seancesDao.getAll();
            updateTable(seances);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données des séances : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour le tableau avec les données des séances
    private void updateTable(List<Seance> seances) {
        tableModel.setRowCount(0);
        for (Seance seance : seances) {
            tableModel.addRow(new Object[]{seance.getId(), seance.getDateSeance(), seance.getHeureDebut(), seance.getHeureFin()});
        }
    }

    // Méthode pour afficher les données de la séance sélectionnée dans les champs de texte
    private void populateFieldsFromSelectedSeance() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Date date = (Date) tableModel.getValueAt(selectedRow, 1);
                String heureDebut = (String) tableModel.getValueAt(selectedRow, 2);
                String heureFin = (String) tableModel.getValueAt(selectedRow, 3);
                
              //  dateField.setText(date);
                heureDebutField.setText(heureDebut);
                heureFinField.setText(heureFin);
                dateChooser.setDate(date);
                
                coursComboBox.setSelectedItem(1);
                
            }
        }
    }

    // Méthode pour effacer les champs de texte et la sélection dans le tableau
    private void clearFieldsAndSelection() {
       // dateField.setText("");
        heureDebutField.setText("");
        heureFinField.setText("");
        table.clearSelection();
    }

    // Méthode pour gérer les erreurs en affichant une boîte de dialogue
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Méthode pour charger les données des cours dans la liste déroulante
    private void loadCoursData() {
        try {
            coursDao = new CoursDao();
            List<Cours> coursList = coursDao.getAll();
            for (Cours cours : coursList) {
                coursComboBox.addItem(cours.getId() + " - " + cours.getTitre());
            }
        } catch (SQLException ex) {
            handleError("Erreur lors du chargement des cours : " + ex.getMessage());
            ex.printStackTrace();
        }
        
        
       
    }
    // Méthode pour supprimer l'élément sélectionné de la table
private void deleteSelectedSeance() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cette séance ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            int seanceId = (int) tableModel.getValueAt(selectedRow, 0); // Obtenez l'ID de la séance à supprimer
            try {
                // Supprimer la séance de la base de données
                seancesDao.delete(seanceId);
                // Recharger les données des séances après suppression
                loadSeanceData();
                // Effacer les champs de texte et la sélection dans le tableau après suppression
                clearFieldsAndSelection();
                JOptionPane.showMessageDialog(this, "Séance supprimée avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression de la séance : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une séance à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}





}

