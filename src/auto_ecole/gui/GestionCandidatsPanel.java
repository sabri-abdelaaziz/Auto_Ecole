package auto_ecole.gui;

import auto_ecole.database.UserDao;
import auto_ecole.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionCandidatsPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
<<<<<<< HEAD
    private JLabel nbrLabel;
=======
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
    private UserDao userDao;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;

    public GestionCandidatsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
<<<<<<< HEAD

        // top panel
        JPanel northPanel = new JPanel(new BorderLayout());

        nbrLabel = new JLabel("Nbr Candidats : " + getNbrCandidats() + "  ");
        nbrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        northPanel.add(nbrLabel, BorderLayout.EAST);

=======
        
        //top panel 
        JPanel northPanel=new JPanel(new BorderLayout());
         
        JLabel nbrLabel = new JLabel("Nbr Candidats : " + getNbrCandidats() + "  ");
        nbrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        northPanel.add(nbrLabel, BorderLayout.EAST);
        
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        JLabel label = new JLabel("Gestion Candidats");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Left Panel: Add User Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add User Form Components
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
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
<<<<<<< HEAD

=======
        
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(addButton);
        // New button to clear text fields and unselect the user
        JButton clearButton = new JButton("Effacer");
        clearButton.addActionListener(new ActionListener() {
<<<<<<< HEAD
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFieldsAndSelection();
            }
=======
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteUser();
            clearFieldsAndSelection();
        }
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        });
        formPanel.add(clearButton);
        // Modify and Delete Buttons
        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyUser();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(modifyButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
<<<<<<< HEAD
                clearFieldsAndSelection();
            }
        });
        formPanel.add(deleteButton);

=======
            }
        });
        formPanel.add(deleteButton);
        
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Right Panel: User Table with Modify and Delete Buttons
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // User Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Adresse");
        tableModel.addColumn("Téléphone");
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
<<<<<<< HEAD
            @Override
=======
         @Override
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedUser();
<<<<<<< HEAD
            }
=======
            }   
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        });

        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Panels to Main Panel
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Load user data
        loadUserData();
    }

    // Method to add a new user
    private void addUser() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String telephone = telephoneField.getText();
        if (!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !telephone.isEmpty()) {
            try {
                User newUser = new User(nom, prenom, adresse, telephone);
                userDao.save(newUser);
<<<<<<< HEAD
                loadUserData();
                JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès.", "Ajout réussi",
                        JOptionPane.INFORMATION_MESSAGE);
=======
                loadUserData(); 
                JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès.", "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
            } catch (SQLException ex) {
                handleError("Erreur lors de l'ajout de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
<<<<<<< HEAD
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to modify the selected user
=======
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 // Method to modify the selected user
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
    private void modifyUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            // Fetch the user from the database based on the ID
            try {
                User selectedUser = userDao.find(userId);
                if (selectedUser != null) {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String adresse = adresseField.getText();
                    String telephone = telephoneField.getText();
<<<<<<< HEAD

=======
                    
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
                    selectedUser.setNom(nom);
                    selectedUser.setPrenom(prenom);
                    selectedUser.setAdresse(adresse);
                    selectedUser.setTelephone(telephone);
<<<<<<< HEAD

                    userDao.update(selectedUser);

                    loadUserData();
                    JOptionPane.showMessageDialog(this, "Utilisateur modifié avec succès.", "Modification réussie",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "L'utilisateur sélectionné n'existe pas.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
=======
                    
                    userDao.update(selectedUser);
                    
                    loadUserData();
                    JOptionPane.showMessageDialog(this, "Utilisateur modifié avec succès.", "Modification réussie", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this, "L'utilisateur sélectionné n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
                }
            } catch (SQLException ex) {
                handleError("Erreur lors de la modification de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
<<<<<<< HEAD
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
=======
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        }
    }

    // Method to delete the selected user
    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            // Delete the user from the database based on the ID
            try {
                userDao.delete(userId);
                // Remove the user from the table model
                tableModel.removeRow(selectedRow);
<<<<<<< HEAD
                JOptionPane.showMessageDialog(this, "Utilisateur supprimer avec succès.", "Suppression réussie",
                        JOptionPane.INFORMATION_MESSAGE);
=======
                JOptionPane.showMessageDialog(this, "Utilisateur supprimer avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
<<<<<<< HEAD
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to get nomber of Candidats
    private int getNbrCandidats() {
        try {
            userDao = new UserDao();
            return userDao.calculCandidats();
=======
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to get nomber of Candidats
    private int getNbrCandidats(){
        try {
            userDao = new UserDao();
            return userDao.calculCandidats(); 
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        } catch (Exception e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Method to load user data into the table
    private void loadUserData() {
        try {
            userDao = new UserDao();
            List<User> users = userDao.getAll();
            updateTable(users);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to update the table with user data
    private void updateTable(List<User> users) {
        tableModel.setRowCount(0);
        for (User user : users) {
<<<<<<< HEAD
            tableModel.addRow(new Object[] { user.getId(), user.getNom(), user.getPrenom(), user.getAdresse(),
                    user.getTelephone() });
        }
    }

    private void populateFieldsFromSelectedUser() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int userId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    User selectedUser = userDao.find(userId);
                    if (selectedUser != null) {
                        // Populate the text fields with the selected user's information
                        nomField.setText(selectedUser.getNom());
                        prenomField.setText(selectedUser.getPrenom());
                        adresseField.setText(selectedUser.getAdresse());
                        telephoneField.setText(selectedUser.getTelephone());
                    }
                } catch (SQLException ex) {
                    handleError("Erreur lors de la récupération des données de l'utilisateur : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private void clearFieldsAndSelection() {
        // Clear text fields
=======
            tableModel.addRow(new Object[]{user.getId(), user.getNom(), user.getPrenom(), user.getAdresse(), user.getTelephone()});
        }
    }
    private void populateFieldsFromSelectedUser() {
    if (!table.getSelectionModel().getValueIsAdjusting()) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                User selectedUser = userDao.find(userId);
                if (selectedUser != null) {
                    // Populate the text fields with the selected user's information
                    nomField.setText(selectedUser.getNom());
                    prenomField.setText(selectedUser.getPrenom());
                    adresseField.setText(selectedUser.getAdresse());
                    telephoneField.setText(selectedUser.getTelephone());
                }
            } catch (SQLException ex) {
                handleError("Erreur lors de la récupération des données de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}       private void clearFieldsAndSelection() {
         // Clear text fields
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");

        // Unselect user from the table
        table.clearSelection();
<<<<<<< HEAD
        nbrLabel.setText("Nbr Candidats : " + getNbrCandidats() + "  ");
    }

=======
    }



>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
    // Method to handle errors by displaying a message dialog
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
