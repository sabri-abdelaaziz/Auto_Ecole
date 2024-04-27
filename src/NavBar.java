import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NavBar extends JPanel {
    public NavBar() {
        // Définir le layout de la barre de navigation
        setLayout(new BorderLayout());
        setBackground(new Color(144, 238, 144)); // Background vert clair

        // Créer le menu vertical à gauche
        JPanel verticalMenu = createVerticalMenu();

        // Ajouter le menu vertical à gauche
        add(verticalMenu, BorderLayout.WEST);
    }

    private JPanel createVerticalMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(10, 1)); // Utilisation d'un GridLayout pour placer les boutons dans une colonne
        menuPanel.setBackground(new Color(0, 100, 0)); // Fond vert foncé

        // Ajouter les titres du menu vertical comme des boutons cliquables
        String[] titles = {"Accueil", "Gestion Candidats", "Gestion Véhicules", "Gestion Moniteurs",
                           "Gestion Séances", "Gestion Cours", "Gestion Examens", "Gestion Factures",
                           "Gestion Réservations", "Statistiques"};
        for (String title : titles) {
            JButton button = new JButton(title);
            button.setFont(new Font("Times New Roman", Font.BOLD, 16)); // Texte en Times New Roman
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Ajouter une ligne noire en dessous de chaque bouton
            button.setBackground(new Color(0, 100, 0)); // Fond vert foncé
            button.setForeground(Color.WHITE); // Texte blanc
            button.setPreferredSize(new Dimension(200, 40)); // Taille des boutons
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Action à effectuer lorsque le bouton est cliqué
                    // Par exemple, changer de page ou afficher une nouvelle fonctionnalité
                    // Vous pouvez implémenter cela selon votre structure d'application
                    System.out.println("Button clicked: " + title);
                }
            });
            menuPanel.add(button);
        }

        return menuPanel;
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de la NavBar dans une JFrame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("NavBar");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 600); // Taille de la fenêtre
                frame.setLocationRelativeTo(null); 
                frame.getContentPane().add(new NavBar(), BorderLayout.CENTER);
                frame.setVisible(true);
            }
        });
    }
}
