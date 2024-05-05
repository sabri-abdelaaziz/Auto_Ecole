package auto_ecole.gui;
import auto_ecole.Constants.FrameConstantes;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends JFrame implements NavBarListener {
    private final NavBar navBar;
    private JPanel centerPanel;    
    private JPanel headerPanel;

    public Home() throws SQLException {
        setTitle("Gestion Auto-Ecole");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        navBar = new NavBar();
        navBar.setNavBarListener(this);
        add(navBar, BorderLayout.WEST);

        // Create the initial center panel
        centerPanel = new AccueilPanel();
        add(centerPanel, BorderLayout.CENTER);

        headerPanel = new Header(); 
        add(headerPanel, BorderLayout.NORTH);
        headerPanel.setSize(800, 60);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        FrameConstantes frameConstantes = new FrameConstantes(this, "Gestion Auto-Ecole");
        frameConstantes.setVisible();
    }

    // Implementing the method from NavBarListener interface
    @Override
    public void titleClicked(String title) {
        try {
            JPanel newCenterPanel;
            
            switch (title) {
                case "Accueil":
                    newCenterPanel = new AccueilPanel();
                    break;
                case "Gestion Candidats":
                    newCenterPanel = new GestionCandidatsPanel();
                    break;
                case "Gestion Véhicules":
                    newCenterPanel = new GestionVehiculesPanel();
                    break;
                case "Gestion Moniteurs":
                    newCenterPanel = new GestionMoniteursPanel();
                    break;
                case "Gestion Séances":
                    newCenterPanel = new GestionSeancesPanel();
                    break;
                case "Gestion Cours":
                    newCenterPanel = new GestionCoursPanel();
                    break;
                case "Gestion Examens":
                    newCenterPanel = new GestionExamensPanel();
                    break;
                case "Gestion Factures":
                    newCenterPanel = new GestionFacturesPanel();
                    break;
                case "Gestion Réservations":
                    newCenterPanel = new GestionReservationsPanel();
                    break;
                case "Statistiques":
                   JPanel statistiquesContainer = new JPanel(new BorderLayout());
                   StatistiquesPanel statistiquesPanel = new StatistiquesPanel();
                   statistiquesContainer.add(statistiquesPanel, BorderLayout.CENTER);

                   // Create a JScrollPane and add the container panel to it
                   JScrollPane statistiquesScrollPane = new JScrollPane(statistiquesContainer);
                   statistiquesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                   statistiquesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                   // Assign the JScrollPane's viewport view to newCenterPanel
                   newCenterPanel = statistiquesContainer;

                   // Remove the existing centerPanel
                   if (centerPanel != null) {
                       remove(centerPanel);
                   }

                   // Repaint and revalidate the frame
                   setCenterPanel(newCenterPanel);
                   return; // Exit the method after adding the statistics panel

                  
                default:
                    newCenterPanel = new AccueilPanel();
                    break;
            }
            
            setCenterPanel(newCenterPanel);
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Set the center panel of the JFrame
    private void setCenterPanel(JPanel panel) {
        if (centerPanel != null) {
            remove(centerPanel);
        }
        centerPanel = panel;
        add(centerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    
}
