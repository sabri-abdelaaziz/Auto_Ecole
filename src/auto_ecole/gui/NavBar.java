package auto_ecole.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

interface NavBarListener {
    void titleClicked(String title,int index);
}

public class NavBar extends JPanel {
    // Static variable to save the index of the clicked button
    private final Color selectedColor = Color.WHITE; // Selected button color
private final Color unselectedColor = Color.BLACK; // Unselected button color
private final Color selectedTextColor = Color.BLACK; // Selected button text color
private final Color unselectedTextColor = Color.WHITE; // Unselected button text color

    private final NavBarListener listener;

    public NavBar(NavBarListener listener) {
        System.out.println("created NarBAr");
        this.listener = listener;
        setLayout(new GridLayout(0, 1));
        setBackground(Color.BLACK);
        createVerticalMenu();
        updateButtons();
    }

    private void createVerticalMenu() {
        String[] titles = {"Accueil", "Gestion Candidats", "Gestion Véhicules", "Gestion Moniteurs",
                "Gestion Séances", "Gestion Cours", "Gestion Examens", "Gestion Factures",
                "Gestion Réservations", "Statistiques"};

        for (int i = 0; i < titles.length; i++) {
            JButton button = new JButton(titles[i]);
            
                button.setOpaque(true);
            button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
            //button.setBackground(unselectedColor);
           // button.setForeground(unselectedTextColor);
            button.setPreferredSize(new Dimension(200, 40));
            String title = titles[i];
            int index = i;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NavBarStatique.selectedIndex = index; // Update the static variable with the clicked button index
                    updateButtons();
                    if (listener != null) {
                        listener.titleClicked(title,index);
                    }
                }
            });

            add(button);
        }
    }

    private void updateButtons() {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                int index = getIndexForButton(button);
                if (index == NavBarStatique.selectedIndex) {
                    
            button.setFont(new Font("Times New Roman", Font.BOLD, 20));
                    button.setBackground(selectedColor);
                    button.setForeground(selectedTextColor);
                } else {
                    button.setBackground(unselectedColor);
                    
            button.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    button.setForeground(unselectedTextColor);
                }
            }
        }
    }

    private int getIndexForButton(JButton button) {
        Component[] components = getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == button) {
                return i;
            }
        }
        return -1;
    }
    public void updateButtons(int clickedIndex) {
        System.out.println("updateButton");
    NavBarStatique.selectedIndex = clickedIndex;
    Component[] components = getComponents();
    for (Component component : components) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            int index = getIndexForButton(button);
            if (index == NavBarStatique.selectedIndex) {
                
             System.out.println("i :"+index+" = stat: "+NavBarStatique.selectedIndex);
                button.setBackground(selectedColor);
                button.setForeground(selectedTextColor);
            } else {
                button.setBackground(unselectedColor);
                button.setForeground(unselectedTextColor);
            }
        }
    }
}

}
