package auto_ecole.gui;

import auto_ecole.database.CoursDao;
import auto_ecole.database.VehiculeDao;
import auto_ecole.model.Cours;
import auto_ecole.model.User;
import com.toedter.calendar.JDateChooser;

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

public class GestionCoursPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel nbrLabel;
    private CoursDao coursDao;
    private JTextField titreField;
    private JDateChooser dateDebutField;
    private JDateChooser dateFinField;
    private JTextField heureDebutField;
    private JTextField heureFinField;
    private JComboBox<String> vehiculeIdComboBox;
    private List<Integer> vehiculeIds;

    public GestionCoursPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Top panel
        JPanel northPanel = new JPanel(new BorderLayout());
        nbrLabel = new JLabel("Nbr Cours : " + getNbrCours() + "  ");
        nbrLabel.setFont(new Font("Arial", Font.BOLD, 12));
        northPanel.add(nbrLabel, BorderLayout.EAST);

        JLabel label = new JLabel("Gestion Cours");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(label, BorderLayout.CENTER);

        // Left Panel: Add Course Form
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add Course Form Components
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.add(new JLabel("Titre:"));
        titreField = new JTextField();
        formPanel.add(titreField);
        formPanel.add(new JLabel("Date Début:"));
        dateDebutField = new JDateChooser();
        formPanel.add(dateDebutField);
        formPanel.add(new JLabel("Date Fin:"));
        dateFinField = new JDateChooser();
        formPanel.add(dateFinField);
        formPanel.add(new JLabel("Heure Début:"));
        heureDebutField = new JTextField();
        formPanel.add(heureDebutField);
        formPanel.add(new JLabel("Heure Fin:"));
        heureFinField = new JTextField();
        formPanel.add(heureFinField);
        formPanel.add(new JLabel("Vehicule ID:"));
        vehiculeIdComboBox = new JComboBox<>();
        formPanel.add(vehiculeIdComboBox);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(addButton);
        JButton clearButton = new JButton("Effacer");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFieldsAndSelection();
            }
        });
        formPanel.add(clearButton);

        // Modify and Delete Buttons
        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCourse();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(modifyButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
                clearFieldsAndSelection();
            }
        });
        formPanel.add(deleteButton);

        leftPanel.add(formPanel, BorderLayout.NORTH);

        // Right Panel: Course Table
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Titre");
        tableModel.addColumn("Date Début");
        tableModel.addColumn("Date Fin");
        tableModel.addColumn("Heure Début");
        tableModel.addColumn("Heure Fin");
        tableModel.addColumn("Vehicule ID");
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean isSelected = table.getSelectedRow() != -1;
                    addButton.setEnabled(!isSelected);
                }
                populateFieldsFromSelectedCourse();
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Panels to Main Panel
        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Load course data
        loadCourseData();
    }

    // Method to add a new course
    private void addCourse() {
        String titre = titreField.getText();
        // Get selected date from date choosers
        java.sql.Date dateDebut = new java.sql.Date(dateDebutField.getDate().getTime());
        java.sql.Date dateFin = new java.sql.Date(dateFinField.getDate().getTime());
        String heureDebut = heureDebutField.getText();
        String heureFin = heureFinField.getText();

        String selectedItem = (String) vehiculeIdComboBox.getSelectedItem();
        String[] parts = selectedItem.split("-");
        int vehiculeId = Integer.parseInt(parts[0].trim());

        try {
            Cours newCourse = new Cours(titre, dateDebut, dateFin, heureDebut, heureFin, vehiculeId);
            coursDao.save(newCourse);
            loadCourseData();
            JOptionPane.showMessageDialog(this, "Cours ajouté avec succès.", "Ajout réussi",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            handleError("Erreur lors de l'ajout du cours : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to modify the selected course
    private void modifyCourse() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Cours selectedCourse = coursDao.find(courseId);
                if (selectedCourse != null) {
                    String titre = titreField.getText();
                    // Get selected date from date choosers
                    java.sql.Date dateDebut = new java.sql.Date(dateDebutField.getDate().getTime());
                    java.sql.Date dateFin = new java.sql.Date(dateFinField.getDate().getTime());
                    String heureDebut = heureDebutField.getText();
                    String heureFin = heureFinField.getText();

                    String selectedItem = (String) vehiculeIdComboBox.getSelectedItem();
                    String[] parts = selectedItem.split("-");
                    int vehiculeId = Integer.parseInt(parts[0].trim());

                    selectedCourse.setTitre(titre);
                    selectedCourse.setDateDebut(dateDebut);
                    selectedCourse.setDateFin(dateFin);
                    selectedCourse.setHeureDebut(heureDebut);
                    selectedCourse.setHeureFin(heureFin);
                    selectedCourse.setVehiculeId(vehiculeId);

                    coursDao.update(selectedCourse);

                    loadCourseData();
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

    // Method to delete the selected course
    private void deleteCourse() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int courseId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                coursDao.delete(courseId);
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

    private int getNbrCours() {
        try {
            coursDao = new CoursDao();
            return coursDao.getNombreCours();
        } catch (Exception e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private void loadCourseData() {
        try {
            VehiculeDao vehicleDao = new VehiculeDao();
            vehiculeIds = vehicleDao.getAllVehiculeIds();
            for (int id : vehiculeIds) {
                vehiculeIdComboBox.addItem(id + "-" + vehicleDao.find(id).getModele());
            }
            coursDao = new CoursDao();
            List<Cours> courses = coursDao.getAll();
            updateTable(courses);
        } catch (SQLException e) {
            handleError("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTable(List<Cours> courses) {
        tableModel.setRowCount(0);
        for (Cours course : courses) {
            tableModel.addRow(new Object[]{
                course.getId(),
                course.getTitre(),
                course.getDateDebut(),
                course.getDateFin(),
                course.getHeureDebut(),
                course.getHeureFin(),
                course.getVehiculeId()
            });
        }
    }

    private void clearFieldsAndSelection() {
        titreField.setText("");
        dateDebutField.setDate(null);
        dateFinField.setDate(null);
        heureDebutField.setText("");
        heureFinField.setText("");  
        table.clearSelection();
        nbrLabel.setText("Nbr Cours : " + getNbrCours() + "  ");
    }

    private void populateFieldsFromSelectedCourse() {
        if (!table.getSelectionModel().getValueIsAdjusting()) {
            int selectedRow = table.getSelectedRow();
            int columnIndex = table.getColumnModel().getColumnIndex("Vehicule ID");
            if (selectedRow != -1) {
                int courseId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    Cours selectedCourse = coursDao.find(courseId);
                    if (selectedCourse != null) {
                        // Populate the text fields with the selected course's information
                        titreField.setText(selectedCourse.getTitre());
                        dateDebutField.setDate(selectedCourse.getDateDebut());
                        dateFinField.setDate(selectedCourse.getDateFin());
                        heureDebutField.setText(selectedCourse.getHeureDebut());
                        heureFinField.setText(selectedCourse.getHeureFin());

                        // Find the index of the selected course's vehicle ID in the list of vehicle IDs
                        int selectedId  =(int) tableModel.getValueAt(selectedRow, columnIndex);

                        // Find the index of the selected ID in the JComboBox
                        int selectedIndex = -1;
                        for (int i = 0; i < vehiculeIdComboBox.getItemCount(); i++) {
                            String item = vehiculeIdComboBox.getItemAt(i);
                            if (item.startsWith(String.valueOf(selectedId))) {
                                selectedIndex = i;
                                break;
                            }
                        }

// Set the selected index of the JComboBox
                        if (selectedIndex != -1) {
                            vehiculeIdComboBox.setSelectedIndex(selectedIndex);
                        }

                    }
                } catch (SQLException ex) {
                    handleError("Erreur lors de la récupération des données du cours : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
