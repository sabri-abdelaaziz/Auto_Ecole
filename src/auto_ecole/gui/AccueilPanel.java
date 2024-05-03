package auto_ecole.gui;

import auto_ecole.database.CoursDao;
import auto_ecole.database.ExamenDao;
import auto_ecole.database.MoniteursDao;
import auto_ecole.database.PaiementDao;
import auto_ecole.database.UserDao;
import auto_ecole.database.VehiculeDao;
import auto_ecole.model.Paiement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

public class AccueilPanel extends JPanel {
    
 private JTable table;
 private JPanel chart;
private JPanel chartPanel;
  private ImageIcon originalIcon;
 private  PaiementDao paiementDao;
  private UserDao userDao;
    private CoursDao coursDao;
    private ExamenDao examenDao;
    private JLabel imageLabel;
    private VehiculeDao vehiculeDao;
    private MoniteursDao moniteurDao;
    
    public AccueilPanel() throws SQLException {
        
        
            chart=new PieChart();
            userDao=new UserDao(); 
            coursDao=new CoursDao();
            examenDao=new ExamenDao();
            paiementDao=new PaiementDao();
            vehiculeDao=new VehiculeDao();
            moniteurDao=new MoniteursDao();
            int nbrUsers=userDao.calculCandidats();
            int nbrMoniteurs=moniteurDao.calculMoniteurs();
            int nbrVehicules=vehiculeDao.getNombreVehicule();
            int nbrCours=coursDao.getNombreCours();
            int nbrExamen=examenDao.calculExamens();
            double totalRevenues=paiementDao.totalRevenues();
        
        
        
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        
        
        
        
        try {
    // URL of the image
    String imageUrl = "https://i.pinimg.com/564x/15/6a/58/156a584e3e6023d1c71cd104c59229c7.jpg";

   // Read the image from the URL
    BufferedImage originalImage = ImageIO.read(new URL(imageUrl));

    // Resize the image
    int scaledWidth = 600; // Desired width
    int scaledHeight = 350; // Desired height
    BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = resizedImage.createGraphics();
    g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
    g2d.dispose();

    // Create an ImageIcon from the resized image
    imageLabel = new JLabel(new ImageIcon(resizedImage));


    // Add the image label to the image panel
   // imagePanel.add(imageLabel);
} catch (IOException e) {
    System.err.println("Error loading image from URL: " + e.getMessage());
}

        
        
        
        
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Bienvenue dans l'interface d'administration de l'auto-école");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(headerLabel);

        // Informations principales
     // Informations principales
JPanel infoPanel = new JPanel();
infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout with vertical orientation
infoPanel.setBackground(Color.WHITE);

// Set the border for the panel to add some padding
infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10)); // Add padding to all sides

JLabel revenues= new JLabel("<html><div style='text-align: center;font-size:30dp;'> Total revenues :  "+totalRevenues+" DH</div></html>");
revenues.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
revenues.setForeground(Color.RED); // Create labels with styled text

JLabel statistiquetitle= new JLabel("<html><div style='text-align: center;'>Statistiques</div></html>");
statistiquetitle.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
statistiquetitle.setForeground(Color.BLUE); 
JLabel elevesLabel = new JLabel("<html><div style='text-align: center;'>Nombre d'élèves inscrits :  "+nbrUsers+"</div></html>");
elevesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
elevesLabel.setForeground(Color.BLACK); // Set text color

JLabel moniteursLabel = new JLabel("<html><div style='text-align: right;'>Nombre de moniteurs disponibles :   "+nbrMoniteurs+"</div></html>");
moniteursLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
moniteursLabel.setForeground(Color.BLACK); // Set text color
JLabel examesLabel = new JLabel("<html><div style='text-align: right;'>Nombre de Exames disponibles :   "+nbrExamen+"</div></html>");
examesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
examesLabel.setForeground(Color.BLACK); // Set text color

JLabel vehiculesLabel = new JLabel("<html><div style='text-align: right;'>Nombre de véhicules :    "+nbrVehicules+"</div></html>");
vehiculesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
vehiculesLabel.setForeground(Color.BLACK); // Set text color

JLabel revenusLabel = new JLabel("<html><div style='text-align: right;'>Nbrs Cours :  "+nbrCours+"</div></html>");
revenusLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and style
revenusLabel.setForeground(Color.BLACK); // Set text color

// Set vertical gap between components
infoPanel.add(Box.createVerticalStrut(2)); // Adjust the height of the space

// Add labels to the panel
 if(imageLabel!=null){
infoPanel.add(imageLabel);
 }
 infoPanel.add(new JLabel("\n\n"));
infoPanel.add(revenues);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(statistiquetitle);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(elevesLabel);
         infoPanel.add(new JLabel("\n"));
infoPanel.add(examesLabel);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(moniteursLabel);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(vehiculesLabel);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(revenusLabel);
 infoPanel.add(new JLabel("\n"));
 
  // Create three buttons
         JButton button1 = new JButton("Visualisation de pie");
        button1.setPreferredSize(new Dimension(10, 50)); // Set preferred size for button1

        JButton button2 = new JButton("Visualisation de Bar");
        button2.setPreferredSize(new Dimension(10, 50)); // Set preferred size for button2

        JButton button3 = new JButton("Visualisation de line");
        button3.setPreferredSize(new Dimension(10, 50)); // Set preferred size for button3

        
        
          button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when button1 is clicked
                
                
