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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class StatistiquesPanel extends JPanel {
    
     private  PaiementDao paiementDao;
  private UserDao userDao;
    private CoursDao coursDao;
    private ExamenDao examenDao;
    private JLabel imageLabel;
    private VehiculeDao vehiculeDao;
    private MoniteursDao moniteurDao;
    
 private JTable table;

    public StatistiquesPanel() {
        
        try {
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
            
             JLabel label = new JLabel("Gestion Candidats");
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setForeground(new Color(0, 191, 255));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        

        // Loa
            
            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout with 2 columns
            add(southPanel,BorderLayout.CENTER);
            add(titlePanel,BorderLayout.NORTH);
            
           // JPanel chartPanel = new JPanel(new BorderLayout());
           // chartPanel.add(chart, BorderLayout.NORTH);
           
           
           
           //------   stats
           
               // Informations principales
JPanel infoPanel = new JPanel();
infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout with vertical orientation
infoPanel.setBackground(Color.WHITE);

// Set the border for the panel to add some padding
infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10)); // Add padding to all sides

JLabel revenues= new JLabel("<html><div style='text-align: center; width:500px;'> Total revenues :  "+totalRevenues+" DH</div></html>");
revenues.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
revenues.setForeground(Color.RED); // Create labels with styled text

JLabel statistiquetitle= new JLabel("<html><div style='text-align: center; width:500px;'>Statistiques</div></html>");
statistiquetitle.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
statistiquetitle.setForeground(Color.BLACK); 
JLabel elevesLabel = new JLabel("<html><div style='text-align: left; width:300px;'>Nombre d'élèves inscrits :<strong>  "+nbrUsers+"</strong></div></html>");
elevesLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
elevesLabel.setForeground(Color.BLACK); // Set text color

JLabel moniteursLabel = new JLabel("<html><div style='text-align: left; width:300px;'>Nombre de moniteurs disponibles : <strong>"+nbrMoniteurs+"</strong></div></html>");
moniteursLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
moniteursLabel.setForeground(Color.BLACK); // Set text color
JLabel examesLabel = new JLabel("<html><div style='text-align: left; width:300px;'>Nombre de Exames disponibles : <strong>"+nbrExamen+"</strong></div></html>");
examesLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
examesLabel.setForeground(Color.BLACK); // Set text color

JLabel vehiculesLabel = new JLabel("<html><div style='text-align: left; width:300px;'>Nombre de véhicules disponibles :  <strong>"+nbrVehicules+"</strong></div></html>");
vehiculesLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
vehiculesLabel.setForeground(Color.BLACK); // Set text color

JLabel revenusLabel = new JLabel("<html><div style='text-align: left; width:300px;'>Nombre de Cours disponibles : <strong>"+nbrCours+"</strong></div></html>");
revenusLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and style
revenusLabel.setForeground(Color.BLACK); // Set text color

// Set vertical gap between components
infoPanel.add(Box.createVerticalStrut(2)); // Adjust the height of the space

        infoPanel.setBorder(new CompoundBorder(new LineBorder(new Color(18, 34, 34)), new EmptyBorder(10, 10, 10, 10)));

 infoPanel.add(new JLabel("\n\n"));
infoPanel.add(statistiquetitle);

 infoPanel.add(new JLabel("\n"));
infoPanel.add(revenues);

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
 




        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the table panel to chartPanel
       // chartPanel.add(tablePanel, BorderLayout.CENTER);
            
            // Add statistics for each entity
            
           // add(infoPanel);
            southPanel.add(new PieChart());
            
            southPanel.add(infoPanel);
            southPanel.add(new LineChart("les revenu par mois","mois","revenues"));
            southPanel.add(infoPanel);
            southPanel.add(new BarChart());
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
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


    // Helper method to create a JPanel with statistics for an entity
    private JPanel createStatisticLabel(String entityName, String statistic1, String statistic2) {
        JPanel panel = new JPanel(new GridLayout(0, 1)); 
        panel.setBorder(BorderFactory.createTitledBorder(entityName)); 

        JLabel label1 = new JLabel(statistic1);
        label1.setFont(new Font("Arial", Font.BOLD, 16)); // Set font style and size
        JLabel label2 = new JLabel(statistic2);
        label2.setFont(new Font("Arial", Font.BOLD, 16)); // Set font style and size

        panel.add(label1);
        panel.add(label2);

        return panel;
    }
}
