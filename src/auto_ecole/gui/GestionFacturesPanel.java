package auto_ecole.gui;

import auto_ecole.database.PaiementDao;
import auto_ecole.database.UserDao;
import auto_ecole.model.Paiement;
import auto_ecole.model.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestionFacturesPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private PaiementDao paiementDao;
    private JComboBox<String> eleveComboBox;
    private JTextField montantField;
    private JDateChooser dateChooser;
    private JComboBox<String> modePaiementComboBox;

    public GestionFacturesPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 100));
        northPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));

        // Title Label with icon
        JLabel label = new JLabel("Gestion des Paiements");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 191, 255));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon
        ImageIcon icon = new ImageIcon("./src/facture.png");
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(resizedIcon);

        // Adding icon and title label to the title panel
        titlePanel.add(iconLabel);
        titlePanel.add(label);
        northPanel.add(titlePanel, BorderLayout.CENTER);

        // Left Panel: Add User Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
        leftPanel.setBackground(Color.WHITE);

        // Composants du formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.white);
        formPanel.setBorder(new CompoundBorder(new LineBorder(new Color(178, 34, 34)), new EmptyBorder(10, 10, 10, 10)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel addUserLabel = new JLabel("Ajouter un facture :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(new Color(178, 34, 34));
        formPanel.add(addUserLabel, gbc);

        // Composants du formulaire
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche

        gbc.gridy++;
        formPanel.add(new JLabel("Élève:"), gbc);
        gbc.gridy++;
        eleveComboBox = new JComboBox<>();
        loadEleves(eleveComboBox); // Charger les élèves dans le JComboBox
        eleveComboBox.setPreferredSize(new Dimension(150, eleveComboBox.getPreferredSize().height));
        eleveComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        eleveComboBox.setBackground(Color.WHITE);
        formPanel.add(eleveComboBox, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Montant:"), gbc);
        gbc.gridy++;
        montantField = new JTextField(14);
        montantField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(montantField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Date de paiement:"), gbc);
        gbc.gridy++;
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, dateChooser.getPreferredSize().height));
        dateChooser.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateChooser, gbc);
        formPanel.add(dateChooser, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Mode de paiement:"));
        gbc.gridy++;
        String[] modesPaiement = {"Cash", "Carte Bancaire"};
        modePaiementComboBox = new JComboBox<>(modesPaiement);
        modePaiementComboBox.setPreferredSize(new Dimension(150, modePaiementComboBox.getPreferredSize().height));
        modePaiementComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        modePaiementComboBox.setBackground(Color.WHITE);
        formPanel.add(modePaiementComboBox, gbc);

        // RoundedBorder
        formPanel.setBorder(new GestionFacturesPanel.RoundedBorder(20));

        // Ajouter le bouton Ajouter dans le formulaire
        JButton addButton = new JButton("Ajouter");
<<<<<<< HEAD
        addButton.setBackground(Color.BLUE);
=======
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        addButton.setBorder(new GestionFacturesPanel.RoundedBorder(10));
>>>>>>> bf115c5 (Updated Page Accuiel,Facture and The Logo)
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPaiement();
            }
        });

        JButton clearButton = new JButton("Supprimer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(178, 34, 34)); // Rouge clair
        clearButton.setBorder(new GestionFacturesPanel.RoundedBorder(10)); // Bordure arrondie
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedPaiement();
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

        // Panel Droite: Tableau des paiements
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(20, 20, 20, 20)));
        rightPanel.setBackground(Color.WHITE);

        // Tableau des paiements
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("ID de l'élève");
        tableModel.addColumn("Montant");
        tableModel.addColumn("Date de paiement");
        tableModel.addColumn("Mode de paiement");
        table = new JTable(tableModel);

        // Ajouter un écouteur d'événements à la table pour détecter la sélection d'une ligne
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("Selection Changed");
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Load the selected row's data into the form
                        int paiementId = (int) tableModel.getValueAt(selectedRow, 0);
                        int eleveId = (int) tableModel.getValueAt(selectedRow, 1);

                        double montant = (double) tableModel.getValueAt(selectedRow, 2);
                        Date datePaiement = (Date) tableModel.getValueAt(selectedRow, 3);
                        String modePaiement = (String) tableModel.getValueAt(selectedRow, 4);

                        // Update the form fields with the loaded data
                        eleveComboBox.setSelectedItem(eleveId);
                        montantField.setText(String.valueOf(montant));

                        dateChooser.setDate(datePaiement);
                        modePaiementComboBox.setSelectedItem(modePaiement);
                    }
                }
            }
        });
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.pink); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true);

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionFacturesPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

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

        // Charger les données des paiements
        loadPaiementData();
    }

    // Méthode pour charger les données des paiements dans le tableau
    private void loadPaiementData() {
        try {
            paiementDao = new PaiementDao();
            List<Paiement> paiements = paiementDao.getAll();
            updateTable(paiements);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données des paiements : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour le tableau avec les données des paiements
    private void updateTable(List<Paiement> paiements) {
        tableModel.setRowCount(0);
        for (Paiement paiement : paiements) {
            tableModel.addRow(new Object[]{paiement.getId(), paiement.getEleveId(), paiement.getMontant(), paiement.getDatePaiement(), paiement.getModePaiement()});
        }
    }

    // Méthode pour ajouter un nouveau paiement
    private void addPaiement() {
        String selectedUserName = (String) eleveComboBox.getSelectedItem();
        User selectedUser = getUserByName(selectedUserName);
        if (selectedUser != null) {
            int eleveId = selectedUser.getId();
            String montant = montantField.getText();
            Date datePaiement = new Date(dateChooser.getDate().getTime());
            String modePaiement = (String) modePaiementComboBox.getSelectedItem();

            try {
                Paiement newPaiement = new Paiement(eleveId, Double.parseDouble(montant), datePaiement, modePaiement);
                paiementDao.save(newPaiement);
                loadPaiementData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Paiement ajouté avec succès.", "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException | SQLException ex) {
                handleError("Erreur lors de l'ajout du paiement : " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Élève sélectionné non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

// Méthode pour obtenir l'objet User à partir du nom
    private User getUserByName(String name) {
        try {
            UserDao userDao = new UserDao();
            return userDao.getUserByName(name);
        } catch (SQLException e) {
            handleError("Erreur lors de la récupération de l'utilisateur par son nom : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

// Modifier la méthode deleteSelectedPaiement pour supprimer le paiement sélectionné
    private void deleteSelectedPaiement() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce paiement ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                int paiementId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    paiementDao.delete(paiementId);
                    loadPaiementData();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Paiement supprimé avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    handleError("Erreur lors de la suppression du paiement : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un paiement à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour effacer les champs du formulaire
    private void clearFields() {
        montantField.setText("");
        dateChooser.setDate(null);
    }

    // Méthode pour afficher les erreurs en boîte de dialogue
    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Méthode pour charger les élèves dans la combobox
    private void loadEleves(JComboBox<String> comboBox) {
        try {
            UserDao userDao = new UserDao();
            List<User> users = userDao.getAll();
            for (User user : users) {
                comboBox.addItem(user.getNom()); // Add only the name of the user
            }
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des élèves : " + e.getMessage());
            e.printStackTrace();
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
