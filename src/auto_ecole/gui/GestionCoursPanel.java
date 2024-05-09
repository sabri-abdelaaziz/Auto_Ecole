package auto_ecole.gui;

import auto_ecole.database.CoursDao;
import auto_ecole.database.MoniteursDao;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
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
    private HeureField heureDebutField;
    private HeureField heureFinField;
    private JComboBox<String> vehiculeIdComboBox;
    private List<Integer> vehiculeIds;
    

    public GestionCoursPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
  
        
        try {
            coursDao = new CoursDao();
           

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
        JLabel label = new JLabel("Gestion Cours");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(178, 34, 34));
        //new Color(0, 191, 255)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);

        // Loading and resizing the icon


        ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Desktop\\Projet_J2EE\\Auto_Ecole\\src\\cours.png");

        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
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

        JLabel addUserLabel = new JLabel("Ajouter un cours :");
        addUserLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        addUserLabel.setForeground(Color.BLUE);
        formPanel.add(addUserLabel, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche
        // Add Course Form Components

        gbc.gridy++;
        formPanel.add(new JLabel("Titre:"), gbc);
        gbc.gridy++;
        titreField = new JTextField(15);
        titreField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(titreField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Date Début:"), gbc);
        gbc.gridy++;
        dateDebutField = new JDateChooser();
        dateDebutField.setPreferredSize(new Dimension(13 * titreField.getFontMetrics(titreField.getFont()).charWidth('W'), dateDebutField.getPreferredSize().height));
        dateDebutField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateDebutField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Date Fin:"), gbc);
        gbc.gridy++;
        dateFinField = new JDateChooser();
        dateFinField.setPreferredSize(new Dimension(13 * titreField.getFontMetrics(titreField.getFont()).charWidth('W'), dateFinField.getPreferredSize().height));
        dateFinField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(dateFinField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Heure Début:"), gbc);
        gbc.gridy++;
        heureDebutField = new GestionCoursPanel.HeureField(); // Initialisation de HeureField
        heureDebutField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureDebutField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Heure Fin:"), gbc);
        gbc.gridy++;
        heureFinField = new GestionCoursPanel.HeureField(); // Initialisation de HeureField
        heureFinField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        formPanel.add(heureFinField, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Vehicule ID:"), gbc);
        gbc.gridy++;
        vehiculeIdComboBox = new JComboBox<>();
        vehiculeIdComboBox.setPreferredSize(new Dimension(13 * titreField.getFontMetrics(titreField.getFont()).charWidth('W'), vehiculeIdComboBox.getPreferredSize().height));
        vehiculeIdComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        vehiculeIdComboBox.setBackground(Color.WHITE);
        formPanel.add(vehiculeIdComboBox, gbc);


        // RoundedBorder
        formPanel.setBorder(new GestionCoursPanel.RoundedBorder(20));

        // Create "Ajouter" button
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        addButton.setBorder(new GestionCoursPanel.RoundedBorder(10)); // Bordure arrondie
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
                clearFieldsAndSelection();
            }
        });

        // Create "Effacer" button
        JButton clearButton = new JButton("Effacer");
        clearButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(0, 191, 255)); // Rouge clair
        clearButton.setBorder(new GestionCoursPanel.RoundedBorder(10)); // Bordure arrondie
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFieldsAndSelection();
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
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14)); // Police Times New Roman en gras
        table.getTableHeader().setBackground(Color.pink); // Fond de l'entête en gris
        table.setBackground(Color.WHITE); // Fond du tableau en blanc
        table.setFillsViewportHeight(true); // Remplir la hauteur de la vue

        // Add table to scroll pane with rounded border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, leftPanel.getPreferredSize().height)); // Même hauteur que leftPanel
        scrollPane.setBorder(new GestionCoursPanel.RoundedBorder(20)); // Appliquer le RoundedBorder au JScrollPane
        scrollPane.setBackground(Color.white);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Create "Supprimer" button
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.pink); // Rouge clair
        deleteButton.setBorder(new GestionCoursPanel.RoundedBorder(10)); // Bordure arrondie
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        coursDao.delete(userId);
                        loadCourseData();
                        clearFieldsAndSelection();
                        JOptionPane.showMessageDialog(GestionCoursPanel.this, "Moniteur supprimé avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        handleError("Erreur lors de la suppression du moniteur : " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(GestionCoursPanel.this, "Veuillez sélectionner un moniteur à supprimer.", "Aucune sélection", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create "Modifier" button
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(Color.pink); // Rouge clair
        modifyButton.setBorder(new GestionCoursPanel.RoundedBorder(10)); // Bordure arrondie
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCourse();
                clearFieldsAndSelection();
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
        //nbrLabel.setText("Nbr Cours : " + getNbrCours() + "  ");
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
                        int selectedId = (int) tableModel.getValueAt(selectedRow, columnIndex);

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
    
        public static class HeureField extends JPanel {

        private JComboBox<String> hourComboBox;
        private JComboBox<String> minuteComboBox;

        public HeureField(){
            setLayout(new FlowLayout());
            setBackground(Color.WHITE);
            
            hourComboBox = new JComboBox<>();
            minuteComboBox = new JComboBox<>();
            hourComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            hourComboBox.setBackground(Color.WHITE);
            minuteComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            minuteComboBox.setBackground(Color.WHITE);
            
            // Ajouter les heures de 00 à 23 dans le JComboBox d'heures
            for (int i = 0; i < 24; i++) {
                hourComboBox.addItem(String.format("%02d", i)); // Formatage pour avoir 2 chiffres
            }

            // Ajouter les minutes de 00 à 59 dans le JComboBox de minutes
            for (int i = 0; i < 60; i++) {
                minuteComboBox.addItem(String.format("%02d", i)); // Formatage pour avoir 2 chiffres
            }

            // Ajouter des écouteurs pour détecter les changements dans les combobox
            hourComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateTime();
                }
            });

            minuteComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    updateTime();
                }
            });

            add(hourComboBox);
            add(new JLabel(":"));
            add(minuteComboBox);
        }

       


        // Méthode pour mettre à jour le champ de texte avec l'heure sélectionnée
        private void updateTime() {
            String hour = (String) hourComboBox.getSelectedItem();
            String minute = (String) minuteComboBox.getSelectedItem();
//            setText(hour + ":" + minute); // Met à jour le champ de texte avec l'heure sélectionnée
        }

        // Méthode pour définir l'heure dans les combobox
        public void setText(String time) {
            String[] parts = time.split(":");
            if (parts.length == 2) {
                String hour = parts[0];
                String minute = parts[1];
                hourComboBox.setSelectedItem((String) hour);
                minuteComboBox.setSelectedItem((String) minute);
            }
        }

        // Méthode pour obtenir l'heure sélectionnée
        public String getText() {
            String hour = (String) hourComboBox.getSelectedItem();
            String minute = (String) minuteComboBox.getSelectedItem();
            return hour + ":" + minute;
        }

        // Méthode pour réinitialiser l'heure à "00:00"
        public void resetTime() {
            hourComboBox.setSelectedIndex(-1); // Désélectionne toute sélection d'heure
            minuteComboBox.setSelectedIndex(-1); // Désélectionne toute sélection de minute

        }
    }
}
