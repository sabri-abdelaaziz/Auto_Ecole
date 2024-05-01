package auto_ecole.gui;

import auto_ecole.Constants.FrameConstantes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;




public class Home extends JFrame implements NavBarListener{
    private final NavBar navBar;
    private JPanel centerPanel;    
    private JPanel headerPanel;


    public Home() {
        
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        navBar = new NavBar();
        navBar.setNavBarListener(this);
        add(navBar, BorderLayout.WEST);

        // Create the initial center panel
        centerPanel = new AccueilPanel();
        add(centerPanel, BorderLayout.CENTER);
        headerPanel=new Header(); 
        add(headerPanel,BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        FrameConstantes frameConstantes = new FrameConstantes(this,"Home");
        frameConstantes.setVisible();
<<<<<<< HEAD
=======
        
       
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
    }

     // Implementing the method from NavBarListener interface
    @Override
public void titleClicked(String title) {
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
<<<<<<< HEAD
            newCenterPanel = new GestionExamensPanel();
=======
            newCenterPanel = new GestionExamensPanel(); // Crée une nouvelle instance de GestionExamensPanel
>>>>>>> b2d697c (Rapport du projet & Gestion des examens)
            break;
        case "Gestion Factures":
            newCenterPanel = new GestionFacturesPanel();
            break;
        case "Gestion Réservations":
            newCenterPanel = new GestionReservationsPanel();
            break;
        case "Statistiques":
            newCenterPanel = new StatistiquesPanel();
            break;
        default:
            newCenterPanel = new AccueilPanel();
            break;
    }

    setCenterPanel(newCenterPanel);
}


    // Create a JPanel for the center content based on the title
    private JPanel createCenterPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setBorder(BorderFactory.createTitledBorder(title)); // Use the title as the border
        return panel;
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
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
    
}
