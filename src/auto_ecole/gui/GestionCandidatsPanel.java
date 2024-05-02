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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionCandidatsPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;

    private JLabel nbrLabel;

    private UserDao userDao;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;

    public GestionCandidatsPanel() throws SQLException {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        //top panel
        JPanel northPanel = new JPanel(new BorderLayout());

        nbrLabel = new JLabel("Nbr Candidats : " + getNbrCandidats() + "  ");
        nbrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        northPanel.add(nbrLabel, BorderLayout.EAST);

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

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addUser();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    clearFieldsAndSelection();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        formPanel.add(addButton);

        JButton clearButton = new JButton("Effacer");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearFieldsAndSelection();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        formPanel.add(clearButton);

        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modifyUser();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    clearFieldsAndSelection();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        formPanel.add(modifyButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteUser();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    clearFieldsAndSelection();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCandidatsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        formPanel.add(deleteButton);

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
            @Override
            public void valueChanged(ListSelectionEvent e) {
                populateFieldsFromSelectedUser();
            }
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

    private void addUser() throws SQLException {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String telephone = telephoneField.getText();
        if (!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !telephone.isEmpty()) {
            User newUser = new User(nom, prenom, adresse, telephone);
            userDao.save(newUser);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès.", "Ajout réussi",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyUser() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            User selectedUser = userDao.find(userId);
            if (selectedUser != null) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String adresse = adresseField.getText();
                String telephone = telephoneField.getText();
                selectedUser.setNom(nom);
                selectedUser.setPrenom(prenom);
                selectedUser.setAdresse(adresse);
                selectedUser.setTelephone(telephone);
                
                userDao.update(selectedUser);
                
                loadUserData();
                JOptionPane.showMessageDialog(this, "Utilisateur modifié avec succès.", "Modification réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "L'utilisateur sélectionné n'existe pas.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            userDao.delete(userId);
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Utilisateur supprimé avec succès.", "Suppression réussie",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getNbrCandidats() throws SQLException {
        userDao = new UserDao();
        return userDao.calculCandidats();
        
    }

    private void loadUserData() throws SQLException {
        userDao = new UserDao();
        List<User> users = userDao.getAll();
        updateTable(users);
    }

    private void updateTable(List<User> users) {
        tableModel.setRowCount(0);
        for (User user : users) {
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

    private void clearFieldsAndSelection() throws SQLException {
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");

        table.clearSelection();
        nbrLabel.setText("Nbr Candidats : " + getNbrCandidats() + "  ");
    }

    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
