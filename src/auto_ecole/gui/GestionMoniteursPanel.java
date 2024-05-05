/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.gui;

/**
 *
 * @author Abdellatif
 */
import auto_ecole.database.MoniteursDao;

import auto_ecole.model.Moniteur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GestionMoniteursPanel extends JPanel {
  
      private DefaultTableModel tableModel;
    private JTable table;
    private MoniteursDao MoniteurDao;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;

    public GestionMoniteursPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        
       
         try {
            MoniteurDao = new MoniteursDao();
           
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
        JLabel label = new JLabel("Gestion Moniteurs");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(Color.PINK);
        //new Color(0, 191, 255)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon
        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Desktop\\Projet_J2EE\\Auto_Ecole\\src\\moniteur.png");
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

        JLabel addUserLabel = new JLabel("Ajouter un candidat :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(new Color(0, 191, 255));
        formPanel.add(addUserLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridy++;
        nomField = new JTextField(15);
        nomField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(nomField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridy++;
        prenomField = new JTextField(15);
        prenomField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(prenomField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Adresse:"), gbc);
        gbc.gridy++;
        adresseField = new JTextField(15);
        adresseField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(adresseField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Téléphone:"), gbc);
        gbc.gridy++;
        telephoneField = new JTextField(15);
        telephoneField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(telephoneField, gbc);


        // RoundedBorder
        formPanel.setBorder(new GestionMoniteursPanel.RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        addButton.setBorder(new GestionMoniteursPanel.RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMoniteur();
                clearFields();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        clearButton.setBorder(new GestionMoniteursPanel.RoundedBorder(10)); // Bordure arrondie
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

        // User Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Adresse");
        tableModel.addColumn("Téléphone");
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedMoniteur();
            }
        });
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.GRAY); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionMoniteursPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.GRAY); // Rouge clair
        deleteButton.setBorder(new GestionMoniteursPanel.RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        MoniteurDao.delete(userId);
                        loadMoniteurData();
                        clearFields();
                        JOptionPane.showMessageDialog(GestionMoniteursPanel.this, "Moniteur supprimé avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        handleError("Erreur lors de la suppression du moniteur : " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(GestionMoniteursPanel.this, "Veuillez sélectionner un moniteur à supprimer.", "Aucune sélection", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.GRAY); // Rouge clair
        modifyButton.setBorder(new GestionMoniteursPanel.RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyMoniteur();
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

        // Load user data
        loadMoniteurData();
    }

    // Method to add a new Moniteur
    private void addMoniteur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String telephone = telephoneField.getText();
        if (!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !telephone.isEmpty()) {
            try {
                Moniteur newMoniteur = new Moniteur(nom, prenom, adresse, telephone);
                MoniteurDao.save(newMoniteur);
                loadMoniteurData(); // Reload Moniteur data after addition
                // Clear the form fields
                nomField.setText("");
                prenomField.setText("");
                adresseField.setText("");
                telephoneField.setText("");
                
                JOptionPane.showMessageDialog(this, "Moniteur ajouté avec succès.", "Ajout réussi",
                JOptionPane.INFORMATION_MESSAGE);
    
            } catch (SQLException ex) {
                handleError("Erreur lors de l'ajout de moniteur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to modify the selected course
    private void modifyMoniteur() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int moniteurId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Moniteur selectedMoniteur = MoniteurDao.findMoniteurById(moniteurId);
                if (selectedMoniteur != null) {
                    
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String adresse = adresseField.getText();
                    String telephone = telephoneField.getText();

                    selectedMoniteur.setNom(nom);
                    selectedMoniteur.setPrenom(prenom);
                    selectedMoniteur.setAdresse(adresse);
                    selectedMoniteur.setTelephone(telephone);
                    
                   
                    MoniteurDao.update(selectedMoniteur);

                    loadMoniteurData();
                    JOptionPane.showMessageDialog(this, "Moniteur modifié avec succès.", "Modification réussie",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Le cmoniteur sélectionné n'existe pas.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                handleError("Erreur lors de la modification du moniteur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un moniteur à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    // Method to load Moniteur data into the table
    private void loadMoniteurData() {
        try {
            MoniteurDao = new MoniteursDao();
            java.util.List<Moniteur> Moniteurs = MoniteurDao.getAll();
            updateTable(Moniteurs);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void populateFieldsFromSelectedMoniteur() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            int columnIndex = table.getColumnModel().getColumnIndex("Nom");
            if (selectedRow != -1) {
                int moniteurId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Moniteur selectedMoniteur = MoniteurDao.findMoniteurById(moniteurId);
                    if (selectedMoniteur != null) {
                        // Populate the text fields with the selected course's information
                        nomField.setText(selectedMoniteur.getNom());
                        prenomField.setText(selectedMoniteur.getPrenom());
                        adresseField.setText(selectedMoniteur.getAdresse());
                        telephoneField.setText(selectedMoniteur.getTelephone());

                    }
                } catch (SQLException ex) {
                    handleError("Erreur lors de la récupération des données du cours : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    // Method to update the table with Moniteur data
    private void updateTable(java.util.List<Moniteur> Moniteurs) {
        tableModel.setRowCount(0); // Clear the existing table data
        for (Moniteur Moniteur : Moniteurs) {
            tableModel.addRow(new Object[]{Moniteur.getId(), Moniteur.getNom(), Moniteur.getPrenom(), Moniteur.getAdresse(), Moniteur.getTelephone()});
        }
    }

    private void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
    }

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
}