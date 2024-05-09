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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class GestionSeancesPanel extends JPanel {
   private DefaultTableModel tableModel;
    private JTable table;
    private SeancesDao seancesDao;
    private CoursDao coursDao;
    private JDateChooser dateChooser; // Utilisation de JDateChooser pour la sélection de la date
    private HeureField heureDebutField;
    private HeureField heureFinField;
    private JComboBox<String> coursComboBox;

    public GestionSeancesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        try {
            coursDao = new CoursDao();
           
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
        
        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 100));
        northPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        // Title Label with icon
        JLabel label = new JLabel("Gestion Séances");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(Color.red);
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon

        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Desktop\\Projet_J2EE\\Auto_Ecole\\src\\seance.png");

        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
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
        addUserLabel.setForeground(Color.ORANGE);
        formPanel.add(addUserLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        // Add Course Form Components

        gbc.gridy++;
        formPanel.add(new JLabel("Date :"), gbc);
        gbc.gridy++;
        dateChooser = new JDateChooser();
        JLabel labelForWidth = new JLabel();
        dateChooser.setPreferredSize(new Dimension(13 * labelForWidth.getFontMetrics(labelForWidth.getFont()).charWidth('W'), dateChooser.getPreferredSize().height));
        dateChooser.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateChooser, gbc);

        
        gbc.gridy++;
        formPanel.add(new JLabel("Heure Début:"), gbc);
        gbc.gridy++;
        heureDebutField = new GestionSeancesPanel.HeureField(); // Initialisation de HeureField
        heureDebutField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureDebutField, gbc);
        
        gbc.gridy++;
        formPanel.add(new JLabel("Heure Fin:"), gbc);
        gbc.gridy++;
        heureFinField = new GestionSeancesPanel.HeureField(); // Initialisation de HeureField
        heureFinField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureFinField, gbc);
        
        
        gbc.gridy++;
        formPanel.add(new JLabel("Cours ID:"), gbc);
        gbc.gridy++;
        coursComboBox = new JComboBox<>();
        coursComboBox.setPreferredSize(new Dimension(13 * labelForWidth.getFontMetrics(labelForWidth.getFont()).charWidth('W'), coursComboBox.getPreferredSize().height));
        coursComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        coursComboBox.setBackground(Color.WHITE);
        formPanel.add(coursComboBox, gbc);

        
        
        
        // RoundedBorder
        formPanel.setBorder(new GestionSeancesPanel.RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.BLUE);
        addButton.setBackground(Color.ORANGE); // Rouge clair
        addButton.setBorder(new GestionSeancesPanel.RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSeance();
                clearFieldsAndSelection();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(Color.ORANGE); // Rouge clair
        clearButton.setBorder(new GestionSeancesPanel.RoundedBorder(10)); // Bordure arrondie
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFieldsAndSelection();
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
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.blue); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionSeancesPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.blue); // Rouge clair
        deleteButton.setBorder(new GestionSeancesPanel.RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedSeance();
                clearFieldsAndSelection();
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.blue); // Rouge clair
        modifyButton.setBorder(new GestionSeancesPanel.RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifySeance();
                clearFieldsAndSelection();
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
        //heureDebutField.setText("");
        //heureFinField.setText("");
        heureDebutField.resetTime(); // Réinitialise le champ d'heure
        heureFinField.resetTime(); 
        table.clearSelection();
        dateChooser.setDate(null);
        coursComboBox.setSelectedIndex(0); // Sélectionne le premier élément dans le JComboBox de véhicules

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
    
   
    private void modifySeance() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow != -1) {
        Date selectedDate = dateChooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime()); // Convert java.util.Date to java.sql.Date
        String heureDebut = heureDebutField.getText();
        String heureFin = heureFinField.getText();

        if (sqlDate != null && !heureDebut.isEmpty() && !heureFin.isEmpty()) {
            
                int seanceId = (int) tableModel.getValueAt(selectedRow, 0); // Get the ID of the selected Seance
                try {
                    // Get the ID of the selected cours from the combo box selection
                    String selectedCours = (String) coursComboBox.getSelectedItem();
                    int coursId = Integer.parseInt(selectedCours.split(" - ")[0]);

                    Seance modifiedSeance = new Seance(seanceId, coursId, sqlDate, heureDebut, heureFin);
                    seancesDao.update(modifiedSeance); // Update the Seance in the database
                    loadSeanceData(); // Reload Seance data after modification
                    clearFieldsAndSelection(); // Clear text fields and selection in the table after modification
                    JOptionPane.showMessageDialog(this, "Séance modifiée avec succès.", "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    handleError("Erreur lors de la modification de la séance : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une séance à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
     
    public static class HeureField extends JPanel {

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
    
   
     
}









