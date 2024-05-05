package auto_ecole.gui;

import auto_ecole.database.VehiculeDao;
import auto_ecole.model.Vehicule;

import javax.swing.*;
import javax.swing.border.*;
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

public class GestionVehiculesPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private VehiculeDao vehiculeDao;
    private JTextField marqueField;
    private JTextField modeleField;
    private JTextField anneeFabricationField;

    public GestionVehiculesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        try {
            vehiculeDao = new VehiculeDao();
        } catch (SQLException ex) {
            handleError("Erreur lors de l'initialisation de la DAO de véhicule : " + ex.getMessage());
            ex.printStackTrace();
        }

        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 100));
        northPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        // Title Label with icon
        JLabel label = new JLabel("Gestion Véhicules");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 191, 255));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon
        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Desktop\\Projet_J2EE\\Auto_Ecole\\src\\voiture.jpg");
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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

        JLabel addUserLabel = new JLabel("Ajouter une véhicule :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(Color.GRAY);
        formPanel.add(addUserLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        formPanel.add(new JLabel("Marque:"), gbc);
        gbc.gridy++;
        marqueField = new JTextField(15);
        marqueField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(marqueField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Modele:"), gbc);
        gbc.gridy++;
        modeleField = new JTextField(15);
        modeleField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(modeleField, gbc);
        gbc.gridy++;
        formPanel.add(new JLabel("Année de fabrication:"), gbc);
        gbc.gridy++;
        anneeFabricationField = new JTextField(15);
        anneeFabricationField.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Police Times New Roman en noir
        formPanel.add(anneeFabricationField, gbc);

        // RoundedBorder
        formPanel.setBorder(new RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.GRAY); // Rouge clair
        addButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVehicule();
                clearFields();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(Color.GRAY); // Rouge clair
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
        tableModel.addColumn("Marque");
        tableModel.addColumn("Modele");
        tableModel.addColumn("Année de Fabrication");

        table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(new Color(178, 34, 34)); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedVehicule();
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
        deleteButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        deleteButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteVehicule();
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        modifyButton.setBorder(new RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyVehicule();
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
        loadVehiculeData();
    }

    // Méthode pour ajouter un nouveau véhicule
    private void addVehicule() {
        String marque = marqueField.getText();
        String modele = modeleField.getText();
        String anneeFabricationText = anneeFabricationField.getText();

        // Vérifier si l'année de fabrication est un nombre valide
        if (!anneeFabricationText.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "L'année de fabrication doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anneeFabrication = Integer.parseInt(anneeFabricationText);

        if (!marque.isEmpty() && !modele.isEmpty()) {
            try {
                Vehicule newVehicule = new Vehicule(marque, modele, anneeFabrication);
                vehiculeDao.save(newVehicule);
                loadVehiculeData();
                clearFields();

                JOptionPane.showMessageDialog(this, "Véhicule ajouté avec succès.", "Ajout réussi",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                handleError("Erreur lors de l'ajout du véhicule : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour charger les données des véhicules dans le tableau
    private void loadVehiculeData() {
        try {
            vehiculeDao = new VehiculeDao();
            List<Vehicule> vehicules = vehiculeDao.getAll();
            updateTable(vehicules);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour le tableau avec les données des véhicules
    private void updateTable(List<Vehicule> vehicules) {
        tableModel.setRowCount(0); // Effacer les données existantes du tableau
        for (Vehicule vehicule : vehicules) {
            tableModel.addRow(new Object[]{vehicule.getId(), vehicule.getMarque(), vehicule.getModele(), vehicule.getAnneeFabrication()});
        }
    }

    // Méthode pour gérer les erreurs en affichant une boîte de dialogue de message
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void clearFields() {
        marqueField.setText("");
        modeleField.setText("");
        anneeFabricationField.setText("");
        table.clearSelection();

    }

    // Méthode pour supprimer un véhicule
    private void deleteVehicule() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int vehiculeId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                vehiculeDao.delete(vehiculeId);
                loadVehiculeData(); // Recharger les données des véhicules après la suppression
            } catch (SQLException ex) {
                handleError("Erreur lors de la suppression du véhicule : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un véhicule à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyVehicule() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int vehiculeId = (int) tableModel.getValueAt(selectedRow, 0);
            String marque = marqueField.getText();
            String modele = modeleField.getText();
            String anneeFabricationText = anneeFabricationField.getText();
            if (!marque.isEmpty() && !modele.isEmpty() && !anneeFabricationText.isEmpty()) {
                try {
                    int anneeFabrication = Integer.parseInt(anneeFabricationText);
                    Vehicule modifiedVehicule = new Vehicule(vehiculeId, marque, modele, anneeFabrication);
                    vehiculeDao.update(modifiedVehicule);
                    loadVehiculeData(); // Recharger les données des véhicules après la modification
                    clearFields(); // Effacer les champs du formulaire
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "L'année de fabrication doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    handleError("Erreur lors de la modification du véhicule : " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un véhicule à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateFieldsFromSelectedVehicule() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int vehiculeId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Vehicule selectedVehicule = vehiculeDao.find(vehiculeId);
                    if (selectedVehicule != null) {
                        marqueField.setText(selectedVehicule.getMarque());
                        modeleField.setText(selectedVehicule.getModele());
                        anneeFabricationField.setText(String.valueOf(selectedVehicule.getAnneeFabrication()));
                    }
                } catch (SQLException ex) {
                    handleError("Erreur lors de la récupération des données du véhicule : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
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
