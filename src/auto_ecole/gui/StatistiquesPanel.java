package auto_ecole.gui;
import auto_ecole.database.CoursDao;
import auto_ecole.database.ExamenDao;
import auto_ecole.database.UserDao;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatistiquesPanel extends JPanel {
    
    private UserDao userDao;
    private CoursDao coursDao;
    private ExamenDao examenDao;

    public StatistiquesPanel() {
        try {
            userDao=new UserDao(); 
            coursDao=new CoursDao();
            examenDao=new ExamenDao();
            int nbrUsers=userDao.calculCandidats();
            int nbrCours=coursDao.getNombreCours();
            int nbrExamen=examenDao.calculExamens();
            setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout with 2 columns
            
            // Add statistics for each entity
            add(createStatisticLabel("Cours", "Total: "+nbrCours, "Pratiques: 12"));
            add(new PieChart());
            add(createStatisticLabel("Candidats", "Total: "+nbrUsers, "Actifs: 800"));
            add(new LineChart("les revenu par mois","mois","revenues"));
            add(createStatisticLabel("Examen", "Total: "+nbrExamen, "Réussis: 25"));
            add(new BarChart());
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
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
