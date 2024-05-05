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
        setBackground(Color.BLACK); // Black background

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
        menuPanel.setBackground(Color.BLACK); // Black background

        String[] titles = {"Accueil", "Gestion Candidats", "Gestion Véhicules", "Gestion Moniteurs",
                "Gestion Séances", "Gestion Cours", "Gestion Examens", "Gestion Factures",
                "Gestion Réservations", "Statistiques"};
        for (String title : titles) {
            JButton button = new JButton(title);
            button.setFont(new Font("Times New Roman", Font.BOLD, 16));
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE)); // White bottom border
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE); // White text color
            button.setPreferredSize(new Dimension(200, 40));

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    button.setBackground(new Color(10, 100, 10)); // Change background color when mouse is pressed
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(Color.BLACK); // Restore default background color when mouse is released
                }
            });

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button.setBackground(Color.BLACK); // Restore default background color when clicked

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
