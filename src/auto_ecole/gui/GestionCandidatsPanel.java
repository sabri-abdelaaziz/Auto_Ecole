package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GestionCandidatsPanel extends JPanel {
    private JPanel candidats;
    
    public GestionCandidatsPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 300));
        JLabel label = new JLabel("Gestion Candidats");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