chartPanel.add(new PieChart(), BorderLayout.NORTH);
                                     repaint(); 

                                    
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Action to perform when button2 is clicked
                    
                    
chartPanel.add(chart=new LineChart("les revenu par mois","mois","revenues"), BorderLayout.NORTH);
                    repaint(); 
                } catch (SQLException ex) {
                    Logger.getLogger(AccueilPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
           
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Action to perform when button3 is clicked
                    
                    
chartPanel.add(new BarChart(), BorderLayout.NORTH);
                                        repaint(); 

                } catch (SQLException ex) {
                    Logger.getLogger(AccueilPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       JPanel p=new JPanel(new GridLayout(3, 1, 10, 10));
        // Add buttons to the panel
        p.add(button1);
        p.add(button2);
        p.add(button3);
        p.setPreferredSize(new Dimension(100, 100)); // Set preferred size

 infoPanel.add(p);



        // Add labels with values to a separate panel
      //  JPanel valuePanel = new JPanel(new GridLayout(2, 2, 10, 10));
       // valuePanel.setBackground(Color.WHITE);
        //valuePanel.add(elevesValueLabel);
        //valuePanel.add(moniteursValueLabel);
        //valuePanel.add(vehiculesValueLabel);
        //valuePanel.add(revenusValueLabel);

        // Graphiques et visualisations
  chartPanel = new JPanel(new BorderLayout());
chartPanel.add(chart, BorderLayout.NORTH);




table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the table panel to chartPanel
        chartPanel.add(tablePanel, BorderLayout.CENTER);

        // Load payment data
        loadPaiementData();

JPanel ss=new JPanel();
ss.add(new JLabel("Les facteurs payés récemment"));
chartPanel.add(ss, BorderLayout.SOUTH);
chartPanel.setBackground(Color.GRAY);


        // Images
      JPanel imagePanel = new JPanel();
imagePanel.setBackground(Color.WHITE);




        // Liens utiles
        JPanel linksPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        
        linksPanel.setBackground(Color.WHITE);
       // JButton for managing students
JButton elevesButton = new JButton("Gérer les élèves");
elevesButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font, style, and size
elevesButton.setForeground(Color.BLUE); // Set text color
elevesButton.setBackground(Color.BLUE); // Set background color

// JButton for managing instructors
JButton moniteursButton = new JButton("Gérer les moniteurs");
moniteursButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font, style, and size
moniteursButton.setForeground(Color.BLUE); // Set text color
moniteursButton.setBackground(Color.BLUE); // Set background color

// JButton for managing vehicles
JButton vehiculesButton = new JButton("Gérer les véhicules");
vehiculesButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font, style, and size
vehiculesButton.setForeground(Color.BLUE); // Set text color
vehiculesButton.setBackground(Color.BLUE); // Set background color



// Action listener for "Gérer les élèves" button
elevesButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your action here for managing students
        // For example, show a message dialog
        
        JOptionPane.showMessageDialog(null, "Gérer les élèves button clicked");
    }
});

// Action listener for "Gérer les moniteurs" button
moniteursButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your action here for managing instructors
        // For example, show a message dialog
        JOptionPane.showMessageDialog(null, "Gérer les moniteurs button clicked");
    }
});

// Action listener for "Gérer les véhicules" button
vehiculesButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your action here for managing vehicles
        // For example, show a message dialog
        JOptionPane.showMessageDialog(null, "Gérer les véhicules button clicked");
    }
});










        // Ajoutez ici d'autres boutons pour les fonctionnalités de gestion
       
        linksPanel.add(elevesButton);
        linksPanel.add(moniteursButton);
        linksPanel.add(vehiculesButton);
        // Ajoutez ici d'autres boutons

        // Ajouter tous les composants à la disposition principale
        add(headerPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
      //  add(valuePanel);
      //add(imageLabel, BorderLayout.EAST);
        add(chartPanel, BorderLayout.WEST);
        add(imagePanel, BorderLayout.NORTH);
        add(linksPanel, BorderLayout.SOUTH);
    }
    
       private void loadPaiementData() {
            try {
                 paiementDao = new PaiementDao();
                ArrayList<Paiement> paiements = (ArrayList<Paiement>) paiementDao.getAll();
                updateTable(paiements);
            } catch (SQLException e) {
                handleError("Erreur lors du chargement des données des paiements : " + e.getMessage());
                e.printStackTrace();
            }
        }


     private void updateTable(ArrayList<Paiement> paiements) {
        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Date", "Montant"});

        // Add rows to the table model
        for (Paiement paiement : paiements) {
            model.addRow(new Object[]{paiement.getId(), paiement.getDatePaiement(), paiement.getMontant()});
        }

        // Set the table model
        table.setModel(model);
    }
    private void handleError(String message) {
        // Handle error
    }
    

}


