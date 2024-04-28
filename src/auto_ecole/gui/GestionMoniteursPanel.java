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
import javax.swing.border.EmptyBorder;
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

        //top panel 
        JPanel northPanel=new JPanel();
        
        
        
        
         northPanel.setLayout(new BorderLayout());
       northPanel.setBackground(new Color(255, 255, 255));
       
         try {
            MoniteurDao = new MoniteursDao();
            int nbrMoniteurs = MoniteurDao.calculMoniteurs();
             JLabel logoLabel = new JLabel("NBR Moniteurs :"+nbrMoniteurs);
         logoLabel.setForeground(Color.red);
        northPanel.add(logoLabel, BorderLayout.WEST);
            
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
       
       
       

    
        // Create and add the welcome label
        JLabel welcomeLabel = new JLabel(" La Gestion des Moniteurs");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
       
        welcomeLabel.setForeground(Color.red);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
           welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set text alignment to center
      
        northPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Create and add the time label
        JLabel timeLabel = new JLabel("-_$");
       
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        timeLabel.setForeground(Color.red);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        northPanel.add(timeLabel, BorderLayout.EAST);
        
        
        
        
        
        // Left Panel: Add Moniteur Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add Moniteur Form Components
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom:"));
        prenomField = new JTextField();
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Adresse:"));
        adresseField = new JTextField();
        formPanel.add(adresseField);
        formPanel.add(new JLabel("Téléphone:"));
        telephoneField = new JTextField();
        formPanel.add(telephoneField);
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMoniteur();
            }
        });
        formPanel.add(addButton);
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Right Panel: Moniteur Table with Modify and Delete Buttons
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Moniteur Table
        tableModel = new DefaultTableModel();
        
    

        tableModel.addColumn("ID");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Adresse");
        tableModel.addColumn("Téléphone");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Modify and Delete Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle modify action
            }
        });
        buttonPanel.add(modifyButton);
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle delete action
            }
        });
        buttonPanel.add(deleteButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add Left and Right Panels to Main Panel
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(northPanel,BorderLayout.NORTH);

        
            // Customizing the appearance of the table (moved after table initialization)
    table.getTableHeader().setBackground(Color.GREEN);
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID column

     // À l'intérieur du constructeur GestionMoniteursPanel()

// Ajouter un ListSelectionListener pour détecter la sélection d'une ligne dans la table
table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Vérifier si une ligne est sélectionnée
        if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
            // Activer le bouton "Supprimer" si une ligne est sélectionnée
            deleteButton.setEnabled(true);
        } else {
            // Désactiver le bouton "Supprimer" si aucune ligne n'est sélectionnée
            deleteButton.setEnabled(false);
        }
    }
});

// Ajouter un ActionListener pour le bouton "Supprimer"
deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Récupérer l'index de la ligne sélectionnée
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer l'ID de l'élément sélectionné dans la table
            int id = (int) table.getValueAt(selectedRow, 0); // Supposant que l'ID est dans la première colonne
            // Appeler la méthode de votre DAO pour supprimer l'élément par son ID
            try {
                
                
                MoniteurDao.deleteById(id);
                // Recharger les données après la suppression
                loadMoniteurData();
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression de l'élément : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
});   
        
        
        // Load Moniteur data
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
            } catch (SQLException ex) {
                handleError("Erreur lors de l'ajout de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
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

    // Method to update the table with Moniteur data
    private void updateTable(java.util.List<Moniteur> Moniteurs) {
        tableModel.setRowCount(0); // Clear the existing table data
        for (Moniteur Moniteur : Moniteurs) {
            tableModel.addRow(new Object[]{Moniteur.getId(), Moniteur.getNom(), Moniteur.getPrenom(), Moniteur.getAdresse(), Moniteur.getTelephone()});
        }
    }

    // Method to handle errors by displaying a message dialog
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}