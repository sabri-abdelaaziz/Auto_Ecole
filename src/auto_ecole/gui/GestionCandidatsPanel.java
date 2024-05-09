package auto_ecole.gui;

import auto_ecole.database.MoniteursDao;
import auto_ecole.database.UserDao;
import auto_ecole.model.User;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class GestionCandidatsPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField adresseField;
    private JTextField telephoneField;

    private UserDao userDao;

    public GestionCandidatsPanel() throws SQLException {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
         try {
            userDao = new UserDao();
 
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
        JLabel label = new JLabel("Gestion Candidats");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 191, 255));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon

        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Desktop\\Projet_J2EE\\Auto_Ecole\\src\\candidat.png");
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

        JLabel addUserLabel = new JLabel("Ajouter un candidat :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(new Color(178, 34, 34));
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
        formPanel.setBorder(new RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.BLUE);
        addButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        addButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
                clearFields();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.BLUE);
        clearButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        clearButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
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
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.GRAY); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedUser();
            }
        });

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.GRAY); // Rouge clair
        deleteButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
                clearFields();
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.GRAY); // Rouge clair
        modifyButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyUser();
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
        loadUserData();
    }

    private void modifyUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                User selectedUser = userDao.find(id);
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
                    JOptionPane.showMessageDialog(this, "Cours modifié avec succès.", "Modification réussie",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Le cours sélectionné n'existe pas.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                handleError("Erreur lors de la modification du cours : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un cours à modifier.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                userDao.delete(id);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Cours supprimé avec succès.", "Suppression réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression du cours : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un cours à supprimer.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUser() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String telephone = telephoneField.getText();
        // Vérifier si l'année de fabrication est un nombre valide
        if (!telephone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tele = Integer.parseInt(telephone);
        if (!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !telephone.isEmpty()) {
            try {
                User newUser = new User(nom, prenom, adresse, telephone);
                userDao.save(newUser);
                loadUserData();
                clearFields();
            } catch (Exception ex) {
                handleError("Erreur lors de la récupération des données de l'utilisateur : " + ex.getMessage());
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(this, "Utilisateur ajouté avec succès.", "Ajout réussi",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadUserData() throws SQLException {
        try {
            userDao = new UserDao();
            List<User> users = userDao.getAll();
            updateTable(users);
        } catch (Exception ex) {
            handleError("Erreur lors de Load Data : " + ex.getMessage());
            ex.printStackTrace();
        }

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

    private void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
        table.clearSelection();
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
