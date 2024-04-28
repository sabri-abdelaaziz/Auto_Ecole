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

public class GestionCandidatsPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private UserDao userDao;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;

    public GestionCandidatsPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Left Panel: Add User Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add User Form Components
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
                addUser();
            }
        });
        formPanel.add(addButton);
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
                loadUserData(); // Reload user data after addition
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
        tableModel.setRowCount(0); // Clear the existing table data
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getId(), user.getNom(), user.getPrenom(), user.getAdresse(), user.getTelephone()});
        }
    }

    // Method to handle errors by displaying a message dialog
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
