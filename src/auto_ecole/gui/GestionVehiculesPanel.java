package auto_ecole.gui;

import auto_ecole.database.VehiculeDao;
import auto_ecole.model.Vehicule;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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

        // Panel gauche : formulaire d'ajout de véhicule
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Composants du formulaire d'ajout de véhicule
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(new JLabel("Marque:"));
        marqueField = new JTextField();
        formPanel.add(marqueField);
        formPanel.add(new JLabel("Modèle:"));
        modeleField = new JTextField();
        formPanel.add(modeleField);
        formPanel.add(new JLabel("Année de fabrication:"));
        anneeFabricationField = new JTextField();
        formPanel.add(anneeFabricationField);
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVehicule();
            }
        });
        formPanel.add(addButton);
        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Panel droit : tableau des véhicules
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tableau des véhicules
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Marque");
        tableModel.addColumn("Modèle");
        tableModel.addColumn("Année de fabrication");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Charger les données des véhicules
        loadVehiculeData();

        // Ajouter les panels gauche et droit au panel principal
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    // Méthode pour ajouter un nouveau véhicule
    private void addVehicule() {
        String marque = marqueField.getText();
        String modele = modeleField.getText();
        int anneeFabrication = Integer.parseInt(anneeFabricationField.getText());
        if (!marque.isEmpty() && !modele.isEmpty()) {
            try {
                Vehicule newVehicule = new Vehicule(marque, modele, anneeFabrication);
                vehiculeDao.save(newVehicule);
                loadVehiculeData(); // Recharger les données des véhicules après l'ajout
                // Effacer les champs du formulaire
                marqueField.setText("");
                modeleField.setText("");
                anneeFabricationField.setText("");
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
}
