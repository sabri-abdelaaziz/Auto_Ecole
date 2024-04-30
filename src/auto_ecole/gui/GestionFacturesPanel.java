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

        // Panel Nord
        JPanel northPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Gestion des Paiements");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Panel Gauche: Formulaire d'ajout de paiement
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Composants du formulaire
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 5, 5));

        eleveComboBox = new JComboBox<>();
        loadEleves(eleveComboBox); // Charger les élèves dans le JComboBox
        formPanel.add(new JLabel("Élève:"));
        formPanel.add(eleveComboBox);

        formPanel.add(new JLabel("Montant:"));
        montantField = new JTextField();
        formPanel.add(montantField);

        formPanel.add(new JLabel("Date de paiement:"));
        dateChooser = new JDateChooser();
        formPanel.add(dateChooser);

        formPanel.add(new JLabel("Mode de paiement:"));
        String[] modesPaiement = { "Cash","Carte Bancaire"};
        modePaiementComboBox = new JComboBox<>(modesPaiement);
        formPanel.add(modePaiementComboBox);

        // Ajouter le bouton Ajouter dans le formulaire
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPaiement();
            }
        });
        formPanel.add(addButton);

        // Ajouter le bouton Supprimer dans le formulaire
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedPaiement();
            }
        });
        formPanel.add(deleteButton);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Panel Droite: Tableau des paiements
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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


        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les panels au panel principal
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

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
        String modePaiement =  (String) modePaiementComboBox.getSelectedItem();

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
   
   

}
