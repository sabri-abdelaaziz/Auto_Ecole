package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Define the interface for handling title clicks
interface NavBarListener {
    void titleClicked(String title);
}

public class NavBar extends JPanel {
    private NavBarListener listener;

    public NavBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(144, 238, 144)); // Light green background

        JPanel verticalMenu = createVerticalMenu();
        add(verticalMenu, BorderLayout.WEST);
    }

    // Set the listener for title clicks
    public void setNavBarListener(NavBarListener listener) {
        this.listener = listener;
    }

    private JPanel createVerticalMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(10, 1));
        menuPanel.setBackground(new Color(0, 100, 0)); // Dark green background

        String[] titles = {"Accueil", "Gestion Candidats", "Gestion Véhicules", "Gestion Moniteurs",
                           "Gestion Séances", "Gestion Cours", "Gestion Examens", "Gestion Factures",
                           "Gestion Réservations", "Statistiques"};
        for (String title : titles) {
            JButton button = new JButton(title);
            button.setFont(new Font("Times New Roman", Font.BOLD, 16));
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            button.setBackground(new Color(0, 100, 0));
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(200, 40));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When a button is clicked, notify the listener
                    if (listener != null) {
                        listener.titleClicked(title);
                    }
                }
            });
            menuPanel.add(button);
        }

        return menuPanel;
    }
}
